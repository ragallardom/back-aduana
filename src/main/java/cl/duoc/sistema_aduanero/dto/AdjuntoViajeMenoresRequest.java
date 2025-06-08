package cl.duoc.sistema_aduanero.dto;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjuntoViajeMenoresRequest {

  private String estado;
  private String tipoSolicitudMenor;
  private String nombreMenor;
  private LocalDateTime fechaNacimientoMenor;
  private String documentoMenor;
  private String numeroDocumentoMenor;
  private String nacionalidadMenor;
  private String nombrePadreMadre;
  private String relacionMenor;
  private String documentoPadre;
  private String numeroDocumentoPadre;
  private String telefonoPadre;
  private String emailPadre;
  private LocalDateTime fechaViaje;
  private String numeroTransporte;
  private String paisOrigen;
  private String paisDestino;
  private String motivoViaje;

  // Datos de adjuntos
  private List<String> tiposDocumento;
  private List<MultipartFile> archivos;

  public SolicitudViajeMenores toSolicitudEntity() {
    SolicitudViajeMenores s = new SolicitudViajeMenores();
    s.setEstado(estado);
    s.setTipoSolicitudMenor(tipoSolicitudMenor);
    s.setNombreMenor(nombreMenor);
    s.setFechaNacimientoMenor(fechaNacimientoMenor);
    s.setDocumentoMenor(documentoMenor);
    s.setNumeroDocumentoMenor(numeroDocumentoMenor);
    s.setNacionalidadMenor(nacionalidadMenor);
    s.setNombrePadreMadre(nombrePadreMadre);
    s.setRelacionMenor(relacionMenor);
    s.setDocumentoPadre(documentoPadre);
    s.setNumeroDocumentoPadre(numeroDocumentoPadre);
    s.setTelefonoPadre(telefonoPadre);
    s.setEmailPadre(emailPadre);
    s.setFechaViaje(fechaViaje);
    s.setNumeroTransporte(numeroTransporte);
    s.setPaisOrigen(paisOrigen);
    s.setPaisDestino(paisDestino);
    s.setMotivoViaje(motivoViaje);
    return s;
  }
}
