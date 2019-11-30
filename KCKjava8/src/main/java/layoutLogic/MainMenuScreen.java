package layoutLogic;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MainMenuScreen {

    @FXML
    private ImageView carMain;
    @FXML
    private ImageView bicycleMain;
    @FXML
    private ImageView scooterMain;

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

    @FXML
    public void changeCarIcon(){
        System.out.println("zmiana auta");
        carMain.setImage(new Image("image/car1.png"));
    }
    @FXML
    public void changeBicycleIcon(){
        System.out.println("zmiana auta");
        bicycleMain.setImage(new Image("image/bicycle1.png"));
    }
    @FXML
    public void changeScooterIcon(){
        System.out.println("zmiana auta");
        scooterMain.setImage(new Image("image/scooter1png.png"));
    }

    @FXML
    public void changeBackCarIcon(){
        carMain.setImage(new Image("image/car.png"));
    }
    @FXML
    public void changeBackBicycleIcon(){
        bicycleMain.setImage(new Image("image/bicycle.png"));
    }
    @FXML
    public void changeBackScooterIcon(){
        scooterMain.setImage(new Image("image/scooter.png"));
    }

}
