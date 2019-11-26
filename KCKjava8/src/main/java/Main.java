import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import layoutLogic.WelcomeScreen;

public class Main  extends Application {

//    public static Scene scene;

    public static void main(String[] args)  {
        launch();
    }


    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/layout/WelcomeScreen.fxml"));

        BorderPane borderPane = loader.load();

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("KCK2");
        primaryStage.show();


    }
}
