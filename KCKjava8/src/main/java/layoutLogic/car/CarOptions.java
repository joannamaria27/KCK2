package layoutLogic.car;

import domain.Klient;
import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import layoutLogic.DBConnector;
import layoutLogic.WindowSingleton;

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
    private TextField editVehicleIdTextField;
    @FXML
    private Button setEditedCarButton;
    @FXML
    private Button editCarListButton;
    @FXML
    private TextField editCarMarkaTextField;
    @FXML
    private TextField editCarModelTextField;
    @FXML
    private TextField editCarStanPojazduTextField;
    @FXML
    private TextField editCarUbezpieczenieTextField;
    @FXML
    private TextField editCarDostepnoscTextField;
    @FXML
    private TextField editCarNewMarkaTextField;
    @FXML
    private TextField editCarNewModelTextField;
    @FXML
    private TextField editCarNewStanPojazduTextField;
    @FXML
    private TextField editCarNewUbezpieczenieTextField;
    @FXML
    private TextField editCarNewDostepnoscTextField;
    @FXML
    private Button editCarButton;
    @FXML
    private TextField rentCarBegDateTextField;
    @FXML
    private TextField rentCarRetDateTextField;
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
    private TextField dostepnosc;
    @FXML
    private Button carListButton;
    @FXML
    private TextField deleteVehicleIdTextField;
    //    @FXML
    //    private TableView tableViewCarTab;
    @FXML
    private StackPane printCarsTabStackPane;
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

    public void showCarList(TextField textField) {
        WindowSingleton.showVehicleTable("samochod", textField);
    }

    public void showDeleteCarList() {
        showCarList(deleteVehicleIdTextField);
    }

    public void showRentCarList() {
        showCarList(rentVehicleVehicleId);
    }

    public void showEditCarList() {
        showCarList(editVehicleIdTextField);
    }

    public void printCarList() {
        final TableView<Pojazd> table = WindowSingleton.createVehicleTable("samochod");
        printCarsTabStackPane.getChildren().add(table);
    }

    public void showClientList() {
        WindowSingleton.showClientTable(rentVehicleClientId);
    }

    public void rentCar() {

        if (rentVehicleVehicleId.getText().equals("") || rentVehicleClientId.getText().equals("") || rentCarBegDateTextField.getText().equals("") || rentCarRetDateTextField.getText().equals("") || accessCodeTextField.getText().equals("") || employeeTextField.getText().equals("")) {
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
        DBConnector.getInstance().addWypozyczenie(new Wypozyczenie(pojazd, rentCarBegDateTextField.getText(), rentCarRetDateTextField.getText(), accessCodeTextField.getText(), klient, _price, employeeTextField.getText()));

        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano wypożyczenie");
    }

    public void fillEditedCarFields() {
        System.out.println("gettext: \n" + editCarNewMarkaTextField.getText() + "\"");
        Pojazd pojazd = DBConnector.getInstance().getEntityManager().find(Pojazd.class, Long.parseLong(editVehicleIdTextField.getText()));
        if (pojazd == null) {
            WindowSingleton.alert("Nie ma pojazdu o tym ID");
            return;
        }

        editCarMarkaTextField.setText("Marka - " + pojazd.getMarka());
        editCarModelTextField.setText("Model - " + pojazd.getModel());
        editCarStanPojazduTextField.setText("Stan pojazdu - " + pojazd.getStan_pojazdu());
        editCarUbezpieczenieTextField.setText("Ubezpieczenie - " + pojazd.getId_ubezpieczenia());
        editCarDostepnoscTextField.setText("Dostępność - " + pojazd.getDostepnosc());

    }

    public void editCar() {
        Long id_;

        if (editCarNewMarkaTextField.getText().equals("") || editCarNewModelTextField.getText().equals("") || editCarNewUbezpieczenieTextField.getText().equals("") || editCarNewStanPojazduTextField.getText().equals("") || editCarNewDostepnoscTextField.getText().equals("")) {
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

        String[] newCarDetails = new String[5];
        newCarDetails[0] = editCarNewMarkaTextField.getText();
        newCarDetails[1] = editCarNewModelTextField.getText();
        newCarDetails[2] = editCarNewUbezpieczenieTextField.getText();
        newCarDetails[3] = editCarNewStanPojazduTextField.getText();
        newCarDetails[4] = editCarNewDostepnoscTextField.getText();
        pojazd.setParameters(newCarDetails);
        DBConnector.getInstance().editPojazd(pojazd);
        WindowSingleton.alert("Zedytowano pojazd");
    }


}