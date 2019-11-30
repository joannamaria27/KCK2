package layoutLogic.rent;

import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import layoutLogic.DBConnector;
import layoutLogic.WindowSingleton;

import java.io.IOException;
import java.util.List;

public class RentOptions {

    @FXML
    private StackPane printRentsTabStackPane;
    @FXML
    private TextField editRentIdTextField;

    @FXML
    private TextField editRentBegDateTextField;
    @FXML
    private TextField editRentRetDateTextField;
    @FXML
    private TextField editRentAccessCodeTextField;
    @FXML
    private TextField editRentPriceTextField;
    @FXML
    private TextField editRentEmployeeTextField;
    @FXML
    private TextField editRentNewBegDateTextField;
    @FXML
    private TextField editRentNewRetDateTextField;
    @FXML
    private TextField editRentNewAccessCodeTextField;
    @FXML
    private TextField editRentNewPriceTextField;
    @FXML
    private TextField editRentNewEmployeeTextField;
    @FXML
    private TextField deleteRentIdTextField;

    private long _id;

    public void printRentList() {
        final TableView<Pojazd> table = WindowSingleton.createRentsTable();
        printRentsTabStackPane.getChildren().add(table);
    }

    public void showEditRentList() {
        WindowSingleton.showRentTable(editRentIdTextField);
    }

    public void fillEditedRentFields() {

        try {
            _id = Long.parseLong(editRentIdTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Nie ma wypożyczenia o takim ID");
            return;
        }

        Wypozyczenie wypozyczenie = DBConnector.getInstance().getEntityManager().find(Wypozyczenie.class, Long.parseLong(editRentIdTextField.getText()));
        if (wypozyczenie == null) {
            WindowSingleton.alert("Nie ma wypożyczenia o tym ID");
            return;
        }

        editRentBegDateTextField.setText("DATA WYPOŻYCZENIA - " + wypozyczenie.getData_wypozyczenia());
        editRentRetDateTextField.setText("MODEL - " + wypozyczenie.getData_oddania());
        editRentAccessCodeTextField.setText("STAN POJAZDU - " + wypozyczenie.getKod_dostepu());
        editRentPriceTextField.setText("UBEZPIECZENIE - " + wypozyczenie.getCena());
        editRentEmployeeTextField.setText("DOSTĘPNOŚĆ - " + wypozyczenie.getPracownik());
    }

    public void editRent() {
        Long id_;

        try {
            id_ = Long.parseLong(editRentIdTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Zły format");
            return;
        }

        if (editRentNewBegDateTextField.getText().equals("") || editRentNewRetDateTextField.getText().equals("") || editRentNewAccessCodeTextField.getText().equals("") || editRentNewPriceTextField.getText().equals("") || editRentNewEmployeeTextField.getText().equals("")) {
            WindowSingleton.alert("Niepoprawne dane");
            return;
        }

        try {
            id_ = Long.parseLong(editRentIdTextField.getText());
        } catch (NumberFormatException e) {
            WindowSingleton.alert("Nie ma wypożyczenia o tym ID");
            return;
        }

        Wypozyczenie wypozyczenie = DBConnector.getInstance().getEntityManager().find(Wypozyczenie.class, Long.parseLong(editRentIdTextField.getText()));

        if (wypozyczenie == null) {
            if (wypozyczenie == null) {
                WindowSingleton.alert("Nie ma wypożyczenia o tym ID");
                return;
            }
        }

        String[] newRentDetails = new String[5];
        newRentDetails[0] = editRentNewBegDateTextField.getText();
        newRentDetails[1] = editRentNewRetDateTextField.getText();
        newRentDetails[2] = editRentNewAccessCodeTextField.getText();
        newRentDetails[3] = editRentNewPriceTextField.getText();
        newRentDetails[4] = editRentNewEmployeeTextField.getText();
        wypozyczenie.setParameters(newRentDetails);
        DBConnector.getInstance().editWypozyczenie(wypozyczenie);
        WindowSingleton.alert("Zedytowano wypożyczenie");

        editRentIdTextField.setText("");
        editRentBegDateTextField.setText("");
        editRentRetDateTextField.setText("");
        editRentAccessCodeTextField.setText("");
        editRentPriceTextField.setText("");
        editRentEmployeeTextField.setText("");
        editRentNewBegDateTextField.setText("");
        editRentNewRetDateTextField.setText("");
        editRentNewAccessCodeTextField.setText("");
        editRentNewPriceTextField.setText("");
        editRentNewEmployeeTextField.setText("");


    }

    public void showDeleteRentList() {
        WindowSingleton.showRentTable(deleteRentIdTextField);
    }

    public void deleteRentFromDB() {

        try {
            _id = Long.parseLong(deleteRentIdTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("zły format");
        }
        DBConnector.getInstance().start();
        Wypozyczenie wypozyczenie = DBConnector.getInstance().getEntityManager().find(Wypozyczenie.class, _id);

        if (wypozyczenie == null) {
            WindowSingleton.alert("Nie ma takiego wypożyczenie");
            DBConnector.getInstance().stop();
            return;
        }

        WindowSingleton.alert("Usunięto wypożyczenie o id = " + _id);
        System.out.println("usunieto wypożyczenie o id " + _id);
        DBConnector.getInstance().deleteWypozyczenie(wypozyczenie);
        DBConnector.getInstance().stop();
        deleteRentIdTextField.setText("");
    }

    public void showMainMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

}
