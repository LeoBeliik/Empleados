package data;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CMain implements Initializable {
    @FXML
    private TabPane tabPanel;
    @FXML
    private Button btnMainEmp, btnMainDep;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TabManager.getInstance().register(s -> {
            switch (s) {
                case "Title":
                    SelectTab(3); //Títulos
                    break;
                case "Salary":
                    SelectTab(4); //Salarios
                    break;
                case "Manager":
                    SelectTab(5); //Departamentos Manager
                    break;
                case "Employee":
                    SelectTab(6); //Departamentos Empleados
                    break;
                case "Back":
                    SelectTab(2); //Volver
                    break;
                default:
                    SelectTab(0); //Página inicial
            }
        });
    }

    public void ADep() {
        SelectTab(1); //Departamentos
        btnMainEmp.getStyleClass().remove("pbutton");
        btnMainDep.getStyleClass().add("pbutton");
    }

    public void AEmp() {
        SelectTab(2); //Empleados
        btnMainDep.getStyleClass().remove("pbutton");
        btnMainEmp.getStyleClass().add("pbutton");
    }

    private void SelectTab(int tab) {
        tabPanel.getSelectionModel().select(tab);
    }

    public void AExit() {
        System.exit(1);
    }
}
