package cl.duoc.sistema_aduanero.repository;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudAduanaRepository
    extends JpaRepository<SolicitudViajeMenores, Long> {}
