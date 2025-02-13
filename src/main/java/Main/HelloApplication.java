package Main;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600); // Tamaño de la ventana

        // Obtener el controlador y pasarle el Stage
        MainController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("BibliotecaFX");
        stage.setScene(scene);

        // Evitar que la ventana sea redimensionable
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
