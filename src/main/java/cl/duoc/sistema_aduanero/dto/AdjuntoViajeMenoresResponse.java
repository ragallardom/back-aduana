package cl.duoc.sistema_aduanero.dto;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;

/** DTO para exponer la información de los adjuntos. */
public class AdjuntoViajeMenoresResponse {
    private Long id;
    private String nombreOriginal;
    private String nombreArchivo;
    private String ruta;

    public AdjuntoViajeMenoresResponse() {}

    public AdjuntoViajeMenoresResponse(Long id,
                                        String nombreOriginal,
                                        String nombreArchivo,
                                        String ruta) {
        this.id = id;
        this.nombreOriginal = nombreOriginal;
        this.nombreArchivo = nombreArchivo;
        this.ruta = ruta;
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

    public static AdjuntoViajeMenoresResponse fromEntity(AdjuntoViajeMenores adj) {
        if (adj == null) return null;
        return new AdjuntoViajeMenoresResponse(adj.getId(), adj.getNombreOriginal(), adj.getNombreArchivo(), adj.getRuta());
    }
}
