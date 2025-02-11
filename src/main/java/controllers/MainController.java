package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Stage stage;

    // Este método se llama para pasar el Stage de la ventana principal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para abrir la vista de gestión de libros
    @FXML
    private void handleGestionLibros() {
        try {
            System.out.println("Gestion Libros Button clicked");
            cargarVista("libro-view.fxml");  // Cambia la vista a libro
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para abrir la vista de gestión de autores
    @FXML
    private void handleGestionAutores() {
        try {
            System.out.println("Gestion Autores Button clicked");
            cargarVista("autor-view.fxml");  // Cambia la vista a autor
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para abrir la vista de gestión de préstamos
    @FXML
    private void handleGestionPrestamos() {
        try {
            System.out.println("Gestion Prestamos Button clicked");
            cargarVista("prestamo-view.fxml");  // Cambia la vista a préstamo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para abrir la vista de gestión de socios
    @FXML
    private void handleGestionSocios() {
        try {
            System.out.println("Gestion Socios Button clicked");
            cargarVista("socio-view.fxml");  // Cambia la vista a socio
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
        if (controller instanceof AutorController) {
            ((AutorController) controller).setStage(stage);
        } else if (controller instanceof LibroController) {
            ((LibroController) controller).setStage(stage);
        } else if (controller instanceof PrestamoController) {
            ((PrestamoController) controller).setStage(stage);
        } else if (controller instanceof SocioController) {
            ((SocioController) controller).setStage(stage);
        } else if (controller instanceof MainController) {
            ((MainController) controller).setStage(stage);
        }

        // En lugar de cambiar el root de la Scene existente, creamos una nueva Scene
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }

}
