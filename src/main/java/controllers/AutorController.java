package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class AutorController {

    private Stage stage;

    // Este método se llama para pasar el Stage de la ventana principal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Aquí puedes hacer la inicialización si es necesario
    }

    // Método para volver a la vista principal
    @FXML
    public void handleVolver() {
        try {
            System.out.println("Volviendo a la vista principal...");
            cargarVista("main-view.fxml");  // Cargar la vista principal
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGestionAutores() {
        System.out.println("Gestion Autores Button clicked");
        // Aquí iría la lógica para manejar la acción del botón
    }

    // Método para cargar la vista de gestión de autores
    private void cargarVista(String vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + vista));
        AnchorPane root = fxmlLoader.load(); // Cargar solo el root

        // Asegúrate de pasar el Stage al controlador de la nueva vista
        if (fxmlLoader.getController() instanceof MainController) {
            MainController controller = (MainController) fxmlLoader.getController();
            controller.setStage(stage);
        }

        // Cambiar el contenido de la ventana
        stage.getScene().setRoot(root);
    }
}
