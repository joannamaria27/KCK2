package layoutLogic;

import javafx.fxml.FXML;

import java.io.IOException;

public class MainMenuScreen {

    @FXML
    public void openCarMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/CarOptions.fxml");
    }

    @FXML
    public void openBicycleMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/BicycleOptions.fxml");
    }

    @FXML
    public void openScooterMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/ScooterOptions.fxml");
    }

    @FXML
    public void openClientMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/CustomerOptions.fxml");
    }

    @FXML
    public void openRentMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/RentOptions.fxml");
    }

}
