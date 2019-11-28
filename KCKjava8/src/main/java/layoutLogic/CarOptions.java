package layoutLogic;

import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;
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
    private TextField deleteVehicleIdTextField;


    TextField editVehicleIdTextView;

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

        WindowSingleton.showVehicleTable("samochod", deleteVehicleIdTextField);

    }

//    public void showVehicleTable(String type){
//
//        final Stage window = new Stage();
//        window.setTitle("Lista pojazdów typu \"" + type +"\"");
//        final TableView<Pojazd> table;
//        final Button select = new Button("Select");
//        String choice = "";
//
//        // id
//        TableColumn<Pojazd, Long> idColumn = new TableColumn<Pojazd, Long>("ID");
//        idColumn.setMinWidth(50);
//        idColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, Long>("id"));
//
//        // marka
//        TableColumn<Pojazd, String> markaColumn = new TableColumn<Pojazd, String>("Marka");
//        markaColumn.setMinWidth(100);
//        markaColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("marka"));
//
//        // model
//        TableColumn<Pojazd, String> modelColumn = new TableColumn<Pojazd, String>("Model");
//        modelColumn.setMinWidth(100);
//        modelColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("model"));
//
//        // id ubezpieczenia
//        TableColumn<Pojazd, String> idUbezpieczeniaColumn = new TableColumn<Pojazd, String>("ID ubezpieczenia");
//        idUbezpieczeniaColumn.setMinWidth(100);
//        idUbezpieczeniaColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("id_ubezpieczenia"));
//
//        // stan pojazdu
//        TableColumn<Pojazd, String> stanPojazduColumn = new TableColumn<Pojazd, String>("Stan pojazdu");
//        stanPojazduColumn.setMinWidth(100);
//        stanPojazduColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("stan_pojazdu"));
//
//        // dostepnosc
//        TableColumn<Pojazd, String> dostepnosColumn = new TableColumn<Pojazd, String>("Dostępność");
//        dostepnosColumn.setMinWidth(100);
//        dostepnosColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("dostepnosc"));
//
//        table = new TableView<Pojazd>();
//        table.setItems(getVehiclesObservableList(type));
//        table.getColumns().addAll(idColumn, markaColumn, modelColumn, idUbezpieczeniaColumn, stanPojazduColumn, dostepnosColumn);
//
//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(table, select);
//
//        Scene scene = new Scene(vBox);
//        window.setScene(scene);
//        window.show();
//        select.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e) {
//                Pojazd selection;
//                selection = table.getSelectionModel().getSelectedItem();
//                System.out.println(selection.getId());
//                deleteVehicleIdTextField.setText(String.valueOf(selection.getId()));
//                window.close();
//            }
//        });
//
//
//    }
//
//    private ObservableList<Pojazd> getVehiclesObservableList(String type){
//        ObservableList<Pojazd> vehicles = FXCollections.observableArrayList();
//        List<Pojazd> list = DBConnector.getInstance().entityManager.createQuery("SELECT a FROM Pojazd a WHERE typ='Samochód'", Pojazd.class).getResultList();
//
//        for (Pojazd pojazd : list) {
//            vehicles.add(pojazd);
//        }
//        return vehicles;
//    }

    public void editVehicle(String type){
        WindowSingleton.showVehicleTable(type, editVehicleIdTextView);
    }

}