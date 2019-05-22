package data;


import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PantInicio extends Preloader {

    private Stage preloaderStage;
    private final Label lblL = new Label("10%");

    public PantInicio() {
    }

    @Override
    public void start(Stage primaryStage) {
        Label lblC = new Label("Cargando...");
        lblC.layoutXProperty().setValue(237);
        lblC.layoutYProperty().setValue(163);
        lblC.textFillProperty().setValue(Color.WHITE);
        lblC.setFont(Font.font("Segoe UI Symbol", 21));

        lblL.layoutXProperty().setValue(269);
        lblL.layoutYProperty().setValue(194);
        lblL.textFillProperty().setValue(Color.WHITE);
        lblL.setFont(Font.font("Segoe UI Symbol", 21));

        AnchorPane root = new AnchorPane();
        root.setMinSize(600, 400);
        root.setStyle("-fx-background-color: linear-gradient(from 10% 10% to 100% 100%, #245582, #bcdaf4);");
        root.getChildren().addAll(lblC, lblL);

        Scene scene = new Scene(root);
        this.preloaderStage = primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            lblL.setText((int)((ProgressNotification) info).getProgress()+"%");
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        if (type == StateChangeNotification.Type.BEFORE_START) {
            lblL.setText("75%");
            FadeTransition ft = new FadeTransition(Duration.seconds(5), preloaderStage.getScene().getRoot());
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            lblL.setText("99%");
            EventHandler<ActionEvent> eh = t -> preloaderStage.hide();
            ft.setOnFinished(eh);
            ft.play();
        }
    }
}

