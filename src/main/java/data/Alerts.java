package data;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

class Alerts {

    static void info( String text) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Alerta!");
        info.setHeaderText(null);
        info.setContentText(text);
        info.showAndWait();
    }

    static boolean confirmation(String text) {
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Alerta!");
        conf.setHeaderText(null);
        conf.setTitle("Confirmation Dialog");
        conf.setContentText(text);

        Optional<ButtonType> result = conf.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    static String choose() {
        Alert cho = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnMan = new ButtonType("Manager");
        ButtonType btnEmp = new ButtonType("Empleado");
        ButtonType btnCancel = ButtonType.CANCEL;
        cho.getButtonTypes().setAll(btnMan, btnEmp, btnCancel);
        cho.setTitle("Alerta!");
        cho.setHeaderText(null);
        cho.setHeaderText("Ver departamentos de Managers o Empleados?");

        Optional<ButtonType> result = cho.showAndWait();
        if (result.isPresent() && result.get() == btnMan)
            return "Manager";
        else if (result.isPresent() && result.get() == btnEmp)
            return "Employee";
        else
            return "Exit";
    }

}

