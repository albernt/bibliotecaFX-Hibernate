package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleGestionLibros() {
        try {
            System.out.println("Gestion Libros Button clicked");
            cargarVista("libro-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGestionAutores() {
        try {
            System.out.println("Gestion Autores Button clicked");
            cargarVista("autor-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGestionPrestamos() {
        try {
            System.out.println("Gestion Prestamos Button clicked");
            cargarVista("prestamo-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGestionSocios() {
        try {
            System.out.println("Gestion Socios Button clicked");
            cargarVista("socio-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVista(String vista) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + vista));
        AnchorPane root = fxmlLoader.load();

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

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
    }

}
