package layoutLogic.bicycle;

import domain.Klient;
import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.event.ActionEvent;
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

public class BicycleOptions {

    @FXML
    private Button addBicycle;
    @FXML
    private Button mainMenuBicycle;
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
    private Button setEditedBicycleButton;
    @FXML
    private Button editBicycleListButton;
    @FXML
    private TextField editBicycleMarkaTextField;
    @FXML
    private TextField editBicycleModelTextField;
    @FXML
    private TextField editBicycleStanPojazduTextField;
    @FXML
    private TextField editBicycleUbezpieczenieTextField;
    @FXML
    private TextField editBicycleDostepnoscTextField;
    @FXML
    private TextField editBicycleNewMarkaTextField;
    @FXML
    private TextField editBicycleNewModelTextField;
    @FXML
    private TextField editBicycleNewStanPojazduTextField;
    @FXML
    private TextField editBicycleNewUbezpieczenieTextField;
    @FXML
    private TextField editBicycleNewDostepnoscTextField;
    @FXML
    private Button editBicycleButton;
    @FXML
    private TextField rentBicycleBegDateTextField;
    @FXML
    private TextField rentBicycleRetDateTextField;
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
    private Button bicycleListButton;
    @FXML
    private TextField deleteVehicleIdTextField;
    //    @FXML
    //    private TableView tableViewBicycleTab;
    @FXML
    private StackPane printBicyclesTabStackPane;
//    @FXML
//    private Button editBicycle;
//    @FXML
//    private Button customerListButton;
//    @FXML
//    private static TextField rentVehicleIdTextField;
//    @FXML
//    private Button bicycleRent;
//    @FXML
//    private Button deleteBicycle;
//    @FXML
//    private Button bicycleListButtonR;
//

    private String _marka, _model, _stanPojazdu, _ubezpieczenie, _dostepnosc;
    private long _id;

    @FXML
    public void addBicycleToDB() {

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
        DBConnector.getInstance().addPojazd(new Pojazd("rower", _marka, _model, _ubezpieczenie, _stanPojazdu, _dostepnosc));
        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano pojazd");
    }

    @FXML
    public void deleteBicycleFromDB() {
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

    public void showBicycleList(TextField textField) {
        WindowSingleton.showVehicleTable("rower", textField);
    }

    public void showDeleteBicycleList() {
        showBicycleList(deleteVehicleIdTextField);
    }

    public void showRentBicycleList() {
        showBicycleList(rentVehicleVehicleId);
    }

    public void showEditBicycleList() {
        showBicycleList(editVehicleIdTextField);
    }

    public void printBicycleList() {
        final TableView<Pojazd> table = WindowSingleton.createVehicleTable("rower");
        printBicyclesTabStackPane.getChildren().add(table);
    }

    public void showClientList() {
        WindowSingleton.showClientTable(rentVehicleClientId);
    }

    public void rentBicycle() {

        if (rentVehicleVehicleId.getText().equals("") || rentVehicleClientId.getText().equals("") || rentBicycleBegDateTextField.getText().equals("") || rentBicycleRetDateTextField.getText().equals("") || accessCodeTextField.getText().equals("") || employeeTextField.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }

//        rentVehicleVehicleId.getText()
//        rentVehicleClientId.getText())
//        pojazd, rentBicycleBegDateTextField.getText(), rentBicycleRetDateTextField.getText(), accessCodeTextField.getText(), klient, _price, employeeTextField.getText())

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
        DBConnector.getInstance().addWypozyczenie(new Wypozyczenie(pojazd, rentBicycleBegDateTextField.getText(), rentBicycleRetDateTextField.getText(), accessCodeTextField.getText(), klient, _price, employeeTextField.getText()));

        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano wypożyczenie");
    }

    public void fillEditedBicycleFields() {
        System.out.println("gettext: \n" + editBicycleNewMarkaTextField.getText() + "\"");
        Pojazd pojazd = DBConnector.getInstance().getEntityManager().find(Pojazd.class, Long.parseLong(editVehicleIdTextField.getText()));
        if (pojazd == null) {
            WindowSingleton.alert("Nie ma pojazdu o tym ID");
            return;
        }

        editBicycleMarkaTextField.setText("Marka - " + pojazd.getMarka());
        editBicycleModelTextField.setText("Model - " + pojazd.getModel());
        editBicycleStanPojazduTextField.setText("Stan pojazdu - " + pojazd.getStan_pojazdu());
        editBicycleUbezpieczenieTextField.setText("Ubezpieczenie - " + pojazd.getId_ubezpieczenia());
        editBicycleDostepnoscTextField.setText("Dostępność - " + pojazd.getDostepnosc());

    }

    public void editBicycle() {
        Long id_;

        if (editBicycleNewMarkaTextField.getText().equals("") || editBicycleNewModelTextField.getText().equals("") || editBicycleNewUbezpieczenieTextField.getText().equals("") || editBicycleNewStanPojazduTextField.getText().equals("") || editBicycleNewDostepnoscTextField.getText().equals("")) {
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

        String[] newBicycleDetails = new String[5];
        newBicycleDetails[0] = editBicycleNewMarkaTextField.getText();
        newBicycleDetails[1] = editBicycleNewModelTextField.getText();
        newBicycleDetails[2] = editBicycleNewUbezpieczenieTextField.getText();
        newBicycleDetails[3] = editBicycleNewStanPojazduTextField.getText();
        newBicycleDetails[4] = editBicycleNewDostepnoscTextField.getText();
        pojazd.setParameters(newBicycleDetails);
        DBConnector.getInstance().editPojazd(pojazd);
        WindowSingleton.alert("Zedytowano pojazd");
    }

}