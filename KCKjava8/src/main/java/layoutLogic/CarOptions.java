package layoutLogic;

import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

//import java.awt.*;

public class CarOptions {

    @FXML
    private Button addCar;
    @FXML
    private Button mainMenuCar;
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
    private Button carListButton;
    @FXML
    private static TextField deleteVehicleIdTextField;
//    @FXML
//    private Button editCar;
//    @FXML
//    private Button customerListButton;
//    @FXML
//    private static TextField rentVehicleIdTextField;
//    @FXML
//    private Button carRent;
//    @FXML
//    private Button deleteCar;
//    @FXML
//    private Button carListButtonR;
//

    private String _marka, _model, _stanPojazdu, _ubezpieczenie, _dostepnosc;
    private long _id;

    @FXML
    public void addCarToDB() {
        _marka = marka.getText();
        _model = model.getText();
        _stanPojazdu = stanPojazdu.getText();
        _ubezpieczenie = ubezpieczenie.getText();
        _dostepnosc = dostepnosc.getText();

        DBConnector.getInstance().start();
        DBConnector.getInstance().addPojazd(new Pojazd("samochod", _marka, _model, _ubezpieczenie, _stanPojazdu, _dostepnosc));
        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano pojazd");
    }

    @FXML
    public void deleteCarFromDB() {
        try {
            _id = Long.parseLong(deleteVehicleIdTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("zły format");
        }
        DBConnector.getInstance().start();
        Pojazd pojazd = DBConnector.getInstance().entityManager.find(Pojazd.class, _id);
        List<Wypozyczenie> list = DBConnector.getInstance().entityManager.createQuery("SELECT a FROM Wypozyczenie a WHERE id_pojazdu_id='" + _id + "'", Wypozyczenie.class).getResultList();

        if (pojazd == null) {
            WindowSingleton.alert("Nie ma takiego pojazdu");
            DBConnector.getInstance().stop();
            return;
        }
        if (list.size() > 0) {
            WindowSingleton.alert("Pojazd jest w trakcie wypożyczenia");
            DBConnector.getInstance().stop();
            return;
        }
        // todo sout przeniesc do okna

        WindowSingleton.alert("Usunięto pojazd o id = " + _id);
        System.out.println("usunieto pojazd o id " + _id);
        DBConnector.getInstance().deletePojazd(pojazd);
        DBConnector.getInstance().stop();
    }


    @FXML
    public void showMainMenu() throws IOException, InterruptedException {
        // DBConnector dbConnector = DBConnector.getInstance();
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

    public void showCarList() {
//        List<Pojazd> list = DBConnector.getInstance().entityManager.createQuery("SELECT a FROM Pojazd a WHERE typ='Samochód'", Pojazd.class).getResultList();
//        String[] elements = new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            Pojazd item = list.get(i);
//            elements[i] = list.get(i).getId() + " - " + list.get(i).getMarka() + " - " + list.get(i).getModel() + " - " + list.get(i).getDostepnosc() + " - " + list.get(i).getId_ubezpieczenia() + " - " + list.get(i).getStan_pojazdu() + " - " + list.get(i).getTyp();
//        }
//
//        for (int i = 0; i < elements.length; i++) {
//            System.out.println(elements);
//        }
//        WindowSingleton.showList("Lista samochodów","ID - Marka - Model - Dostępność - ID ubezpieczenia - Stan pojazdu - Typ", elements);

        WindowSingleton.showVehicleTable("samochod");

    }

}