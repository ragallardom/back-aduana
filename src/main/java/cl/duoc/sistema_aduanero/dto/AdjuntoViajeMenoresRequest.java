package cl.duoc.sistema_aduanero.dto;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjuntoViajeMenoresRequest {
    private String nombreArchivo;
    private String ruta;

    public AdjuntoViajeMenores toEntity() {
        AdjuntoViajeMenores adj = new AdjuntoViajeMenores();
        adj.setNombreArchivo(nombreArchivo);
        adj.setRuta(ruta);
        return adj;
    }
}
