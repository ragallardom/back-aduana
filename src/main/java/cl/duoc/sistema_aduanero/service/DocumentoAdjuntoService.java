package cl.duoc.sistema_aduanero.service;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.repository.DocumentoAdjuntoRepository;
import cl.duoc.sistema_aduanero.repository.SolicitudAduanaRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentoAdjuntoService {

  @Autowired private DocumentoAdjuntoRepository documentoAdjuntoRepository;

  @Value("${app.upload.base-dir}") private String basePath;

  public AdjuntoViajeMenores guardarArchivo(SolicitudViajeMenores solicitud,
                                            String tipoDocumento,
                                            MultipartFile archivo)
      throws IOException {
    if (archivo == null || archivo.isEmpty()) {
      throw new IllegalArgumentException("El archivo está vacío");
    }

    String carpetaSolicitud =
        Paths.get(basePath, "solicitud_" + solicitud.getId()).toString();
    File dir = new File(carpetaSolicitud);
    if (!dir.exists()) {
      dir.mkdirs();
    }

    String originalFilename = archivo.getOriginalFilename();
    String extension = "";
    if (originalFilename != null && originalFilename.contains(".")) {
      extension = "." + FilenameUtils.getExtension(originalFilename);
    }

    String nombreFormateado =
        tipoDocumento.toLowerCase().replaceAll("\\s+", "_") + extension;

    Path rutaFinal = Paths.get(carpetaSolicitud, nombreFormateado);
    archivo.transferTo(rutaFinal.toFile());

    AdjuntoViajeMenores doc = new AdjuntoViajeMenores();
    doc.setSolicitud(solicitud);
    doc.setNombreOriginal(originalFilename != null ? originalFilename : "");
    doc.setNombreArchivo(nombreFormateado);
    doc.setRuta(rutaFinal.toString());

    return documentoAdjuntoRepository.save(doc);
  }

  public List<AdjuntoViajeMenores> guardarAdjuntos(
      SolicitudViajeMenores solicitud,
      List<String> tiposDocumento,
      List<MultipartFile> archivos)
      throws IOException {
    List<AdjuntoViajeMenores> guardados = new ArrayList<>();
    for (int i = 0; i < archivos.size(); i++) {
      String tipo = "doc" + i;
      if (tiposDocumento != null && i < tiposDocumento.size()) {
        tipo = tiposDocumento.get(i);
      }
      MultipartFile archivo = archivos.get(i);

      String carpetaSolicitud =
          Paths.get(basePath, "solicitud_" + solicitud.getId()).toString();
      File dir = new File(carpetaSolicitud);
      if (!dir.exists()) {
        dir.mkdirs();
      }

      String originalFilename = archivo.getOriginalFilename();
      String extension = "";
      if (originalFilename != null && originalFilename.contains(".")) {
        extension = "." + FilenameUtils.getExtension(originalFilename);
      }

      String timestamp = String.valueOf(System.currentTimeMillis());
      String nombreFormateado =
          solicitud.getId() + "_" + tipo.toLowerCase().replaceAll("\\s+", "_") + "_" + timestamp + extension;

      Path rutaFinal = Paths.get(carpetaSolicitud, nombreFormateado);
      archivo.transferTo(rutaFinal.toFile());

      AdjuntoViajeMenores doc = new AdjuntoViajeMenores();
      doc.setSolicitud(solicitud);
      doc.setNombreOriginal(originalFilename != null ? originalFilename : "");
      doc.setNombreArchivo(nombreFormateado);
      doc.setRuta(rutaFinal.toString());

      guardados.add(documentoAdjuntoRepository.save(doc));
    }
    return guardados;
  }

  @Autowired private SolicitudAduanaRepository solicitudRepository;

  public SolicitudViajeMenores crearSolicitud(SolicitudViajeMenores solicitud) {
    return solicitudRepository.save(solicitud);
  }

  public SolicitudViajeMenores obtenerPorId(Long id) {
    Optional<SolicitudViajeMenores> opt = solicitudRepository.findById(id);
    return opt.orElse(null);
  }
}
