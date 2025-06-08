package cl.duoc.sistema_aduanero.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.duoc.sistema_aduanero.dto.SolicitudViajeMenoresRequest;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.service.DocumentoAdjuntoService;
import cl.duoc.sistema_aduanero.service.SolicitudAduanaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SolicitudAduanaController.class)
class SolicitudAduanaControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private SolicitudAduanaService solicitudService;

  @MockBean private DocumentoAdjuntoService adjuntoService;

  @Test
  void crearSolicitud() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(1L);

    Mockito
        .when(solicitudService.crearSolicitud(any(SolicitudViajeMenores.class)))
        .thenReturn(solicitud);

    SolicitudViajeMenoresRequest req = new SolicitudViajeMenoresRequest();

    mockMvc
        .perform(post("/api/solicitudes")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L));
  }

  @Test
  void listarSolicitudes() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(2L);
    Mockito.when(solicitudService.obtenerTodas())
        .thenReturn(Collections.singletonList(solicitud));

    mockMvc.perform(get("/api/solicitudes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(2L));
  }

  @Test
  void crearConAdjunto() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(3L);

    Mockito
        .when(solicitudService.crearSolicitud(any(SolicitudViajeMenores.class)))
        .thenReturn(solicitud);
    Mockito.when(adjuntoService.guardarArchivo(any(), any(), any()))
        .thenReturn(null);

    MockMultipartFile file = new MockMultipartFile(
        "archivo", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hola".getBytes());

    mockMvc
        .perform(multipart("/api/solicitudes/adjuntar")
                     .file(file)
                     .param("nombreSolicitante", "Juan")
                     .param("tipoDocumento", "Pasaporte")
                     .param("numeroDocumento", "123")
                     .param("tipoAdjunto", "Doc")
                     .param("motivo", "Viaje")
                     .param("paisOrigen", "CL"))
        .andExpect(status().isCreated());
  }
}
