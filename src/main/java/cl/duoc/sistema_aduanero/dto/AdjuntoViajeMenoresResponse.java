package cl.duoc.sistema_aduanero.dto;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjuntoViajeMenoresResponse {
    private Long id;
    private String nombreOriginal;
    private String nombreArchivo;
    private String ruta;

    public static AdjuntoViajeMenoresResponse fromEntity(AdjuntoViajeMenores adj) {
        if (adj == null) return null;
        return new AdjuntoViajeMenoresResponse(adj.getId(), adj.getNombreOriginal(), adj.getNombreArchivo(), adj.getRuta());
    }
}
