package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class PrestamoController {

    private Stage stage;

    // Método para pasar el Stage de la ventana principal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Inicialización si es necesario
    }

    @FXML
    public void handleGestionPrestamos() {
        System.out.println("Gestion Prestamos Button clicked");
        // Aquí iría la lógica para gestionar préstamos
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

    // Método para cargar una vista específica en la misma ventana
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
