package cl.duoc.sistema_aduanero.repository;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoAdjuntoRepository
    extends JpaRepository<AdjuntoViajeMenores, Long> {}
