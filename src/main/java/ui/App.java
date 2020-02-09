package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by SHARK on 08.08.2017.
 */
public class App extends javafx.application.Application {
    public static void main(String[] args) throws ClassNotFoundException {

        launch();

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainWindow.fxml"));
        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Sunrise");
        primaryStage.show();

    }

}
