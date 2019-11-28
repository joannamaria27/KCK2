package layoutLogic;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WelcomeScreen {

    @FXML
    Button start;
    @FXML
    ProgressBar pbar;
    DoubleProperty progress = new SimpleDoubleProperty(0);

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public void ProgressMax() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            pbar.setProgress(i / 100);
            if (i == 2) {
                DBConnector dbConnector = DBConnector.getInstance();
            }
            System.out.println("progressMax");
            TimeUnit.SECONDS.sleep(1);
        }
        start.setEnabled(true);
    }

    @FXML
    void initialize() throws InterruptedException, IOException {
        //ProgressMax();
    }

    @FXML
    public void showMainMenu() throws IOException, InterruptedException {
        // DBConnector dbConnector = DBConnector.getInstance();
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

}
