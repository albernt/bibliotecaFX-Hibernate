package controllers;

import DAO.PrestamoDAOImpl;
import entities.Prestamo;
import entities.Libro;
import entities.Socio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PrestamoController {

    @FXML
    private TextField socioIdField, libroIdField;

    @FXML
    private DatePicker fechaPrestamoField, fechaDevolucionField;

    @FXML
    private TableView<Prestamo> tablaPrestamos;

    @FXML
    private TableColumn<Prestamo, String> colLibro, colSocio, colFechaPrestamo, colFechaDevolucion;

    private Stage stage;
    private final PrestamoDAOImpl prestamoDAO = new PrestamoDAOImpl();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        configurarColumnasTabla();
        cargarPrestamosActivos();
    }

    private void configurarColumnasTabla() {
        colLibro.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
        colSocio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSocio().getNombre()));
        colFechaPrestamo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));
        colFechaDevolucion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFechaDevolucion() != null ? cellData.getValue().getFechaDevolucion().toString() : "No devuelto"));
    }

    @FXML
    private void cargarPrestamosActivos() {
        List<Prestamo> prestamos = prestamoDAO.listarPrestamosActivos();
        if (prestamos != null) {
            tablaPrestamos.setItems(javafx.collections.FXCollections.observableArrayList(prestamos));
        } else {
            tablaPrestamos.setItems(javafx.collections.FXCollections.observableArrayList());
        }
    }

    @FXML
    private void handleRegistrarPrestamo() {
        String socioId = socioIdField.getText();
        String libroId = libroIdField.getText();
        Date fechaPrestamo = java.sql.Date.valueOf(fechaPrestamoField.getValue());
        Date fechaDevolucion = java.sql.Date.valueOf(fechaDevolucionField.getValue());

        if (socioId.isEmpty() || libroId.isEmpty() || fechaPrestamo == null || fechaDevolucion == null) {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }

        try {
            Long socioIdLong = Long.parseLong(socioId);
            Long libroIdLong = Long.parseLong(libroId);
            prestamoDAO.registrarPrestamo(libroIdLong, socioIdLong, fechaPrestamo, fechaDevolucion);
            cargarPrestamosActivos();
            mostrarAlerta("Éxito", "Préstamo registrado correctamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los IDs deben ser números válidos.");
        }
    }

    @FXML
    private void handleBuscarHistorialPrestamos() {
        String socioId = socioIdField.getText();
        if (socioId.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un ID de socio para buscar.");
            return;
        }
        try {
            Long socioIdLong = Long.parseLong(socioId);
            List<Prestamo> prestamos = prestamoDAO.listarHistorialPrestamos(socioIdLong);
            if (prestamos.isEmpty()) {
                mostrarAlerta("Información", "No se encontraron préstamos para este socio.");
            } else {
                tablaPrestamos.setItems(javafx.collections.FXCollections.observableArrayList(prestamos));
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El ID del socio debe ser un número válido.");
        }
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
