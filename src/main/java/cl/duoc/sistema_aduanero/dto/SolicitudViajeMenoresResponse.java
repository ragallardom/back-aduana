package cl.duoc.sistema_aduanero.dto;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudViajeMenoresResponse {
    private Long id;
    private String estado;
    private LocalDateTime fechaCreacion;
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
    private List<AdjuntoViajeMenoresResponse> documentos;

    public static SolicitudViajeMenoresResponse fromEntity(SolicitudViajeMenores s) {
        if (s == null) return null;
        SolicitudViajeMenoresResponse dto = new SolicitudViajeMenoresResponse();
        dto.setId(s.getId());
        dto.setEstado(s.getEstado());
        dto.setFechaCreacion(s.getFechaCreacion());
        dto.setTipoSolicitudMenor(s.getTipoSolicitudMenor());
        dto.setNombreMenor(s.getNombreMenor());
        dto.setFechaNacimientoMenor(s.getFechaNacimientoMenor());
        dto.setDocumentoMenor(s.getDocumentoMenor());
        dto.setNumeroDocumentoMenor(s.getNumeroDocumentoMenor());
        dto.setNacionalidadMenor(s.getNacionalidadMenor());
        dto.setNombrePadreMadre(s.getNombrePadreMadre());
        dto.setRelacionMenor(s.getRelacionMenor());
        dto.setDocumentoPadre(s.getDocumentoPadre());
        dto.setNumeroDocumentoPadre(s.getNumeroDocumentoPadre());
        dto.setTelefonoPadre(s.getTelefonoPadre());
        dto.setEmailPadre(s.getEmailPadre());
        dto.setFechaViaje(s.getFechaViaje());
        dto.setNumeroTransporte(s.getNumeroTransporte());
        dto.setPaisOrigen(s.getPaisOrigen());
        dto.setPaisDestino(s.getPaisDestino());
        dto.setMotivoViaje(s.getMotivoViaje());
        if (s.getDocumentos() != null) {
            dto.setDocumentos(s.getDocumentos().stream()
                .map(AdjuntoViajeMenoresResponse::fromEntity)
                .collect(Collectors.toList()));
        }
        return dto;
    }
}
