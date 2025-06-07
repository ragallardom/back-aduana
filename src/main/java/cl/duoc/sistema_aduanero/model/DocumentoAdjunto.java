package cl.duoc.sistema_aduanero.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class DocumentoAdjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    @JsonIgnore
    private SolicitudAduana solicitud;

    private String nombreArchivo;
    private String tipoDocumento;
    private String rutaArchivo;
    private LocalDateTime fechaSubida;
}
