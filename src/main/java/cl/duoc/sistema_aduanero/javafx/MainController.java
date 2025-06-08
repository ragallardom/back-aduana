package cl.duoc.sistema_aduanero.javafx;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
 * Controlador para la interfaz JavaFX del Sistema Aduanero.
 * Maneja:
 *  - Registro de solicitudes (con multipart/form-data).
 *  - Listado de solicitudes.
 *  - Aprobación/Rechazo de solicitudes.
 */
public class MainController {

  // ======================
  // Campos de FXML (Registrar Solicitud)
  // ======================
  @FXML private TextField tfNombre;
  @FXML private TextField tfNumero;
  @FXML private TextField tfTipoDocEnt;
  @FXML private TextField tfPais;
  @FXML private TextArea taMotivo;
  @FXML private TextField tfTipoAdj;
  @FXML private Label lblArchivoSeleccionado;

  private File archivoSeleccionado;

  // ======================
  // Campos de FXML (Revisar Solicitudes)
  // ======================
  @FXML private TableView<SolicitudViajeMenores> tableSolicitudes;
  @FXML private TableColumn<SolicitudViajeMenores, Long> colId;
  @FXML private TableColumn<SolicitudViajeMenores, String> colNombre;
  @FXML private TableColumn<SolicitudViajeMenores, String> colEstado;
  @FXML private TableColumn<SolicitudViajeMenores, String> colFecha;

  // Cliente HTTP reutilizable
  private final HttpClient httpClient = HttpClient.newHttpClient();

  @FXML
  private void initialize() {
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNombre.setCellValueFactory(
        new PropertyValueFactory<>("nombrePadreMadre"));
    colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
  }

  // ======================
  // Handler: Salir de la aplicación
  // ======================
  @FXML
  private void handleSalir() {
    System.exit(0);
  }

  // ======================
  // Handler: Seleccionar archivo para adjuntar
  // ======================
  @FXML
  private void handleSeleccionarArchivo() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Selecciona documento a adjuntar");
    archivoSeleccionado = fileChooser.showOpenDialog(null);
    if (archivoSeleccionado != null) {
      lblArchivoSeleccionado.setText(archivoSeleccionado.getName());
    } else {
      lblArchivoSeleccionado.setText("(ninguno)");
    }
  }

  // ======================
  // Handler: Enviar nueva solicitud con adjunto
  // ======================
  @FXML
  private void handleEnviarSolicitud() {
    try {
      // 1) Obtener valores de los campos
      String nombre = tfNombre.getText();
      String numero = tfNumero.getText();
      String tipoDocumentoEnt = tfTipoDocEnt.getText();
      String pais = tfPais.getText();
      String motivo = taMotivo.getText();
      String tipoAdj = tfTipoAdj.getText();

      // Validar que hayan ingresado datos mínimos
      if (nombre.isBlank() || numero.isBlank() || tipoDocumentoEnt.isBlank() ||
          pais.isBlank() || motivo.isBlank() || tipoAdj.isBlank()) {
        showAlert(Alert.AlertType.WARNING,
                  "Todos los campos de texto son obligatorios.");
        return;
      }
      if (archivoSeleccionado == null) {
        showAlert(Alert.AlertType.WARNING,
                  "Debes seleccionar un archivo para adjuntar.");
        return;
      }

      // 2) Construir el body multipart/form-data usando MultipartBodyPublisher
      MultipartBodyPublisher bodyPublisher =
          MultipartBodyPublisher.builder()
              // Nombres de los campos compatibles con la API REST
              .addPart("nombrePadreMadre", nombre)
              .addPart("numeroDocumentoPadre", numero)
              .addPart("documentoPadre", tipoDocumentoEnt)
              .addPart("paisOrigen", pais)
              .addPart("motivoViaje", motivo)
              .addPart("tiposDocumento", tipoAdj)
              .addFilePart("archivos", archivoSeleccionado.toPath())
              .build();

      // 3) Obtener el boundary generado por el builder
      String boundary = bodyPublisher.getBoundary();

      // 4) Crear la petición HTTP POST
        HttpRequest request =
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/solicitudes"))
                .header("Content-Type",
                        "multipart/form-data; boundary=" + boundary)
                .POST(bodyPublisher)
                .build();

      // 5) Enviar petición y obtener respuesta
      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
        showAlert(Alert.AlertType.INFORMATION,
                  "Solicitud enviada correctamente.");
        limpiarFormulario();
      } else {
        showAlert(Alert.AlertType.ERROR, "Error al enviar solicitud (" +
                                             response.statusCode() + "):\n" +
                                             response.body());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      showAlert(Alert.AlertType.ERROR, "Exception: " + ex.getMessage());
    }
  }

  // ======================
  // Handler: Refrescar lista de solicitudes
  // ======================
  @FXML
  private void handleRefrescar() {
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create("http://localhost:8080/api/solicitudes"))
              .GET()
              .build();

      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        // Convertir JSON a List<SolicitudViajeMenores>
        List<SolicitudViajeMenores> lista = JsonUtils.fromJsonList(
            response.body(), SolicitudViajeMenores.class);
        ObservableList<SolicitudViajeMenores> obsList =
            FXCollections.observableArrayList(lista);
        tableSolicitudes.setItems(obsList);
      } else {
        showAlert(Alert.AlertType.ERROR,
                  "Error al cargar solicitudes: HTTP " + response.statusCode());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      showAlert(Alert.AlertType.ERROR, "Exception: " + ex.getMessage());
    }
  }

  // ======================
  // Handler: Ver detalle de la solicitud seleccionada
  // ======================
  @FXML
  private void handleVerDetalle() {
    SolicitudViajeMenores sel =
        tableSolicitudes.getSelectionModel().getSelectedItem();
    if (sel == null) {
      showAlert(Alert.AlertType.WARNING, "Selecciona una solicitud primero.");
      return;
    }
    // Por simplicidad, muestra un diálogo con el JSON completo de la solicitud
    try {
      String jsonDetalle = JsonUtils.toJson(sel);
      showAlert(Alert.AlertType.INFORMATION, jsonDetalle);
    } catch (Exception e) {
      e.printStackTrace();
      showAlert(Alert.AlertType.ERROR,
                "Error al convertir a JSON: " + e.getMessage());
    }
  }

  // ======================
  // Handler: Aprobar solicitud seleccionada
  // ======================
  @FXML
  private void handleAprobar() {
    SolicitudViajeMenores sel =
        tableSolicitudes.getSelectionModel().getSelectedItem();
    if (sel == null) {
      showAlert(Alert.AlertType.WARNING, "Selecciona una solicitud primero.");
      return;
    }
    cambiarEstado(sel.getId(), "APROBADA");
  }

  // ======================
  // Handler: Rechazar solicitud seleccionada
  // ======================
  @FXML
  private void handleRechazar() {
    SolicitudViajeMenores sel =
        tableSolicitudes.getSelectionModel().getSelectedItem();
    if (sel == null) {
      showAlert(Alert.AlertType.WARNING, "Selecciona una solicitud primero.");
      return;
    }
    cambiarEstado(sel.getId(), "RECHAZADA");
  }

  // ======================
  // Método auxiliar para cambiar el estado de una solicitud
  // ======================
  private void cambiarEstado(Long id, String nuevoEstado) {
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create("http://localhost:8080/api/solicitudes/" + id +
                              "/estado?estado=" + nuevoEstado))
              .PUT(HttpRequest.BodyPublishers.noBody())
              .build();

      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() == 200) {
        showAlert(Alert.AlertType.INFORMATION,
                  "Estado actualizado a " + nuevoEstado);
        handleRefrescar(); // Refrescar la lista tras el cambio
      } else {
        showAlert(Alert.AlertType.ERROR, "Error al cambiar estado (" +
                                             response.statusCode() +
                                             "): " + response.body());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      showAlert(Alert.AlertType.ERROR, "Exception: " + ex.getMessage());
    }
  }

  // ======================
  // Limpia los campos del formulario de solicitud
  // ======================
  private void limpiarFormulario() {
    tfNombre.clear();
    tfNumero.clear();
    tfTipoDocEnt.clear();
    tfPais.clear();
    taMotivo.clear();
    tfTipoAdj.clear();
    lblArchivoSeleccionado.setText("(ninguno)");
    archivoSeleccionado = null;
  }

  // ======================
  // Muestra un Alert dialog con el tipo y mensaje
  // ======================
  private void showAlert(Alert.AlertType tipo, String mensaje) {
    Alert alert = new Alert(tipo, mensaje, ButtonType.OK);
    alert.showAndWait();
  }
}
