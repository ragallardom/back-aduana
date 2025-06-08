package cl.duoc.sistema_aduanero.model;

import jakarta.persistence.*;

@Entity
@Table(name = "adjuntos_viaje_menores")
public class AdjuntoViajeMenores {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @Column(name = "nombre_original", nullable = false)
  private String nombreOriginal;

  @Column(name = "nombre_archivo", nullable = false)
  private String nombreArchivo;

  @Column(name = "ruta", nullable = false) private String ruta;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_solicitud", nullable = false)
  private SolicitudViajeMenores solicitud;

  public AdjuntoViajeMenores() {}

  public AdjuntoViajeMenores(
      Long id,
      String nombreOriginal,
      String nombreArchivo,
      String ruta,
      SolicitudViajeMenores solicitud) {
    this.id = id;
    this.nombreOriginal = nombreOriginal;
    this.nombreArchivo = nombreArchivo;
    this.ruta = ruta;
    this.solicitud = solicitud;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombreOriginal() {
    return nombreOriginal;
  }

  public void setNombreOriginal(String nombreOriginal) {
    this.nombreOriginal = nombreOriginal;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getRuta() {
    return ruta;
  }

  public void setRuta(String ruta) {
    this.ruta = ruta;
  }

  public SolicitudViajeMenores getSolicitud() {
    return solicitud;
  }

  public void setSolicitud(SolicitudViajeMenores solicitud) {
    this.solicitud = solicitud;
  }
}
