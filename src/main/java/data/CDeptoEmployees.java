package data;

import dao.entities.DEmployee;
import dao.imp.DepEmployeeImp;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CDeptoEmployees implements Initializable {

    @FXML
    private TableView<DEmployee> tblMana;
    @FXML
    private Label lblMana;
    @FXML
    private TextField txtSearchMana, txtDepto;
    @FXML
    private DatePicker dateFromMana, dateToMana;
    @FXML
    private GridPane gpAdd;

    private static final DepEmployeeImp MA = new DepEmployeeImp();
    private static final ObservableList<DEmployee> list = FXCollections.observableArrayList();
    private final FilteredList<DEmployee> filtro = new FilteredList<>(list, p -> true);
    static int idEmp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateTable();
        Search();
        Footer(true);
    }

    public void AAdd() {
        Footer(false);
        txtDepto.clear();
        dateFromMana.setValue(null);
        dateToMana.setValue(null);
    }

    public void ADel() {
        try {
            DEmployee dato = tblMana.getSelectionModel().getSelectedItem();
            if (Alerts.confirmation("Realmente desea borrar estos datos?")) {
                MA.dltDepEmp(dato.getDeptNo(), dato.getEmpNo());
                LoadTable();
            }
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void AEdit() {
        try {
            txtDepto.setDisable(true);
            txtDepto.setText(tblMana.getSelectionModel().getSelectedItem().getDeptNo());
            dateFromMana.setValue(tblMana.getSelectionModel().getSelectedItem().getFromDate());
            dateToMana.setValue(tblMana.getSelectionModel().getSelectedItem().getToDate());
            Footer(false);
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a editar");
        }
    }

    public void AOk() {
        if (txtDepto.getText() == null || txtDepto.getText().isEmpty() || dateFromMana.getValue() == null || dateToMana == null)
            Alerts.info("Los datos no pueden estar en blanco");
        else if (MA.getName(txtDepto.getText()).isEmpty())
            Alerts.info("Número de departamento invalido");
        else if (MA.findEmpDep(txtDepto.getText(), idEmp) != null && !txtDepto.isDisabled())
            Alerts.info("Esos datos ya se encuentran registrados.");
        else if (Alerts.confirmation("¿Desea guardar los cambios?")) {
            MA.addDepEmp(txtDepto.getText(), idEmp, dateFromMana.getValue(), dateToMana.getValue());
            Footer(true);
            txtDepto.setDisable(false);
            LoadTable();
        }
    }

    public void ACancel() {
        Footer(true);
    }

    private void Footer(boolean footer) {
        lblMana.setVisible(footer);
        gpAdd.setVisible(!footer);
    }

    public void AExitMana() {
        TabManager.getInstance().fireEvent("Back");
    }

    private void Search() {
        txtSearchMana.textProperty().addListener((observable, oldValue, newValue) ->
                filtro.setPredicate(DEmployee -> {
                    //Si el filtro está vacio, muestra todos los datos.
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String filtroMin = newValue.toLowerCase();

                    if (String.valueOf(DEmployee.getDeptNo()).toLowerCase().contains(filtroMin))
                        return true; //filtra por Departamento.
                    else if ((String.valueOf(DEmployee.getToDate()).toLowerCase().contains(filtroMin)))
                        return true; //filtra por fecha inicial.
                    else
                        return String.valueOf(DEmployee.getToDate()).toLowerCase().contains(filtroMin); //filtra por fecha final.
                })
        );

        SortedList<DEmployee> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tblMana.comparatorProperty());
        tblMana.setItems(datos);
    }

    private void CreateTable() {
        TableColumn<DEmployee, Integer> idEmp = new TableColumn<>("");
        idEmp.setVisible(false);
        idEmp.setCellValueFactory(new PropertyValueFactory<>("empNo"));
        TableColumn<DEmployee, String> idDep = new TableColumn<>("");
        idDep.setVisible(false);
        idDep.setCellValueFactory(new PropertyValueFactory<>("deptNo"));
        TableColumn<DEmployee, String> name = new TableColumn<>("Departamentos");
        name.setMinWidth(130);
        name.setCellValueFactory(new PropertyValueFactory<>("deptName"));
        TableColumn<DEmployee, LocalDate> fDate = new TableColumn<>("inicio");
        fDate.setMinWidth(123);
        fDate.setCellValueFactory(new PropertyValueFactory<>("FromDate"));
        TableColumn<DEmployee, LocalDate> tDate = new TableColumn<>("final");
        tDate.setMinWidth(123);
        tDate.setCellValueFactory(new PropertyValueFactory<>("ToDate"));

        tblMana.setItems(list);
        tblMana.getColumns().addAll(Arrays.asList(idEmp, idDep, name, fDate, tDate));
    }

    static void LoadTable() {
        list.clear();
        list.addAll(MA.getAll(idEmp));
    }
}
