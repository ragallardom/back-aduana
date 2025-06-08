package cl.duoc.sistema_aduanero.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO para recibir los adjuntos de una solicitud de viaje de menores.
 * Permite enviar entre tres y cinco archivos por solicitud.
 */
public class AdjuntoViajeMenoresRequest {

    private List<String> tiposDocumento;
    private List<MultipartFile> archivos;

    public AdjuntoViajeMenoresRequest() {
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
