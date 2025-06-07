package cl.duoc.sistema_aduanero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "solicitudes_viaje_menores")
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudViajeMenores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Datos del menor
    @Column(name = "tipo_solicitud_menor", nullable = false)
    private String tipoSolicitudMenor;

    @Column(name = "nombre_menor", nullable = false)
    private String nombreMenor;

    @Column(name = "fecha_nacimiento_menor", nullable = false)
    private LocalDateTime fechaNacimientoMenor;

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

    @Column(name = "email_padre", nullable = false)
    private String emailPadre;

    // Detalles del viaje
    @Column(name = "fecha_viaje", nullable = false)
    private LocalDateTime fechaViaje;

    @Column(name = "numero_transporte", nullable = false)
    private String numeroTransporte;

    @Column(name = "pais_origen", nullable = false)
    private String paisOrigen;

    @Column(name = "pais_destino", nullable = false)
    private String paisDestino;

    @Column(name = "motivo_viaje", nullable = false)
    private String motivoViaje;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdjuntoViajeMenores> documentos = new ArrayList<>();

}