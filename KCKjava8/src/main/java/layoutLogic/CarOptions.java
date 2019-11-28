package layoutLogic;

import domain.Pojazd;
import domain.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private String _marka, _model, _stanPojazdu, _ubezpieczenie, _dostepnosc;
    private long _id;

    @FXML
    public void addCarToDB(){
        _marka = marka.getText();
        _model = model.getText();
        _stanPojazdu = stanPojazdu.getText();
        _ubezpieczenie = ubezpieczenie.getText();
        _dostepnosc = dostepnosc.getText();



        System.out.println(_marka+_model+_stanPojazdu+_ubezpieczenie+_dostepnosc);
        DBConnector.getInstance().start();
        DBConnector.getInstance().addPojazd(new Pojazd("samochod", _marka, _model, _ubezpieczenie, _stanPojazdu, _dostepnosc));
        DBConnector.getInstance().stop();
    }

    @FXML
    public void deleteCarFromDB(){
        try {
            _id = Long.parseLong(id.getText());
        } catch (NumberFormatException e) {
            // todo wyswietlanie informacji o zlym formacie gdzies w oknie
            System.out.println("zły format");
        }
        DBConnector.getInstance().start();
        Pojazd pojazd = DBConnector.getInstance().entityManager.find(Pojazd.class, _id);
        List<Wypozyczenie> list = DBConnector.getInstance().entityManager.createQuery("SELECT a FROM Wypozyczenie a WHERE id_pojazdu_id='" + _id + "'", Wypozyczenie.class).getResultList();

        if(pojazd == null){
            // todo wyswietlanie bledu ze nie ma takiego pojazdu w bazie
            System.out.println("nie ma takiego pojazdu");
            DBConnector.getInstance().stop();
            return;
        }
        if(list.size()>0){
            // todo sout przeniesc do okna
            System.out.println("pojazd jest w trakcie wypozyczenia");
            DBConnector.getInstance().stop();
            return;
        }
        // todo sout przeniesc do okna
        System.out.println("usunieto pojazd o id "+_id);
        DBConnector.getInstance().deletePojazd(pojazd);
        DBConnector.getInstance().stop();
    }

    public void showCarsInNewWindow(){
        // todo wyswietlanie listy samochodow w nowym oknie
    }

    @FXML
    public void showMainMenu() throws IOException, InterruptedException {
        // DBConnector dbConnector = DBConnector.getInstance();
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

}