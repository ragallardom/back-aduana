package cl.duoc.sistema_aduanero.dto;

/**
 * DTO de respuesta para un adjunto asociado a una solicitud de viaje de menores.
 */
public class AdjuntoViajeMenoresResponse {

    private Long id;
    private String nombreOriginal;
    private String nombreArchivo;
    private String ruta;

    public AdjuntoViajeMenoresResponse() {
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
}
