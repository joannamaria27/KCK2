import javafx.application.Application;
import javafx.stage.Stage;
import layoutLogic.WindowSingleton;

public class Main  extends Application {

    public static  Stage primaryStage;

//    public static Scene scene;

    public static void main(String[] args)  {
        launch();
    }


    public void start(Stage primaryStage) throws Exception {

        System.out.println("halo");
        WindowSingleton window = WindowSingleton.getInstance();
        window.startApp(primaryStage);



    }
}
