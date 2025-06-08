package cl.duoc.sistema_aduanero.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.service.DocumentoAdjuntoService;
import cl.duoc.sistema_aduanero.service.SolicitudAduanaService;
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

  @MockBean private SolicitudAduanaService solicitudService;

  @MockBean private DocumentoAdjuntoService adjuntoService;

  @Test
  void crearConAdjunto() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(3L);

    Mockito
        .when(solicitudService.crearSolicitud(any(SolicitudViajeMenores.class)))
        .thenReturn(solicitud);
    Mockito
        .when(adjuntoService.guardarAdjuntos(any(), any(), any()))
        .thenReturn(Collections.emptyList());

    MockMultipartFile file1 = new MockMultipartFile(
        "archivos", "a.txt", MediaType.TEXT_PLAIN_VALUE, "1".getBytes());
    MockMultipartFile file2 = new MockMultipartFile(
        "archivos", "b.txt", MediaType.TEXT_PLAIN_VALUE, "2".getBytes());
    MockMultipartFile file3 = new MockMultipartFile(
        "archivos", "c.txt", MediaType.TEXT_PLAIN_VALUE, "3".getBytes());

    mockMvc
        .perform(multipart("/api/solicitudes")
                     .file(file1)
                     .file(file2)
                     .file(file3)
        .param("estado", "NUEVA"))
        .andExpect(status().isOk());
  }

  @Test
  void crearSinEstadoUsaPendiente() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(4L);

    Mockito
        .when(solicitudService.crearSolicitud(any(SolicitudViajeMenores.class)))
        .thenReturn(solicitud);
    Mockito
        .when(adjuntoService.guardarAdjuntos(any(), any(), any()))
        .thenReturn(Collections.emptyList());

    mockMvc
        .perform(multipart("/api/solicitudes"))
        .andExpect(status().isOk());

    ArgumentCaptor<SolicitudViajeMenores> captor =
        ArgumentCaptor.forClass(SolicitudViajeMenores.class);
    verify(solicitudService).crearSolicitud(captor.capture());
    assertEquals("PENDIENTE", captor.getValue().getEstado());
  }

  @Test
  void entradaFijaDestinoChile() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(5L);

    Mockito
        .when(solicitudService.crearSolicitud(any(SolicitudViajeMenores.class)))
        .thenReturn(solicitud);

    mockMvc
        .perform(multipart("/api/solicitudes")
                     .param("tipoSolicitudMenor", "Entrada")
                     .param("paisDestino", "Peru"))
        .andExpect(status().isOk());

    ArgumentCaptor<SolicitudViajeMenores> captor =
        ArgumentCaptor.forClass(SolicitudViajeMenores.class);
    verify(solicitudService).crearSolicitud(captor.capture());
    assertEquals("Chile", captor.getValue().getPaisDestino());
  }

  @Test
  void salidaFijaOrigenChile() throws Exception {
    SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
    solicitud.setId(6L);

    Mockito
        .when(solicitudService.crearSolicitud(any(SolicitudViajeMenores.class)))
        .thenReturn(solicitud);

    mockMvc
        .perform(multipart("/api/solicitudes")
                     .param("tipoSolicitudMenor", "Salida")
                     .param("paisOrigen", "Argentina"))
        .andExpect(status().isOk());

    ArgumentCaptor<SolicitudViajeMenores> captor =
        ArgumentCaptor.forClass(SolicitudViajeMenores.class);
    verify(solicitudService).crearSolicitud(captor.capture());
    assertEquals("Chile", captor.getValue().getPaisOrigen());
  }
}
