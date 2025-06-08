package cl.duoc.sistema_aduanero.controller;

import cl.duoc.sistema_aduanero.dto.AdjuntoViajeMenoresRequest;
import cl.duoc.sistema_aduanero.dto.SolicitudViajeMenoresResponse;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.service.DocumentoAdjuntoService;
import cl.duoc.sistema_aduanero.service.SolicitudAduanaService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  /**
   * Lista todas las solicitudes disponibles.
   *
   * @return lista de solicitudes en formato de respuesta
   */
  @GetMapping
  public List<SolicitudViajeMenoresResponse> listar() {
    return solicitudService.obtenerTodas().stream()
        .map(SolicitudViajeMenoresResponse::fromEntity)
        .toList();
  }

  /**
   * Obtiene una solicitud por id.
   *
   * @param id identificador de la solicitud
   * @return entidad envuelta en {@link ResponseEntity}
   */
  @GetMapping("/{id}")
  public ResponseEntity<SolicitudViajeMenoresResponse> obtenerPorId(
      @PathVariable Long id) {
    Optional<SolicitudViajeMenores> s = solicitudService.obtenerPorId(id);
    if (s.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(SolicitudViajeMenoresResponse.fromEntity(s.get()));
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

  @PutMapping("/{id}/estado")
  public ResponseEntity<SolicitudViajeMenoresResponse> actualizarEstado(
      @PathVariable Long id, @RequestParam String estado) {
    Optional<SolicitudViajeMenores> actOpt = solicitudService.actualizarEstado(id, estado);
    if (actOpt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(SolicitudViajeMenoresResponse.fromEntity(actOpt.get()));
  }
}

