package layoutLogic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class WelcomeScreen {

    @FXML
    Button start;
    @FXML
    ProgressBar pbar;

    public void ProgressMax() {
        for (int i = 0; i < 100; i++) {
            pbar.setProgress(i / 100);
            if (i == 80) {
                DBConnector.getInstance();
            }

        }
        start.setEnabled(true);
    }

    @FXML
    void initialize()
    {
        ProgressMax();
    }

    @FXML
    public void showMainMenu() throws IOException {
        WindowSingleton.getInstance().setLayout("/layout/MainMenuScreen.fxml");
    }

}
