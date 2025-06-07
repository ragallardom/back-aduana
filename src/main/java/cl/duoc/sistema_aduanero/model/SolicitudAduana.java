package cl.duoc.sistema_aduanero.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class SolicitudAduana {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombreSolicitante;

    @NotBlank
    private String tipoDocumento;

    @NotBlank
    private String numeroDocumento;

    @NotBlank
    private String motivo;

    @NotBlank
    private String paisOrigen;

    private String estado = "PENDIENTE";

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private List<DocumentoAdjunto> documentos;

}
