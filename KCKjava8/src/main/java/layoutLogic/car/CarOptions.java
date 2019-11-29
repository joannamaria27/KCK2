package layoutLogic.car;

import domain.Klient;
import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField editCarBegDateTextField;
    @FXML
    private TextField editCarRetDateTextField;
    @FXML
    private TextField accessCodeTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField employeeTextField;
    @FXML
    private TextField rentVehicleClientId;
    @FXML
    private TextField rentVehicleVehicleId;
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

    public void showDeleteCarList(){
        showCarList(deleteVehicleIdTextField);
    }

    public void showRentCarList(){
        showCarList(rentVehicleVehicleId);
    }

    public void showEditCarList(){
        showCarList(editVehicleIdTextField);
    }

    public void printCarList() {
        final TableView<Pojazd> table = WindowSingleton.createVehicleTable("samochod");
        printCarsTabStackPane.getChildren().add(table);
    }

    public void showClientList(){
        WindowSingleton.showClientTable(rentVehicleClientId);
    }

    public void rentCar(){

        float _price;
        try {
            _price = Float.parseFloat(priceTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Niepoprawna cena");
            return;
        }

        if(DBConnector.getInstance().getEntityManager().createQuery("SELECT a FROM Wypozyczenie a WHERE id_pojazdu_id='"+rentVehicleVehicleId.getText()+"'", Wypozyczenie.class).getResultList().size()>0){
            WindowSingleton.alert("Pojazd jest w trakcie wypożyczenia");
            return;
        }

        Wypozyczenie wypozyczenie = new Wypozyczenie();
        Pojazd pojazd = (Pojazd) DBConnector.getInstance().getEntityManager().find(Pojazd.class, Long.parseLong(rentVehicleVehicleId.getText()));
        Klient klient = (Klient) DBConnector.getInstance().getEntityManager().find(Klient.class, Long.parseLong(rentVehicleClientId.getText()));
        DBConnector.getInstance().addWypozyczenie(new Wypozyczenie(pojazd, editCarBegDateTextField.getText(), editCarRetDateTextField.getText(), accessCodeTextField.getText(), klient, _price, employeeTextField.getText()));

        DBConnector.getInstance().start();
        DBConnector.getInstance().addWypozyczenie(wypozyczenie);
        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano wypożyczenie");
    }
}