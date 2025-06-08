package cl.duoc.sistema_aduanero.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "solicitudes_viaje_menores")
public class SolicitudViajeMenores {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @Column(name = "estado", nullable = false) private String estado;

  @Column(name = "fecha_creacion", nullable = false)
  private LocalDateTime fechaCreacion = LocalDateTime.now();

  // Datos del menor
  @Column(name = "tipo_solicitud_menor", nullable = false)
  private String tipoSolicitudMenor;

  @Column(name = "nombre_menor", nullable = false) private String nombreMenor;

  @Column(name = "fecha_nacimiento_menor", nullable = false)
  private LocalDate fechaNacimientoMenor;

  @Column(name = "documento_menor", nullable = false)
  private String documentoMenor;

  @Column(name = "numero_documento_menor", nullable = false)
  private String numeroDocumentoMenor;

  @Column(name = "nacionalidad_menor", nullable = false)
  private String nacionalidadMenor;

  // Datos del padre o tutor
  @Column(name = "nombre_padre_madre", nullable = false)
  private String nombrePadreMadre;

  @Column(name = "relacion_menor", nullable = false)
  private String relacionMenor;

  @Column(name = "documento_padre", nullable = false)
  private String documentoPadre;

  @Column(name = "numero_documento_padre", nullable = false)
  private String numeroDocumentoPadre;

  @Column(name = "telefono_padre", nullable = false)
  private String telefonoPadre;

  @Column(name = "email_padre", nullable = false) private String emailPadre;

  // Detalles del viaje
  @Column(name = "fecha_viaje", nullable = false)
  private LocalDate fechaViaje;

  @Column(name = "numero_transporte", nullable = false)
  private String numeroTransporte;

  @Column(name = "pais_origen", nullable = false) private String paisOrigen;

  @Column(name = "pais_destino", nullable = false) private String paisDestino;

  @Column(name = "motivo_viaje", nullable = false) private String motivoViaje;

  @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL,
             orphanRemoval = true)
  private List<AdjuntoViajeMenores> documentos = new ArrayList<>();

  public SolicitudViajeMenores() {}

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

  public List<AdjuntoViajeMenores> getDocumentos() {
    return documentos;
  }

  public void setDocumentos(List<AdjuntoViajeMenores> documentos) {
    this.documentos = documentos;
  }
}