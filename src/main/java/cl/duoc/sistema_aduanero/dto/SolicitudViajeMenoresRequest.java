package cl.duoc.sistema_aduanero.dto;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO para la creación de una solicitud de viaje de menores.
 * Incluye los datos de la solicitud y los archivos adjuntos asociados.
 */
public class SolicitudViajeMenoresRequest {

    // Datos de la solicitud
    private String estado;
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

    // Información de los documentos adjuntos
    private List<String> tiposDocumento;
    private List<MultipartFile> archivos;

    public SolicitudViajeMenoresRequest() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public List<String> getTiposDocumento() {
        return tiposDocumento;
    }

    public void setTiposDocumento(List<String> tiposDocumento) {
        this.tiposDocumento = tiposDocumento;
    }

    public List<MultipartFile> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<MultipartFile> archivos) {
        this.archivos = archivos;
    }
}
