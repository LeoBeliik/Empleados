import com.sun.javafx.application.LauncherImpl;
import data.CDepartments;
import data.CEmployees;
import data.PantInicio;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
//TODO limpiar codigo, probar y terminar :D
public class Main extends Application {

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, PantInicio.class, args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("ui/Main.fxml"));
        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.show();
    }

    @Override
    public void init() {
        CDepartments.LoadTable();
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(50));
        CEmployees.LoadTable();
    }
}
