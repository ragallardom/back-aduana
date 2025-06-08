package cl.duoc.sistema_aduanero.controller;

import cl.duoc.sistema_aduanero.dto.AdjuntoViajeMenoresRequest;
import cl.duoc.sistema_aduanero.dto.SolicitudViajeMenoresResponse;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.service.DocumentoAdjuntoService;
import cl.duoc.sistema_aduanero.service.SolicitudAduanaService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudAduanaController {

  private final SolicitudAduanaService solicitudService;
  private final DocumentoAdjuntoService adjuntoService;

  public SolicitudAduanaController(SolicitudAduanaService solicitudService,
                                   DocumentoAdjuntoService adjuntoService) {
    this.solicitudService = solicitudService;
    this.adjuntoService = adjuntoService;
  }

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> crear(@ModelAttribute AdjuntoViajeMenoresRequest request) {
    if (request.getArchivos() == null || request.getArchivos().size() < 3) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Map.of("message", "Se requieren al menos 3 archivos adjuntos"));
    }
    try {
      SolicitudViajeMenores entidad = request.toSolicitudEntity();
      SolicitudViajeMenores guardada = solicitudService.crearSolicitud(entidad);

      List<?> docs =
          adjuntoService.guardarAdjuntos(guardada, request.getTiposDocumento(), request.getArchivos());
      guardada.setDocumentos((List) docs);

      return ResponseEntity.ok(SolicitudViajeMenoresResponse.fromEntity(guardada));
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "Error procesando archivos"));
    }
  }
}
