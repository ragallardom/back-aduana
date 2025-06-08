package cl.duoc.sistema_aduanero.javafx;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Flow;

/**
 * Peer of HttpRequest.BodyPublisher that knows how to build a
 * multipart/form-data body. Usage: MultipartBodyPublisher builder =
 * MultipartBodyPublisher.builder(); builder.addPart("fieldName", "valorTexto");
 *    builder.addFilePart("archivo", Path.of("C:/ruta/mi.pdf"));
 *    HttpRequest.BodyPublisher body = builder.build();
 *    String boundary = builder.getBoundary();
 */
public class MultipartBodyPublisher implements HttpRequest.BodyPublisher {
  private static final String CRLF = "\r\n";
  private static final String TWO_HYPHENS = "--";

  private final String boundary;
  private final List<Part> parts = new ArrayList<>();

  private MultipartBodyPublisher(String boundary) { this.boundary = boundary; }

  public static MultipartBuilder builder() { return new MultipartBuilder(); }

  /**
   * Retorna el boundary (necesario para ponerlo en Content-Type:
   * multipart/form-data; boundary=XXX)
   */
  public String getBoundary() { return boundary; }

  @Override
  public long contentLength() {
    // Retornar -1 indica longitud desconocida, el cliente usará chunked
    // encoding
    return -1;
  }

  @Override
  public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
    subscriber.onSubscribe(
        new BodySubscription(subscriber, buildByteBuffers()));
  }

  private List<ByteBuffer> buildByteBuffers() {
    List<ByteBuffer> byteBuffers = new ArrayList<>();

    // Para cada parte, generamos un ByteBuffer con su encabezado, contenido y
    // CRLF al final
    for (Part part : parts) {
      // 1) --boundary\r\n
      byteBuffers.add(ByteBuffer.wrap(
          (TWO_HYPHENS + boundary + CRLF).getBytes(StandardCharsets.UTF_8)));

      // 2) Headers de la parte (Content-Disposition y posiblemente
      // Content-Type)
      StringBuilder headers = new StringBuilder();
      headers.append("Content-Disposition: form-data; name=\"")
          .append(part.fieldName)
          .append("\"");
      if (part.fileName != null) {
        // Si es un archivo, agregamos ; filename="xxx"
        headers.append("; filename=\"").append(part.fileName).append("\"");
        headers.append(CRLF);
        headers.append("Content-Type: ").append(part.contentType).append(CRLF);
      } else {
        headers.append(CRLF);
        // Para valor de texto, no hace falta Content-Type (por defecto es
        // text/plain; charset=UTF-8)
      }
      headers.append(CRLF); // línea en blanco antes del contenido
      byteBuffers.add(
          ByteBuffer.wrap(headers.toString().getBytes(StandardCharsets.UTF_8)));

      // 3) Contenido de la parte
      if (part.value != null) {
        // Parte texto
        byteBuffers.add(
            ByteBuffer.wrap(part.value.getBytes(StandardCharsets.UTF_8)));
        byteBuffers.add(ByteBuffer.wrap(CRLF.getBytes(StandardCharsets.UTF_8)));
      } else if (part.fileBytes != null) {
        // Parte archivo
        byteBuffers.add(ByteBuffer.wrap(part.fileBytes));
        byteBuffers.add(ByteBuffer.wrap(CRLF.getBytes(StandardCharsets.UTF_8)));
      }
    }

    // 4) LÍNEA FINAL: --boundary--\r\n
    byteBuffers.add(
        ByteBuffer.wrap((TWO_HYPHENS + boundary + TWO_HYPHENS + CRLF)
                            .getBytes(StandardCharsets.UTF_8)));

    return byteBuffers;
  }

  // Clase interna: Builder
  public static class MultipartBuilder {
    private final String boundary = UUID.randomUUID().toString();
    private final List<Part> parts = new ArrayList<>();

    private MultipartBuilder() {}

    public MultipartBuilder addPart(String fieldName, String value) {
      if (fieldName == null || value == null) {
        throw new IllegalArgumentException(
            "fieldName y value no pueden ser null");
      }
      parts.add(new Part(fieldName, null, "text/plain; charset=UTF-8",
                         value.getBytes(StandardCharsets.UTF_8)));
      return this;
    }

    public MultipartBuilder addFilePart(String fieldName, Path path)
        throws IOException {
      if (fieldName == null || path == null) {
        throw new IllegalArgumentException(
            "fieldName y path no pueden ser null");
      }
      String fileName = path.getFileName().toString();
      // Determinar Content-Type aproximado a partir de la extensión
      String contentType = Files.probeContentType(path);
      if (contentType == null) {
        contentType = "application/octet-stream";
      }
      byte[] fileBytes = Files.readAllBytes(path);
      parts.add(new Part(fieldName, fileName, contentType, fileBytes));
      return this;
    }

    public MultipartBodyPublisher build() {
      MultipartBodyPublisher mp = new MultipartBodyPublisher(boundary);
      mp.parts.addAll(this.parts);
      return mp;
    }

    /** Retorna el boundary que usará el BodyPublisher. */
    public String getBoundary() { return boundary; }
  }

  // Clase interna que almacena cada parte
  private static class Part {
    final String fieldName;
    final String fileName; // si es null, se trata como parte de texto
    final String
        contentType; // text/plain; charset=UTF-8  ó  application/pdf, etc.
    final byte[] fileBytes; // contenido del archivo o bytes del texto
    final String
        value; // si es parte texto, guardamos el texto aquí (value != null)

    Part(String fieldName, String fileName, String contentType, byte[] bytes) {
      this.fieldName = fieldName;
      this.fileName = fileName;
      this.contentType = contentType;
      if (fileName == null) {
        // es parte de texto
        this.value = new String(bytes, StandardCharsets.UTF_8);
        this.fileBytes = null;
      } else {
        // es parte de archivo
        this.value = null;
        this.fileBytes = bytes;
      }
    }
  }

  // Suscripción que va enviando los ByteBuffers al subscriber
  private static class BodySubscription implements Flow.Subscription {
    private final Flow.Subscriber<? super ByteBuffer> subscriber;
    private final Iterator<ByteBuffer> iterator;
    private boolean completed = false;

    BodySubscription(Flow.Subscriber<? super ByteBuffer> subscriber,
                     List<ByteBuffer> bufs) {
      this.subscriber = subscriber;
      this.iterator = bufs.iterator();
    }

    @Override
    public void request(long n) {
      try {
        long i = 0;
        while (i < n && iterator.hasNext()) {
          ByteBuffer buf = iterator.next();
          subscriber.onNext(buf);
          i++;
        }
        if (!iterator.hasNext() && !completed) {
          completed = true;
          subscriber.onComplete();
        }
      } catch (Exception e) {
        subscriber.onError(e);
      }
    }

    @Override
    public void cancel() {
      // No-op
    }
  }
}
