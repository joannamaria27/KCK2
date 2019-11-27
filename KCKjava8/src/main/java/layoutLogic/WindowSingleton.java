package layoutLogic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowSingleton {

    private static WindowSingleton instance;
    private Stage primaryStage;

    private WindowSingleton() { }

    public static WindowSingleton getInstance() {
        if(instance == null){
            instance = new WindowSingleton();
            return instance;
        }
        else return instance;

    }

    public void setLayout(String pathToFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(pathToFXML));

        BorderPane borderPane = null;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("KCK2");
        primaryStage.show();
    }

    public void startApp(Stage stage) throws IOException {
        primaryStage = stage;
        setLayout("/layout/WelcomeScreen.fxml");


    }
}
