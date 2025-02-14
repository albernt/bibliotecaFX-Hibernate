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

    // Instancia del DAO para interactuar con la base de datos
    private SocioDAOImpl socioDAO = new SocioDAOImpl();

    // Campos de entrada en la vista
    @FXML
    private TextField nombreField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;

    // Tabla de socios
    @FXML
    private TableView<Socio> tablaSocios;
    @FXML
    private TableColumn<Socio, String> colNombre;
    @FXML
    private TableColumn<Socio, String> colDireccion;
    @FXML
    private TableColumn<Socio, String> colTelefono;

    // Este método se llama para pasar el Stage de la ventana principal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        configurarColumnasTabla();
        cargarSociosEnTabla();
    }

    private void configurarColumnasTabla() {
        // Configura las columnas de la tabla usando SimpleStringProperty para las propiedades de texto
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion()));
        colTelefono.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
    }

    @FXML
    private void cargarSociosEnTabla() {
        // Obtener la lista de socios desde el DAO
        List<Socio> socios = socioDAO.listarSocios();

        if (socios != null && !socios.isEmpty()) {
            tablaSocios.getItems().setAll(socios);  // Llenar la lista de socios en la tabla
        } else {
            tablaSocios.getItems().clear();  // Limpiar la tabla si no hay socios
            mostrarAlerta("No hay socios registrados.");
        }
    }

    // Método para manejar el botón "Volver"
    @FXML
    public void handleVolver() {
        try {
            cargarVista("main-view.fxml");  // Cargar la vista principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para manejar el botón "Añadir"
    @FXML
    public void handleAñadir() {
        String nombre = nombreField.getText();
        String direccion = direccionField.getText();
        String telefono = telefonoField.getText();

        // Verificar que los campos no estén vacíos
        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        // Crear un nuevo socio sin ID (asumimos que el ID es autoincremental)
        Socio socio = new Socio(nombre, direccion, telefono);

        // Mostrar valores para depuración
        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Teléfono: " + telefono);

        // Guardar en la base de datos
        socioDAO.agregarSocio(socio);
        mostrarAlerta("Socio añadido correctamente.");
        limpiarCampos();
        handleListar();  // Actualizar la lista de socios
    }

    // Método para manejar el botón "Modificar"
    @FXML
    public void handleModificar() {
        String nombre = nombreField.getText();
        String direccion = direccionField.getText();
        String telefono = telefonoField.getText();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        // Lógica para modificar el socio (buscar por nombre y modificar)
        Socio socio = socioDAO.buscarPorNombre(nombre);
        if (socio != null) {
            socio.setDireccion(direccion);
            socio.setTelefono(telefono);
            socioDAO.modificarSocio(socio);
            mostrarAlerta("Socio modificado correctamente.");
            limpiarCampos();
            handleListar();  // Actualizar la lista de socios
        } else {
            mostrarAlerta("Socio no encontrado.");
        }
    }

    // Método para manejar el botón "Buscar"
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

    // Método para manejar el botón "Eliminar"
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
            handleListar();  // Actualizar la lista de socios
        } else {
            mostrarAlerta("Socio no encontrado.");
        }
    }

    // Método para manejar el botón "Listar"
    @FXML
    public void handleListar() {
        List<Socio> socios = socioDAO.listarSocios();
        if (socios.isEmpty()) {
            mostrarAlerta("No hay socios registrados.");
        } else {
            // Llenar la tabla con los datos de los socios
            tablaSocios.getItems().clear();
            tablaSocios.getItems().addAll(socios);
        }
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        nombreField.clear();
        direccionField.clear();
        telefonoField.clear();
    }

    // Método para mostrar alerta de error o información
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para cargar la vista de gestión de socios
    private void cargarVista(String vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + vista));
        AnchorPane root = fxmlLoader.load();  // Cargar solo el root

        // Obtener el controlador de la vista cargada y pasarle el Stage
        Object controller = fxmlLoader.getController();
        if (controller instanceof MainController) {
            ((MainController) controller).setStage(stage); // Pasa el Stage
        }

        // Cambiar el contenido de la ventana (actualiza solo el root)
        stage.getScene().setRoot(root);
    }
}
