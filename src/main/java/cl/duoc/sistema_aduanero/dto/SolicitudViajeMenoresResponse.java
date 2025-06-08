package cl.duoc.sistema_aduanero.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta que expone la información de una solicitud de viaje de menores
 * junto con sus documentos adjuntos.
 */
public class SolicitudViajeMenoresResponse {

    private Long id;
    private String estado;
    private LocalDateTime fechaCreacion;
    private String tipoSolicitudMenor;
    private String nombreMenor;
    private LocalDate fechaNacimientoMenor;
    private String documentoMenor;
    private String numeroDocumentoMenor;
    private String nacionalidadMenor;
    private String nombrePadreMadre;
    private String relacionMenor;
    private String documentoPadre;
    private String numeroDocumentoPadre;
    private String telefonoPadre;
    private String emailPadre;
    private LocalDate fechaViaje;
    private String numeroTransporte;
    private String paisOrigen;
    private String paisDestino;
    private String motivoViaje;
    private List<AdjuntoViajeMenoresResponse> documentos;

    public SolicitudViajeMenoresResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoSolicitudMenor() {
        return tipoSolicitudMenor;
    }

    public void setTipoSolicitudMenor(String tipoSolicitudMenor) {
        this.tipoSolicitudMenor = tipoSolicitudMenor;
    }

    public String getNombreMenor() {
        return nombreMenor;
    }

    public void setNombreMenor(String nombreMenor) {
        this.nombreMenor = nombreMenor;
    }

    public LocalDate getFechaNacimientoMenor() {
        return fechaNacimientoMenor;
    }

    public void setFechaNacimientoMenor(LocalDate fechaNacimientoMenor) {
        this.fechaNacimientoMenor = fechaNacimientoMenor;
    }

    public String getDocumentoMenor() {
        return documentoMenor;
    }

    public void setDocumentoMenor(String documentoMenor) {
        this.documentoMenor = documentoMenor;
    }

    public String getNumeroDocumentoMenor() {
        return numeroDocumentoMenor;
    }

    public void setNumeroDocumentoMenor(String numeroDocumentoMenor) {
        this.numeroDocumentoMenor = numeroDocumentoMenor;
    }

    public String getNacionalidadMenor() {
        return nacionalidadMenor;
    }

    public void setNacionalidadMenor(String nacionalidadMenor) {
        this.nacionalidadMenor = nacionalidadMenor;
    }

    public String getNombrePadreMadre() {
        return nombrePadreMadre;
    }

    public void setNombrePadreMadre(String nombrePadreMadre) {
        this.nombrePadreMadre = nombrePadreMadre;
    }

    public String getRelacionMenor() {
        return relacionMenor;
    }

    public void setRelacionMenor(String relacionMenor) {
        this.relacionMenor = relacionMenor;
    }

    public String getDocumentoPadre() {
        return documentoPadre;
    }

    public void setDocumentoPadre(String documentoPadre) {
        this.documentoPadre = documentoPadre;
    }

    public String getNumeroDocumentoPadre() {
        return numeroDocumentoPadre;
    }

    public void setNumeroDocumentoPadre(String numeroDocumentoPadre) {
        this.numeroDocumentoPadre = numeroDocumentoPadre;
    }

    public String getTelefonoPadre() {
        return telefonoPadre;
    }

    public void setTelefonoPadre(String telefonoPadre) {
        this.telefonoPadre = telefonoPadre;
    }

    public String getEmailPadre() {
        return emailPadre;
    }

    public void setEmailPadre(String emailPadre) {
        this.emailPadre = emailPadre;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public String getNumeroTransporte() {
        return numeroTransporte;
    }

    public void setNumeroTransporte(String numeroTransporte) {
        this.numeroTransporte = numeroTransporte;
    }

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public String getMotivoViaje() {
        return motivoViaje;
    }

    public void setMotivoViaje(String motivoViaje) {
        this.motivoViaje = motivoViaje;
    }

    public List<AdjuntoViajeMenoresResponse> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<AdjuntoViajeMenoresResponse> documentos) {
        this.documentos = documentos;
    }
}
