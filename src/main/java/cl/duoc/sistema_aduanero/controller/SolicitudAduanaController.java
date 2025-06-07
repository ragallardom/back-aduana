package cl.duoc.sistema_aduanero.controller;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.service.DocumentoAdjuntoService;
import cl.duoc.sistema_aduanero.service.SolicitudAduanaService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudAduanaController {

    private static final String RUTA_UPLOADS = "uploads/";

    private final SolicitudAduanaService solicitudService;
    private final DocumentoAdjuntoService adjuntoService;

    public SolicitudAduanaController(SolicitudAduanaService solicitudService, DocumentoAdjuntoService adjuntoService) {
        this.solicitudService = solicitudService;
        this.adjuntoService = adjuntoService;
    }

    @PostMapping
    public ResponseEntity<SolicitudViajeMenores> crear(@RequestBody SolicitudViajeMenores solicitud) {
        return new ResponseEntity<>(solicitudService.crearSolicitud(solicitud), HttpStatus.CREATED);
    }

    @GetMapping
    public List<SolicitudViajeMenores> listar() {
        return solicitudService.obtenerTodas();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudViajeMenores> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado
    ) {
        return ResponseEntity.ok(solicitudService.actualizarEstado(id, estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudViajeMenores> obtenerPorId(@PathVariable Long id) {
        SolicitudViajeMenores s = solicitudService.obtenerPorId(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(s);
    }

    @GetMapping("/descargar/{id}")
    public ResponseEntity<InputStreamResource> descargarTodosLosDocumentos(@PathVariable Long id) {
        try {
            SolicitudViajeMenores solicitud = solicitudService.obtenerPorId(id);
            if (solicitud == null) {
                return ResponseEntity.notFound().build();
            }

            String nombreZip = "solicitud_" + id + "_documentos.zip";

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (AdjuntoViajeMenores doc : solicitud.getDocumentos()) {
                String rutaArchivo = doc.getRuta();
                Path pathDoc = Paths.get(rutaArchivo);

                if (!Files.exists(pathDoc) || Files.isDirectory(pathDoc)) {
                    continue;
                }

                String nombreDentroZip = doc.getNombreArchivo();

                zos.putNextEntry(new ZipEntry(nombreDentroZip));

                try (InputStream is = Files.newInputStream(pathDoc)) {
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }

                zos.closeEntry();
            }

            zos.finish();
            zos.close();

            byte[] zipBytes = baos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(zipBytes);
            InputStreamResource resource = new InputStreamResource(bis);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreZip + "\"")
                    .contentLength(zipBytes.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "¡Hola desde Spring Boot!";
    }

    @PostMapping("/adjuntar")
    public ResponseEntity<SolicitudViajeMenores> crearConAdjunto(
            @RequestParam String nombreSolicitante,
            @RequestParam String tipoDocumento,
            @RequestParam String numeroDocumento,
            @RequestParam String tipoAdjunto,
            @RequestParam String motivo,
            @RequestParam String paisOrigen,
            @RequestParam("archivo") MultipartFile archivo
    ) {
        try {
            SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
            solicitud.setNombreSolicitante(nombreSolicitante);
            solicitud.setTipoDocumento(tipoDocumento);
            solicitud.setNumeroDocumento(numeroDocumento);
            solicitud.setMotivo(motivo);
            solicitud.setPaisOrigen(paisOrigen);

            SolicitudViajeMenores guardada = solicitudService.crearSolicitud(solicitud);



            adjuntoService.guardarArchivo(guardada, tipoAdjunto, archivo);

            return new ResponseEntity<>(guardada, HttpStatus.CREATED);

        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
