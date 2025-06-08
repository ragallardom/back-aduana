package cl.duoc.sistema_aduanero.service;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.repository.SolicitudAduanaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SolicitudAduanaService {

  private final SolicitudAduanaRepository repository;

  public SolicitudAduanaService(SolicitudAduanaRepository repository) {
    this.repository = repository;
  }

  public SolicitudViajeMenores crearSolicitud(SolicitudViajeMenores solicitud) {
    return repository.save(solicitud);
  }

  public List<SolicitudViajeMenores> obtenerTodas() {
    return repository.findAll();
  }

  public Optional<SolicitudViajeMenores> actualizarEstado(Long id,
                                                          String nuevoEstado) {
    Optional<SolicitudViajeMenores> opt = repository.findById(id);
    if (opt.isEmpty()) {
      return Optional.empty();
    }
    SolicitudViajeMenores solicitud = opt.get();
    solicitud.setEstado(nuevoEstado);
    return Optional.of(repository.save(solicitud));
  }

  public Optional<SolicitudViajeMenores> obtenerPorId(Long id) {
    return repository.findById(id);
  }
}
