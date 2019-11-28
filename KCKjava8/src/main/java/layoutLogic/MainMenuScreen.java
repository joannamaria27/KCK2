package layoutLogic;

import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuScreen {

    @FXML
    public void openCarMenu() throws IOException {
        System.out.println("openCarMenu");

        // todo
        WindowSingleton.getInstance().setLayout("/layout/CarOptions.fxml");
    }

    @FXML
    public void openBicycleMenu() throws IOException {
        System.out.println("openBicycleMenu");

        //todo
        WindowSingleton.getInstance().setLayout("/layout/BicycleOptions.fxml");
    }

    @FXML
    public void openScooterMenu() throws IOException {
        System.out.println("openScooterMenu");

        // todo
        WindowSingleton.getInstance().setLayout("/layout/ScooterOptions.fxml");
    }

    @FXML
    public void openClientMenu() throws IOException {
        System.out.println("openClientMenu");

        // todo
//        WindowSingleton.getInstance().setLayout("/layout/ClientScreen.fxml");
    }

}
