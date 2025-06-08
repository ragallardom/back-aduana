package cl.duoc.sistema_aduanero.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.repository.DocumentoAdjuntoRepository;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class DocumentoAdjuntoServiceTest {

  @Mock private DocumentoAdjuntoRepository documentoAdjuntoRepository;

  @InjectMocks private DocumentoAdjuntoService service;

  private Path tempDir;

  @BeforeEach
  void setUp() throws Exception {
    tempDir = Files.createTempDirectory("uploads");
    ReflectionTestUtils.setField(service, "BASE_PATH", tempDir.toString());
  }

  @AfterEach
  void tearDown() throws Exception {
    Files.walk(tempDir)
        .map(Path::toFile)
        .sorted((a, b) -> - a.compareTo(b))
        .forEach(File::delete);
  }

  @Test
  void guardarArchivo() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(10L);

    MockMultipartFile file = new MockMultipartFile(
        "file", "doc.pdf", "application/pdf", "data".getBytes());

    when(documentoAdjuntoRepository.save(any(AdjuntoViajeMenores.class)))
        .thenAnswer(inv -> inv.getArgument(0));

    AdjuntoViajeMenores adjunto =
        service.guardarArchivo(solicitud, "Documento Nacional", file);

    verify(documentoAdjuntoRepository, times(1))
        .save(any(AdjuntoViajeMenores.class));
    assertNotNull(adjunto);
    assertEquals("documento_nacional.pdf", adjunto.getNombreArchivo());
    assertTrue(Files.exists(Paths.get(adjunto.getRuta())));
  }
}
