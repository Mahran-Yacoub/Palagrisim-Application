package driver;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ngram.NGramBuilder;
import java.io.File;



public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../files/Main.fxml"));
        Scene scene = new Scene(root, 860, 610);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Color Models");
        primaryStage.show();
    }
}