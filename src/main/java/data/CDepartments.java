package data;

import dao.entities.Departments;
import dao.imp.DepartmentsImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CDepartments implements Initializable {

    @FXML
    private TableView<Departments> tblDepto;
    @FXML
    private Label lblDepto;
    @FXML
    private TextField txtNameDepto, txtNumDepto, txtSearchDepto;
    @FXML
    private GridPane gpAdd;

    private static final DepartmentsImp DI = new DepartmentsImp();
    private static final ObservableList<Departments> list = FXCollections.observableArrayList();
    private final FilteredList<Departments> filtro = new FilteredList<>(list, p -> true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateTable();
        Search();
        Footer(true);
    }

    public void AAdd() {
        Footer(false);
        txtNumDepto.setDisable(false);
        txtNumDepto.clear();
        txtNameDepto.clear();
    }

    public void ADel() {
        try {
            String dato = tblDepto.getSelectionModel().getSelectedItem().getDeptNo();
            if (Alerts.confirmation("Realmente desea borrar estos datos?")) {
                DI.dltDep(dato);
                LoadTable();
            }
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void AEdit() {
        try {
            txtNumDepto.setText(tblDepto.getSelectionModel().getSelectedItem().getDeptNo());
            txtNumDepto.setDisable(true);
            txtNameDepto.setText(tblDepto.getSelectionModel().getSelectedItem().getDeptName());
            Footer(false);
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a editar");
        }
    }

    public void AOk() {
        if (!txtNumDepto.getText().isEmpty() && !txtNameDepto.getText().isEmpty()) {
            if (Character.isDigit(txtNumDepto.getText().charAt(0)) || txtNumDepto.getText().length() > 4)
                Alerts.info("El número de departamento debe empezar con una letra y tener un máximo de 4 caracteres.");
            else if (DI.findDep(txtNumDepto.getText()) != null && !txtNumDepto.isDisable())
                Alerts.info("Ese número de departamento ya está siendo usado, por favor elija otro.");
            else if (Alerts.confirmation("¿Desea guardar los cambios?")) {
                DI.addDep(txtNumDepto.getText(), txtNameDepto.getText());
                Footer(true);
                LoadTable();
            }
        } else
            Alerts.info("Los datos no pueden estar en blanco");
    }

    public void ACancel() {
        Footer(true);
    }

    private void Footer(boolean footer) {
        lblDepto.setVisible(footer);
        gpAdd.setVisible(!footer);
    }

    private void Search() {
        txtSearchDepto.textProperty().addListener((observable, oldValue, newValue) ->
                filtro.setPredicate(Deparments -> {
                    //Si el filtro está vacio, muestra todos los datos.
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String filtroMin = newValue.toLowerCase();

                    if (Deparments.getDeptNo().toLowerCase().contains(filtroMin))
                        return true; //filtra por numero de departamento.
                    else
                        return Deparments.getDeptName().toLowerCase().contains(filtroMin); //filtra por nombre de departamento.
                })
        );

        SortedList<Departments> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tblDepto.comparatorProperty());
        tblDepto.setItems(datos);
    }

    private void CreateTable() {
        TableColumn<Departments, String> id = new TableColumn<>("Número");
        id.setMinWidth(100);
        id.setCellValueFactory(new PropertyValueFactory<>("DeptNo"));
        TableColumn<Departments, String> name = new TableColumn<>("Nombre");
        name.setMinWidth(276);
        name.setCellValueFactory(new PropertyValueFactory<>("DeptName"));
        tblDepto.setItems(list);
        tblDepto.getColumns().addAll(Arrays.asList(id, name));
    }

    public static void LoadTable() {
        list.clear();
        list.addAll(DI.getAll());
    }
}