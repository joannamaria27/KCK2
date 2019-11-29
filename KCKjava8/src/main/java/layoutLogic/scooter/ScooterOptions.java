package layoutLogic.scooter;

import domain.Klient;
import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import layoutLogic.DBConnector;
import layoutLogic.WindowSingleton;

import java.io.IOException;
import java.util.List;

//import java.awt.*;

public class ScooterOptions {

    @FXML
    private TextField deleteVehicleIdTextField;
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
    private StackPane printScooterTabStackPane;


    @FXML
    private TextField editVehicleIdTextField;
    @FXML
    private Button setEditedScooterButton;
    @FXML
    private Button editScooterListButton;
    @FXML
    private TextField editScooterMarkaTextField;
    @FXML
    private TextField editScooterModelTextField;
    @FXML
    private TextField editScooterStanPojazduTextField;
    @FXML
    private TextField editScooterUbezpieczenieTextField;
    @FXML
    private TextField editScooterDostepnoscTextField;
    @FXML
    private TextField editScooterNewMarkaTextField;
    @FXML
    private TextField editScooterNewModelTextField;
    @FXML
    private TextField editScooterNewStanPojazduTextField;
    @FXML
    private TextField editScooterNewUbezpieczenieTextField;
    @FXML
    private TextField editScooterNewDostepnoscTextField;
    @FXML
    private Button editScooterButton;
    @FXML
    private TextField rentScooterBegDateTextField;
    @FXML
    private TextField rentScooterRetDateTextField;
    @FXML
    private TextField rentVehicleClientId;
    @FXML
    private TextField rentVehicleVehicleId;
    @FXML
    private TextField accessCodeTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField employeeTextField;
    @FXML
    private Button ScooterListButton;
    @FXML
    private StackPane printScootersTabStackPane;


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
        if (marka.getText().equals("") || model.getText().equals("") || stanPojazdu.getText().equals("") || ubezpieczenie.getText().equals("") || dostepnosc.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }

        _marka = marka.getText();
        _model = model.getText();
        _stanPojazdu = stanPojazdu.getText();
        _ubezpieczenie = ubezpieczenie.getText();
        _dostepnosc = dostepnosc.getText();

        DBConnector.getInstance().start();
        DBConnector.getInstance().addPojazd(new Pojazd("skuter", _marka, _model, _ubezpieczenie, _stanPojazdu, _dostepnosc));
        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano pojazd");
    }

    @FXML
    public void deleteScooterFromDB() {
        try {
            _id = Long.parseLong(deleteVehicleIdTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("zły format");
        }
        DBConnector.getInstance().start();
        Pojazd pojazd = DBConnector.getInstance().getEntityManager().find(Pojazd.class, _id);
        List<Wypozyczenie> list = DBConnector.getInstance().getEntityManager().createQuery("SELECT a FROM Wypozyczenie a WHERE id_pojazdu_id='" + _id + "'", Wypozyczenie.class).getResultList();

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

        WindowSingleton.alert("Usunięto pojazd o id = " + _id);
        System.out.println("usunieto pojazd o id " + _id);
        DBConnector.getInstance().deletePojazd(pojazd);
        DBConnector.getInstance().stop();
        deleteVehicleIdTextField.setText("");
    }


    @FXML
    public void showMainMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

    public void showScooterList() {
        WindowSingleton.showVehicleTable("skuter", deleteVehicleIdTextField);
    }

    public void showScooterList(TextField textField) {
        WindowSingleton.showVehicleTable("skuter", textField);
    }

    public void showDeleteScooterList() {
        showScooterList(deleteVehicleIdTextField);
    }

    public void showRentScooterList() {
        showScooterList(rentVehicleVehicleId);
    }

    public void showEditScooterList() {
        showScooterList(editVehicleIdTextField);
    }

    public void printScooterList() {
        final TableView<Pojazd> table = WindowSingleton.createVehicleTable("skuter");
        printScooterTabStackPane.getChildren().add(table);
    }

    public void showClientList() {
        WindowSingleton.showClientTable(rentVehicleClientId);
    }

    public void rentScooter() {

        if (rentVehicleVehicleId.getText().equals("") || rentVehicleClientId.getText().equals("") || rentScooterBegDateTextField.getText().equals("") || rentScooterRetDateTextField.getText().equals("") || accessCodeTextField.getText().equals("") || employeeTextField.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }

//        rentVehicleVehicleId.getText()
//        rentVehicleClientId.getText())
//        pojazd, rentCarBegDateTextField.getText(), rentCarRetDateTextField.getText(), accessCodeTextField.getText(), klient, _price, employeeTextField.getText())

        float _price;
        try {
            _price = Float.parseFloat(priceTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Niepoprawna cena");
            return;
        }

        if (DBConnector.getInstance().getEntityManager().createQuery("SELECT a FROM Wypozyczenie a WHERE id_pojazdu_id='" + rentVehicleVehicleId.getText() + "'", Wypozyczenie.class).getResultList().size() > 0) {
            WindowSingleton.alert("Pojazd jest w trakcie wypożyczenia");
            return;
        }

        Wypozyczenie wypozyczenie = new Wypozyczenie();
        Pojazd pojazd = (Pojazd) DBConnector.getInstance().getEntityManager().find(Pojazd.class, Long.parseLong(rentVehicleVehicleId.getText()));
        Klient klient = (Klient) DBConnector.getInstance().getEntityManager().find(Klient.class, Long.parseLong(rentVehicleClientId.getText()));
        if (pojazd == null) {
            WindowSingleton.alert("Nie ma pojazdu o tym ID");
            return;
        }
        if (klient == null) {
            WindowSingleton.alert("Nie ma klienta o tym ID");
            return;
        }
        DBConnector.getInstance().start();
        DBConnector.getInstance().addWypozyczenie(new Wypozyczenie(pojazd, rentScooterBegDateTextField.getText(), rentScooterRetDateTextField.getText(), accessCodeTextField.getText(), klient, _price, employeeTextField.getText()));

        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano wypożyczenie");
    }

    public void fillEditedScooterFields() {
        System.out.println("gettext: \n" + editScooterNewMarkaTextField.getText() + "\"");
        Pojazd pojazd = DBConnector.getInstance().getEntityManager().find(Pojazd.class, Long.parseLong(editVehicleIdTextField.getText()));
        if (pojazd == null) {
            WindowSingleton.alert("Nie ma pojazdu o tym ID");
            return;
        }

        editScooterMarkaTextField.setText("Marka - " + pojazd.getMarka());
        editScooterModelTextField.setText("Model - " + pojazd.getModel());
        editScooterStanPojazduTextField.setText("Stan pojazdu - " + pojazd.getStan_pojazdu());
        editScooterUbezpieczenieTextField.setText("Ubezpieczenie - " + pojazd.getId_ubezpieczenia());
        editScooterDostepnoscTextField.setText("Dostępność - " + pojazd.getDostepnosc());

    }

    public void editScooter() {
        Long id_;

        if (editScooterNewMarkaTextField.getText().equals("") || editScooterNewModelTextField.getText().equals("") || editScooterNewUbezpieczenieTextField.getText().equals("") || editScooterNewStanPojazduTextField.getText().equals("") || editScooterNewDostepnoscTextField.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }

        try {
            id_ = Long.parseLong(editVehicleIdTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("zły format");
            WindowSingleton.alert("Zły format");
            return;
        }

        Pojazd pojazd = DBConnector.getInstance().getEntityManager().find(Pojazd.class, Long.parseLong(editVehicleIdTextField.getText()));

        if (pojazd == null) {
            if (pojazd == null) {
                WindowSingleton.alert("Nie ma pojazdu o tym ID");
                return;
            }
        }

        String[] newScooterDetails = new String[5];
        newScooterDetails[0] = editScooterNewMarkaTextField.getText();
        newScooterDetails[1] = editScooterNewModelTextField.getText();
        newScooterDetails[2] = editScooterNewUbezpieczenieTextField.getText();
        newScooterDetails[3] = editScooterNewStanPojazduTextField.getText();
        newScooterDetails[4] = editScooterNewDostepnoscTextField.getText();
        pojazd.setParameters(newScooterDetails);
        DBConnector.getInstance().editPojazd(pojazd);
        WindowSingleton.alert("Zedytowano pojazd");
    }

}