package layoutLogic.customer;

import domain.Klient;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPane;
import layoutLogic.DBConnector;
import layoutLogic.WindowSingleton;

import java.io.IOException;

public class CustomerOptions {

    @FXML
    private TextField nazwisko;
    @FXML
    private TextField imie;
    @FXML
    private TextField pesel;
    @FXML
    private TextField dataUrodzenia;
    @FXML
    private TextField adres;
    @FXML
    private TextField numerPrawaJazdy;
    @FXML
    private TextField telefon;


    @FXML
    private StackPane printCustomerTabStackPane;

    @FXML
    private TextField deleteCustomerIdTextField;


    @FXML
    private TextField editCustomerIdTextField;
    @FXML
    private TextField editCustomerNazwiskoTextField;
    @FXML
    private TextField editCustomerImieTextField;
    @FXML
    private TextField editCustomerAdresTextField;
    @FXML
    private TextField editCustomerDataUTextField;
    @FXML
    private TextField editCustomerTelefonTextField;
    @FXML
    private TextField editCustomerNumerPJTextField;
    @FXML
    private TextField editCustomerPeselTextField;
    @FXML
    private TextField editCustomerNewNazwiskoTextField;
    @FXML
    private TextField editCustomerNewImieTextField;
    @FXML
    private TextField editCustomerNewAdresTextField;
    @FXML
    private TextField editCustomerNewDataUTextField;
    @FXML
    private TextField editCustomerNewTelefonTextField;
    @FXML
    private TextField editCustomerNewNumerPJTextField;
    @FXML
    private TextField editCustomerNewPeselTextField;
    @FXML
    private TextField editCustomerNewIdTextField;


    private String _nazwisko, _imie, _pesel, _dataUrodzenia, _adres, _numerPrawaJazdy, _telefon;
    private long _id;

    public CustomerOptions() {
    }

    @FXML
    public void addCustomerToDB() {

        if (nazwisko.getText().equals("") || imie.getText().equals("") || pesel.getText().equals("") || dataUrodzenia.getText().equals("") || adres.getText().equals("")|| numerPrawaJazdy.getText().equals("") || telefon.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }
        _nazwisko = nazwisko.getText();
        _imie = imie.getText();
        _pesel = pesel.getText();
        _dataUrodzenia = dataUrodzenia.getText();
        _adres = adres.getText();
        _numerPrawaJazdy = numerPrawaJazdy.getText();
        _telefon=telefon.getText();

        DBConnector.getInstance().start();
        DBConnector.getInstance().addKlient(new Klient( _numerPrawaJazdy, _nazwisko, _imie, _dataUrodzenia, _adres, _pesel, _telefon));
        DBConnector.getInstance().stop();
        WindowSingleton.alert("Dodano klienta");

        nazwisko.setText("");
        imie.setText("");
        pesel.setText("");
        dataUrodzenia.setText("");
        adres.setText("");
        numerPrawaJazdy.setText("");
        telefon.setText("");

    }

    public void printCustomerList() {
        final TableView<Klient> table = WindowSingleton.createClientTable();
        printCustomerTabStackPane.getChildren().add(table);
    }

    public void deleteCustomerFromDB() {

        try {
            _id = Long.parseLong(deleteCustomerIdTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("zły format");
        }
        DBConnector.getInstance().start();
        Klient klient = DBConnector.getInstance().getEntityManager().find(Klient.class, _id);

        if (klient == null) {
            WindowSingleton.alert("Nie ma takiego klienta");
            DBConnector.getInstance().stop();
            return;
        }

        WindowSingleton.alert("Usunięto klienta o id = " + _id);
        System.out.println("usunieto klienta o id " + _id);
        DBConnector.getInstance().deleteKlient(klient);
        DBConnector.getInstance().stop();
        deleteCustomerIdTextField.setText("");

    }

    public void showMainMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }
    public void showEditCustomerList() {
        WindowSingleton.showClientTable(editCustomerIdTextField);
    }

    public void showDeleteCustomerList() {
        WindowSingleton.showClientTable(deleteCustomerIdTextField);
    }

    public void fillEditedCustomerFields() {

        try {
            _id = Long.parseLong(editCustomerIdTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Nie ma klienta o takim ID");
            return;
        }

        Klient klient = DBConnector.getInstance().getEntityManager().find(Klient.class, Long.parseLong(editCustomerIdTextField.getText()));
        if (klient == null) {
            WindowSingleton.alert("Nie ma klienta o tym ID");
            return;
        }

        editCustomerNazwiskoTextField.setText("NAZWISKO - " + klient.getNazwisko());
        editCustomerImieTextField.setText("IMIE - " + klient.getImie());
        editCustomerAdresTextField.setText("ADRES - " + klient.getAdres());
        editCustomerDataUTextField.setText("DATA URODZENIA - " + klient.getData_urodzenia());
        editCustomerPeselTextField.setText("PESEL - " + klient.getPesel());
        editCustomerNumerPJTextField.setText("NUMER PRAWA JAZDY - " + klient.getNr_prawa_jazdy());
        editCustomerTelefonTextField.setText("TELEFON - " + klient.getTelefon());
    }

    public void editCustomer() {
        Long id_;

        try {
            id_ = Long.parseLong(editCustomerIdTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Zły format");
            return;
        }

        if (editCustomerNewNazwiskoTextField.getText().equals("") || editCustomerNewImieTextField.getText().equals("") || editCustomerNewAdresTextField.getText().equals("") || editCustomerNewDataUTextField.getText().equals("") || editCustomerNewPeselTextField.getText().equals("") || editCustomerNewNumerPJTextField.getText().equals("") || editCustomerNewTelefonTextField.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }

        try {
            id_ = Long.parseLong(editCustomerIdTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Nie ma klienta o tym ID");
            return;
        }

        Klient klient = DBConnector.getInstance().getEntityManager().find(Klient.class, Long.parseLong(editCustomerIdTextField.getText()));

        if (klient == null) {
            if (klient == null) {
                WindowSingleton.alert("Nie ma klienta o tym ID");
                return;
            }
        }

        String[] newCustomerDetails = new String[7];
        newCustomerDetails[0] = editCustomerNewNazwiskoTextField.getText();
        newCustomerDetails[1] = editCustomerNewImieTextField.getText();
        newCustomerDetails[2] = editCustomerNewDataUTextField.getText();
        newCustomerDetails[3] = editCustomerNewAdresTextField.getText();
        newCustomerDetails[4] = editCustomerNewPeselTextField.getText();
        newCustomerDetails[5] = editCustomerNewNumerPJTextField.getText();
        newCustomerDetails[6] = editCustomerNewPeselTextField.getText();
        klient.setParameters(newCustomerDetails);
        DBConnector.getInstance().editKlient(klient);
        WindowSingleton.alert("Zedytowano klienta");

        editCustomerIdTextField.setText("");
        editCustomerNazwiskoTextField.setText("");
        editCustomerImieTextField.setText("");
        editCustomerDataUTextField.setText("");
        editCustomerAdresTextField.setText("");
        editCustomerPeselTextField.setText("");
        editCustomerNumerPJTextField.setText("");
        editCustomerTelefonTextField.setText("");
        editCustomerNewNazwiskoTextField.setText("");
        editCustomerNewImieTextField.setText("");
        editCustomerNewDataUTextField.setText("");
        editCustomerNewAdresTextField.setText("");
        editCustomerNewPeselTextField.setText("");
        editCustomerNewNumerPJTextField.setText("");
        editCustomerNewTelefonTextField.setText("");

    }

}
