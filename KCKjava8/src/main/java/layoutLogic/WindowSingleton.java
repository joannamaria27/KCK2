package layoutLogic;

import domain.Klient;
import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class WindowSingleton {

    private static WindowSingleton instance;
    private Stage primaryStage;
    private Scene scene;



    private WindowSingleton() {
    }

    public static WindowSingleton getInstance() {
        if (instance == null) {
            instance = new WindowSingleton();
            return instance;
        } else return instance;

    }

    public static void setInstance(WindowSingleton instance) {
        WindowSingleton.instance = instance;
    }

    public static void alert(String message) {
        final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Alert");
        window.setResizable(false);
        window.getIcons().add(new Image("image/AlertIcon.png"));
        window.setMinWidth(356);
        window.setMinHeight(220);

        Label label = new Label(message);
        Button closeButton = new Button("Zamknij");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                window.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void showVehicleTable(String type, final TextField idField) {

        final Stage window = new Stage();
        Button button = new Button("Wybierz");
        window.setTitle("Lista pojazdów typu \"" + type + "\"");


        final TableView<Pojazd> table = createVehicleTable(type);
        Pojazd pojazd = table.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, button);

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Pojazd selection;
                selection = table.getSelectionModel().getSelectedItem();
                //System.out.println(selection.getId());
                idField.setText(String.valueOf(selection.getId()));
                window.close();
            }
        });

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    public static void showClientTable(final TextField idField) {

        final Stage window = new Stage();
        Button button = new Button("Wybierz");
        window.setTitle("Lista klientów");


        final TableView<Klient> table = createClientTable();
        Klient klient = table.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, button);

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Klient selection;
                selection = table.getSelectionModel().getSelectedItem();
                //System.out.println(selection.getId());
                idField.setText(String.valueOf(selection.getId()));
                window.close();
            }
        });

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    public static void showRentTable(final TextField idField) {

        final Stage window = new Stage();
        Button button = new Button("Wybierz");
        window.setTitle("Lista wypożyczeń");


        final TableView<Wypozyczenie> table = createRentsTable();
        Wypozyczenie wypozyczenie = table.getSelectionModel().getSelectedItem();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, button);

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Wypozyczenie selection;
                selection = table.getSelectionModel().getSelectedItem();
                //System.out.println(selection.getId());
                idField.setText(String.valueOf(selection.getId()));
                window.close();
            }
        });

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

    public static TableView createVehicleTable(String type) {
        final TableView<Pojazd> table;
//        final Button select = new Button("Select");
//        String choice = "";

        // id
        TableColumn<Pojazd, Long> idColumn = new TableColumn<Pojazd, Long>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, Long>("id"));

        // marka
        TableColumn<Pojazd, String> markaColumn = new TableColumn<Pojazd, String>("Marka");
        markaColumn.setMinWidth(100);
        markaColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("marka"));

        // model
        TableColumn<Pojazd, String> modelColumn = new TableColumn<Pojazd, String>("Model");
        modelColumn.setMinWidth(100);
        modelColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("model"));

        // id ubezpieczenia
        TableColumn<Pojazd, String> idUbezpieczeniaColumn = new TableColumn<Pojazd, String>("ID ubezpieczenia");
        idUbezpieczeniaColumn.setMinWidth(100);
        idUbezpieczeniaColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("id_ubezpieczenia"));

        // stan pojazdu
        TableColumn<Pojazd, String> stanPojazduColumn = new TableColumn<Pojazd, String>("Stan pojazdu");
        stanPojazduColumn.setMinWidth(100);
        stanPojazduColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("stan_pojazdu"));

        // dostepnosc
        TableColumn<Pojazd, String> dostepnosColumn = new TableColumn<Pojazd, String>("Dostępność");
        dostepnosColumn.setMinWidth(100);
        dostepnosColumn.setCellValueFactory(new PropertyValueFactory<Pojazd, String>("dostepnosc"));

        table = new TableView<Pojazd>();
        table.setItems(WindowSingleton.getVehiclesObservableList(type));
        table.getColumns().addAll(idColumn, markaColumn, modelColumn, idUbezpieczeniaColumn, stanPojazduColumn, dostepnosColumn);

        return table;
    }

    public static TableView createClientTable() {
        final TableView<Klient> table;
//        final Button select = new Button("Select");
//        String choice = "";

        // id
        TableColumn<Klient, Long> idColumn = new TableColumn<Klient, Long>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Klient, Long>("id"));

        // adres
        TableColumn<Klient, String> addressColumn = new TableColumn<Klient, String>("Adres");
        addressColumn.setMinWidth(100);
        addressColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("adres"));

        // nr prawa jazdy
        TableColumn<Klient, String> drivingLicenceNumberColumn = new TableColumn<Klient, String>("Nr prawa jazdy");
        drivingLicenceNumberColumn.setMinWidth(100);
        drivingLicenceNumberColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("nr_prawa_jazdy"));

        // imie
        TableColumn<Klient, String> nameColumn = new TableColumn<Klient, String>("Imie");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("imie"));

        // nazwisko
        TableColumn<Klient, String> surnameColumn = new TableColumn<Klient, String>("Nazwisko");
        surnameColumn.setMinWidth(100);
        surnameColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("nazwisko"));

        // data urodzenia
        TableColumn<Klient, String> birthDateColumn = new TableColumn<Klient, String>("Data urodzenia");
        birthDateColumn.setMinWidth(100);
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("data_urodzenia"));

        // pesel
        TableColumn<Klient, String> peselColumn = new TableColumn<Klient, String>("PESEL");
        peselColumn.setMinWidth(100);
        peselColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("pesel"));

        TableColumn<Klient, String> phoneColumn = new TableColumn<Klient, String>("Telefon");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Klient, String>("telefon"));


        table = new TableView<Klient>();
        table.setItems(WindowSingleton.getClientObservableList());
        table.getColumns().addAll(idColumn, nameColumn, surnameColumn, birthDateColumn, addressColumn, phoneColumn, drivingLicenceNumberColumn, peselColumn);

        return table;
    }

    public static TableView createRentsTable() {
        final TableView<Wypozyczenie> table;
//        final Button select = new Button("Select");
//        String choice = "";

        // id
        TableColumn<Wypozyczenie, Long> idColumn = new TableColumn<Wypozyczenie, Long>("ID");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Long>("id"));


        // todo id pojazdu i klienta nie wyswietla sie w tabeli :c
        // id samochodu
        TableColumn<Wypozyczenie, Long> carIdColumn = new TableColumn<Wypozyczenie, Long>("ID pojazdu");
        carIdColumn.setMinWidth(100);
        carIdColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Long>("id_pojazdu"));

        // id klienta
        TableColumn<Wypozyczenie, Klient> clientIdColumn = new TableColumn<Wypozyczenie, Klient>("ID klienta");
        clientIdColumn.setMinWidth(100);
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Klient>("id_klienta"));

        // cena
        TableColumn<Wypozyczenie, Float> priceColumn = new TableColumn<Wypozyczenie, Float>("Cena");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, Float>("cena"));

        // data rozpoczecia
        TableColumn<Wypozyczenie, String> begDateColumn = new TableColumn<Wypozyczenie, String>("Data wypożyczenia");
        begDateColumn.setMinWidth(100);
        begDateColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, String>("data_wypozyczenia"));

        // data zakonczenia
        TableColumn<Wypozyczenie, String> retDateColumn = new TableColumn<Wypozyczenie, String>("Data oddania");
        retDateColumn.setMinWidth(100);
        retDateColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, String>("data_oddania"));

        // kod dostepu
        TableColumn<Wypozyczenie, String> accessCodeColumn = new TableColumn<Wypozyczenie, String>("Kod dostępu");
        accessCodeColumn.setMinWidth(100);
        accessCodeColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, String>("kod_dostepu"));

        // pracownik
        TableColumn<Wypozyczenie, String> employeeColumn = new TableColumn<Wypozyczenie, String>("Pracownik");
        employeeColumn.setMinWidth(100);
        employeeColumn.setCellValueFactory(new PropertyValueFactory<Wypozyczenie, String>("pracownik"));


        table = new TableView<Wypozyczenie>();
        table.setItems(WindowSingleton.getRentObservableList());
        table.getColumns().addAll(idColumn, carIdColumn, clientIdColumn, priceColumn, begDateColumn, retDateColumn, accessCodeColumn, employeeColumn);

        return table;
    }

    private static ObservableList<Pojazd> getVehiclesObservableList(String type) {
        ObservableList<Pojazd> vehicles = FXCollections.observableArrayList();
        List<Pojazd> list = DBConnector.getInstance().getEntityManager().createQuery("SELECT a FROM Pojazd a WHERE typ='" + type + "'", Pojazd.class).getResultList();

        for (Pojazd pojazd : list) {
            vehicles.add(pojazd);
        }
        return vehicles;
    }

    private static ObservableList<Klient> getClientObservableList() {
        ObservableList<Klient> clients = FXCollections.observableArrayList();
        List<Klient> list = DBConnector.getInstance().getEntityManager().createQuery("SELECT a FROM Klient a ", Klient.class).getResultList();

        for (Klient klient : list) {
            clients.add(klient);
        }
        return clients;
    }

    private static ObservableList<Wypozyczenie> getRentObservableList() {
        ObservableList<Wypozyczenie> rents = FXCollections.observableArrayList();
        List<Wypozyczenie> list = DBConnector.getInstance().getEntityManager().createQuery("SELECT a FROM Wypozyczenie a ", Wypozyczenie.class).getResultList();

        for (Wypozyczenie wypozyczenie : list) {
            rents.add(wypozyczenie);
        }
        return rents;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setLayout(String pathToFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(pathToFXML));

        BorderPane borderPane = null;
        try {
            borderPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }

        scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("KCK2");
        primaryStage.show();
    }

    public void startApp(Stage stage) throws IOException, InterruptedException {
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("image/WindowIcon.png"));
        setLayout("/layout/WelcomeScreen.fxml");
        //WelcomeScreen.ProgressMax();


    }
}
