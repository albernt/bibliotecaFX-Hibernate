package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class LibroController {

    private Stage stage;

    // Este método se llama para pasar el Stage de la ventana principal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Inicialización si es necesario
    }

    @FXML
    public void handleVolver() {
        try {
            System.out.println("Volviendo a la vista principal...");
            cargarVista("main-view.fxml");  // Cargar la vista principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar la vista de gestión de libros
    private void cargarVista(String vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + vista));
        AnchorPane root = fxmlLoader.load(); // Cargar solo el root

        // Si el controlador de la nueva vista es un MainController, pásale el Stage
        if (fxmlLoader.getController() instanceof MainController) {
            MainController controller = (MainController) fxmlLoader.getController();
            controller.setStage(stage);
        }

        // Cambiar el contenido de la ventana (actualiza solo el root)
        stage.getScene().setRoot(root);
    }
}
