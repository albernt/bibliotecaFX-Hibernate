package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import DAO.SocioDAOImpl;
import entities.Socio;

import java.io.IOException;
import java.util.List;

public class SocioController {

    private Stage stage;

    private SocioDAOImpl socioDAO = new SocioDAOImpl();

    @FXML
    private TextField nombreField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;

    @FXML
    private TableView<Socio> tablaSocios;
    @FXML
    private TableColumn<Socio, String> colNombre;
    @FXML
    private TableColumn<Socio, String> colDireccion;
    @FXML
    private TableColumn<Socio, String> colTelefono;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        configurarColumnasTabla();
        cargarSociosEnTabla();
    }

    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion()));
        colTelefono.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
    }

    @FXML
    private void cargarSociosEnTabla() {
        List<Socio> socios = socioDAO.listarSocios();

        if (socios != null && !socios.isEmpty()) {
            tablaSocios.getItems().setAll(socios);
        } else {
            tablaSocios.getItems().clear();
            mostrarAlerta("No hay socios registrados.");
        }
    }

    @FXML
    public void handleVolver() {
        try {
            cargarVista("main-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAñadir() {
        String nombre = nombreField.getText();
        String direccion = direccionField.getText();
        String telefono = telefonoField.getText();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        Socio socio = new Socio(nombre, direccion, telefono);

        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Teléfono: " + telefono);

        // Guardar en la base de datos
        socioDAO.agregarSocio(socio);
        mostrarAlerta("Socio añadido correctamente.");
        limpiarCampos();
        handleListar();
    }

    @FXML
    public void handleModificar() {
        String nombre = nombreField.getText();
        String direccion = direccionField.getText();
        String telefono = telefonoField.getText();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        Socio socio = socioDAO.buscarPorNombre(nombre);
        if (socio != null) {
            socio.setDireccion(direccion);
            socio.setTelefono(telefono);
            socioDAO.modificarSocio(socio);
            mostrarAlerta("Socio modificado correctamente.");
            limpiarCampos();
            handleListar();
        } else {
            mostrarAlerta("Socio no encontrado.");
        }
    }

    @FXML
    public void handleBuscar() {
        String nombre = nombreField.getText();

        if (nombre.isEmpty()) {
            mostrarAlerta("Introduce un nombre para buscar.");
            return;
        }

        Socio socio = socioDAO.buscarPorNombre(nombre);
        if (socio != null) {
            nombreField.setText(socio.getNombre());
            direccionField.setText(socio.getDireccion());
            telefonoField.setText(socio.getTelefono());
        } else {
            mostrarAlerta("Socio no encontrado.");
        }
    }

    @FXML
    public void handleEliminar() {
        String nombre = nombreField.getText();

        if (nombre.isEmpty()) {
            mostrarAlerta("Introduce un nombre para eliminar.");
            return;
        }

        Socio socio = socioDAO.buscarPorNombre(nombre);
        if (socio != null) {
            socioDAO.eliminarSocio(socio.getId());
            mostrarAlerta("Socio eliminado correctamente.");
            limpiarCampos();
            handleListar();
        } else {
            mostrarAlerta("Socio no encontrado.");
        }
    }

    @FXML
    public void handleListar() {
        List<Socio> socios = socioDAO.listarSocios();
        if (socios.isEmpty()) {
            mostrarAlerta("No hay socios registrados.");
        } else {
            tablaSocios.getItems().clear();
            tablaSocios.getItems().addAll(socios);
        }
    }

    private void limpiarCampos() {
        nombreField.clear();
        direccionField.clear();
        telefonoField.clear();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarVista(String vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + vista));
        AnchorPane root = fxmlLoader.load();

        Object controller = fxmlLoader.getController();
        if (controller instanceof MainController) {
            ((MainController) controller).setStage(stage);
        }

        stage.getScene().setRoot(root);
    }
}
