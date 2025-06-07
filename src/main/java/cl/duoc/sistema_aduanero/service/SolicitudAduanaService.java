package cl.duoc.sistema_aduanero.service;

import cl.duoc.sistema_aduanero.model.SolicitudAduana;
import cl.duoc.sistema_aduanero.repository.SolicitudAduanaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SolicitudAduanaService {

    private final SolicitudAduanaRepository repository;

    public SolicitudAduanaService(SolicitudAduanaRepository repository) {
        this.repository = repository;
    }

    public SolicitudAduana crearSolicitud(SolicitudAduana solicitud) {
        return repository.save(solicitud);
    }

    public List<SolicitudAduana> obtenerTodas() {
        return repository.findAll();
    }

    public SolicitudAduana actualizarEstado(Long id, String nuevoEstado) {
        SolicitudAduana solicitud = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        solicitud.setEstado(nuevoEstado);
        return repository.save(solicitud);
    }

    public SolicitudAduana obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    }
}
