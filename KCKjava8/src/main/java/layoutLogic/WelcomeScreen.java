package layoutLogic;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import java.awt.*;

public class WelcomeScreen {

    @FXML
    Button start;
    @FXML
    ProgressBar pbar;

    public void ProgressMax() {
        for (int i = 0; i < 100; i++) {
            pbar.setProgress(i / 100);
            if (i == 80) {
                //cos tam
            }

        }
        start.setEnabled(true);
    }

    @FXML
    void initialize()
    {

    }

}
