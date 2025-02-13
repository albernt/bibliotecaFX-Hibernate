package controllers;

import DAO.LibroDAOImpl;
import entities.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LibroController {

    @FXML
    private TextField tituloField, isbnField, autorField, anoPublicacionField, editorialField;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> colTitulo, colISBN, colAutor, colEditorial;

    @FXML
    private TableColumn<Libro, Integer> colAno;

    private Stage stage;
    private final LibroDAOImpl libroDAO = new LibroDAOImpl();
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        configurarColumnasTabla();
        cargarLibrosEnTabla();
    }

    private void configurarColumnasTabla() {
        colTitulo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));
        colISBN.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsbn()));
        colAutor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAutor()));
        colEditorial.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEditorial()));
        colAno.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getAnoPublicacion()).asObject());
    }

    @FXML
    private void cargarLibrosEnTabla() {
        List<Libro> libros = libroDAO.obtenerTodosLosLibros();
        if (libros != null) {
            listaLibros.setAll(libros);
        } else {
            listaLibros.clear();
        }
        tablaLibros.setItems(listaLibros);
    }

    @FXML
    private void handleAgregarLibro() {
        String titulo = tituloField.getText();
        String isbn = isbnField.getText();
        String autor = autorField.getText();
        String editorial = editorialField.getText();
        int anoPublicacion;
        try {
            anoPublicacion = Integer.parseInt(anoPublicacionField.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El año de publicación debe ser un número válido.");
            return;
        }
        if (titulo.isEmpty() || isbn.isEmpty() || autor.isEmpty() || editorial.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar completos.");
            return;
        }
        Libro nuevoLibro = new Libro(titulo, isbn, autor, editorial, anoPublicacion);
        libroDAO.agregarLibro(nuevoLibro);
        listaLibros.add(nuevoLibro);
        limpiarCampos();
        mostrarAlerta("Éxito", "Libro agregado correctamente.");
    }

    @FXML
    private void handleModificarLibro() {
        Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un libro para modificar.");
            return;
        }
        libroSeleccionado.setTitulo(tituloField.getText());
        libroSeleccionado.setAutor(autorField.getText());
        libroSeleccionado.setEditorial(editorialField.getText());
        try {
            libroSeleccionado.setAnoPublicacion(Integer.parseInt(anoPublicacionField.getText()));
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El año de publicación debe ser un número válido.");
            return;
        }
        libroDAO.modificarLibro(libroSeleccionado);
        cargarLibrosEnTabla();
        mostrarAlerta("Éxito", "Libro modificado correctamente.");
    }

    @FXML
    private void handleEliminarLibro() {
        Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado == null) {
            mostrarAlerta("Error", "Selecciona un libro para eliminar.");
            return;
        }
        // Eliminamos basándonos en el ISBN (consulta en el DAO)
        libroDAO.eliminarLibro(libroSeleccionado.getIsbn());
        listaLibros.remove(libroSeleccionado);
        mostrarAlerta("Éxito", "Libro eliminado correctamente.");
    }

    @FXML
    private void handleBuscarPorISBN() {
        String isbn = isbnField.getText();
        if (isbn.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un ISBN para buscar.");
            return;
        }
        Libro libro = libroDAO.obtenerLibroPorIsbn(isbn);
        if (libro == null) {
            mostrarAlerta("Información", "No se encontró un libro con ese ISBN.");
            listaLibros.clear();
        } else {
            // setAll acepta varargs; aquí se pasa el objeto
            listaLibros.setAll(libro);
        }
    }

    @FXML
    private void handleBuscarPorAutor() {
        String autor = autorField.getText();
        if (autor.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un autor para buscar.");
            return;
        }
        Libro libro = libroDAO.obtenerLibroPorAutor(autor);
        if (libro == null) {
            mostrarAlerta("Información", "No se encontró un libro de ese autor.");
        } else {
            listaLibros.setAll(libro);
        }
    }

    @FXML
    private void handleBuscarPorTitulo() {
        String titulo = tituloField.getText();
        if (titulo.isEmpty()) {
            mostrarAlerta("Error", "Ingresa un título para buscar.");
            return;
        }
        Libro libro = libroDAO.obtenerLibroPorTitulo(titulo);
        if (libro == null) {
            mostrarAlerta("Información", "No se encontró un libro con ese título.");
        } else {
            listaLibros.setAll(libro);
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

    private void limpiarCampos() {
        tituloField.clear();
        isbnField.clear();
        autorField.clear();
        anoPublicacionField.clear();
        editorialField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
