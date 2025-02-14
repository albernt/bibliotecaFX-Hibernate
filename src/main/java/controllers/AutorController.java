package controllers;

import DAO.AutorDAOImpl;
import entities.Autor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class AutorController {

    private Stage stage;
    private final AutorDAOImpl autorDAO = new AutorDAOImpl();
    private final ObservableList<Autor> listaAutores = FXCollections.observableArrayList();

    @FXML
    private TextField idField, nombreField, nacionalidadField;

    @FXML
    private TableView<Autor> tablaAutores;

    @FXML
    private TableColumn<Autor, Long> colId;

    @FXML
    private TableColumn<Autor, String> colNombre, colNacionalidad;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));

        cargarAutoresEnTabla();
    }

    @FXML
    private void cargarAutoresEnTabla() {
        List<Autor> autores = autorDAO.listarTodosLosAutores();
        if (autores != null) {
            listaAutores.setAll(autores);
        } else {
            listaAutores.clear();
        }
        tablaAutores.setItems(listaAutores);
    }

    @FXML
    private void handleAgregarAutor() {
        String nombre = nombreField.getText();
        String nacionalidad = nacionalidadField.getText();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        Autor nuevoAutor = new Autor(null, nombre, nacionalidad);
        if (autorDAO.agregarAutor(nuevoAutor)) {
            listaAutores.add(nuevoAutor);
            limpiarCampos();
            mostrarAlerta("Éxito", "Autor agregado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo agregar el autor.");
        }
    }

    @FXML
    private void handleModificarAutor() {
        Autor autorSeleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (autorSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un autor para modificar.");
            return;
        }

        String nombre = nombreField.getText();
        String nacionalidad = nacionalidadField.getText();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        autorSeleccionado.setNombre(nombre);
        autorSeleccionado.setNacionalidad(nacionalidad);

        if (autorDAO.modificarAutor(autorSeleccionado)) {
            cargarAutoresEnTabla();
            mostrarAlerta("Éxito", "Autor modificado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo modificar el autor.");
        }
    }

    @FXML
    private void handleEliminarAutor() {
        Autor autorSeleccionado = tablaAutores.getSelectionModel().getSelectedItem();
        if (autorSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un autor para eliminar.");
            return;
        }

        if (autorDAO.eliminarAutor(autorSeleccionado.getId())) {
            listaAutores.remove(autorSeleccionado);
            mostrarAlerta("Éxito", "Autor eliminado correctamente.");
        } else {
            mostrarAlerta("Error", "No se pudo eliminar el autor.");
        }
    }

    @FXML
    private void handleBuscarAutor() {
        String nombre = nombreField.getText();
        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un nombre para buscar.");
            return;
        }

        List<Autor> autores = autorDAO.buscarAutoresPorNombre(nombre);
        if (autores != null && !autores.isEmpty()) {
            listaAutores.setAll(autores);
        } else {
            mostrarAlerta("Información", "No se encontraron autores con ese nombre.");
            listaAutores.clear();
        }
    }

    private void limpiarCampos() {
        idField.clear();
        nombreField.clear();
        nacionalidadField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void handleVolver() {
        try {
            System.out.println("Volviendo a la vista principal...");
            cargarVista("main-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + vista));
        AnchorPane root = fxmlLoader.load();

        if (fxmlLoader.getController() instanceof MainController) {
            MainController controller = (MainController) fxmlLoader.getController();
            controller.setStage(stage);
        }

        stage.getScene().setRoot(root);
    }
}