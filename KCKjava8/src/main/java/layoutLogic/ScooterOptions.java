package layoutLogic;

import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

//import java.awt.*;

public class ScooterOptions {

    @FXML
    private Button addScooter;
    @FXML
    private Button mainMenuScooter;
    @FXML
    private TextField id;
    @FXML
    private TextField marka;
    @FXML
    private TextField model;
    @FXML
    private TextField stanPojazdu;
    @FXML
    private TextField ubezpieczenie;
    @FXML
    private TextField dostepnosc;
    @FXML
    private Button scooterListButton;
    @FXML
    private static TextField deleteVehicleIdTextField;
    @FXML
    private StackPane printScooterTabStackPane;
//    @FXML
//    private Button editScooter;
//    @FXML
//    private Button customerListButton;
//    @FXML
//    private static TextField rentVehicleIdTextField;
//    @FXML
//    private Button scooterRent;
//    @FXML
//    private Button deleteScooter;
//    @FXML
//    private Button scooterListButtonR;
//

    private String _marka, _model, _stanPojazdu, _ubezpieczenie, _dostepnosc;
    private long _id;

    @FXML
    public void addScooterToDB() {
        _marka = marka.getText();
        _model = model.getText();
        _stanPojazdu = stanPojazdu.getText();
        _ubezpieczenie = ubezpieczenie.getText();
        _dostepnosc = dostepnosc.getText();

        DBConnector.getInstance().start();
        DBConnector.getInstance().addPojazd(new Pojazd("skuter", _marka, _model, _ubezpieczenie, _stanPojazdu, _dostepnosc));
        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano skuter");
    }

    @FXML
    public void deleteScooterFromDB() {
        try {
            _id = Long.parseLong(deleteVehicleIdTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("zły format");
        }
        DBConnector.getInstance().start();
        Pojazd pojazd = DBConnector.getInstance().entityManager.find(Pojazd.class, _id);
        List<Wypozyczenie> list = DBConnector.getInstance().entityManager.createQuery("SELECT a FROM Wypozyczenie a WHERE id_pojazdu_id='" + _id + "'", Wypozyczenie.class).getResultList();

        if (pojazd == null) {
            WindowSingleton.alert("Nie ma takiego skutera");
            DBConnector.getInstance().stop();
            return;
        }
        if (list.size() > 0) {
            WindowSingleton.alert("Skuter jest w trakcie wypożyczenia");
            DBConnector.getInstance().stop();
            return;
        }
        // todo sout przeniesc do okna

        WindowSingleton.alert("Usunięto skuter o id = " + _id);
        System.out.println("usunieto skuter o id " + _id);
        DBConnector.getInstance().deletePojazd(pojazd);
        DBConnector.getInstance().stop();
    }


    @FXML
    public void showMainMenu() throws IOException, InterruptedException {
        // DBConnector dbConnector = DBConnector.getInstance();
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

    public void showScooterList() {
        WindowSingleton.showVehicleTable("skuter", deleteVehicleIdTextField);
    }

    public void printScooterList(){
        Scene scene = WindowSingleton.getInstance().getScene();
        final TableView<Pojazd> table = WindowSingleton.createVehicleTable("skuter");
        printScooterTabStackPane.getChildren().add(table);
    }

}