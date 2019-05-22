package data;

import dao.entities.SalPK;
import dao.entities.Salaries;
import dao.imp.SalariesImp;
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

public class CSalaries implements Initializable {

    @FXML
    private TableView<Salaries> tblSal;
    @FXML
    private Label lblSal;
    @FXML
    private TextField txtSearchSal, txtSalarySal;
    @FXML
    private GridPane gpAdd;
    @FXML
    DatePicker dateFromSal, dateToSal;

    private static final SalariesImp SA = new SalariesImp();
    private static final ObservableList<Salaries> list = FXCollections.observableArrayList();
    private final FilteredList<Salaries> filtro = new FilteredList<>(list, p -> true);
    static int idEmp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateTable();
        Search();
        Footer(true);
    }

    public void AAdd() {
        Footer(false);
        txtSalarySal.clear();
        dateFromSal.setValue(null);
        dateToSal.setValue(null);
    }

    public void ADel() {
        try {
            SalPK dato = tblSal.getSelectionModel().getSelectedItem().getIdSa();
            if (Alerts.confirmation("Realmente desea borrar estos datos?")) {
                SA.dltSal(dato.getEmpNo(), dato.getFromDate(), dato.getToDate());
                LoadTable();
            }
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void AEdit() {
        try {
            dateFromSal.setDisable(true);
            dateToSal.setDisable(true);
            txtSalarySal.setText(String.valueOf(tblSal.getSelectionModel().getSelectedItem().getSalary()).replace("$", "").replace(" ", ""));
            dateFromSal.setValue(tblSal.getSelectionModel().getSelectedItem().getFromDateSal());
            dateToSal.setValue(tblSal.getSelectionModel().getSelectedItem().getToDateSal());
            Footer(false);
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a editar");
        }
    }

    public void AOk() {
        if (dateFromSal.getValue() != null && dateToSal.getValue() != null && !txtSalarySal.getText().isEmpty()) {
            if (SA.findSal(idEmp, dateFromSal.getValue(), dateToSal.getValue()) != null && !dateFromSal.isDisabled())
                Alerts.info("Esos datos ya se encuentran registrados.");
            else if (Alerts.confirmation("¿Desea guardar los cambios?")) {
                try {
                    SA.addSal(idEmp, dateFromSal.getValue(), dateToSal.getValue(), Integer.parseInt(txtSalarySal.getText()));
                    Footer(true);
                    dateFromSal.setDisable(false);
                    dateToSal.setDisable(false);
                    LoadTable();
                } catch (NumberFormatException e) {
                    Alerts.info("El salario debe contener solo números.");
                }
            }
        } else
            Alerts.info("Los datos no pueden estar en blanco");
    }

    public void ACancel() {
        Footer(true);
    }

    private void Footer(boolean footer) {
        lblSal.setVisible(footer);
        gpAdd.setVisible(!footer);
    }

    public void AExit() {
        TabManager.getInstance().fireEvent("Back");
    }

    private void Search() {
        txtSearchSal.textProperty().addListener((observable, oldValue, newValue) ->
                filtro.setPredicate(Salaries -> {
                    //Si el filtro está vacio, muestra todos los datos.
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String filtroMin = newValue.toLowerCase();

                    if (String.valueOf(Salaries.getSalary()).toLowerCase().contains(filtroMin))
                        return true; //filtra por salario.
                    else if ((String.valueOf(Salaries.getFromDateSal()).toLowerCase().contains(filtroMin)))
                        return true; //filtra por fecha inicial.
                    else
                        return String.valueOf(Salaries.getToDateSal()).toLowerCase().contains(filtroMin); //filtra por fecha final.
                })
        );

        SortedList<Salaries> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tblSal.comparatorProperty());
        tblSal.setItems(datos);
    }

    private void CreateTable() {
        TableColumn<Salaries, Integer> id = new TableColumn<>("");
        id.setVisible(false);
        id.setCellValueFactory(new PropertyValueFactory<>("idEmpSal"));
        TableColumn<Salaries, String> sal = new TableColumn<>("Salarios");
        sal.setMinWidth(116);
        sal.setCellValueFactory(new PropertyValueFactory<>("salary"));
        TableColumn<Salaries, LocalDate> fDate = new TableColumn<>("Inicio");
        fDate.setMinWidth(130);
        fDate.setCellValueFactory(new PropertyValueFactory<>("fromDateSal"));
        TableColumn<Salaries, LocalDate> tDate = new TableColumn<>("Final");
        tDate.setMinWidth(130);
        tDate.setCellValueFactory(new PropertyValueFactory<>("toDateSal"));
        tblSal.setItems(list);

        tblSal.getColumns().addAll(Arrays.asList(id, sal, fDate, tDate));
    }

    static void LoadTable() {
        list.clear();
        list.addAll(SA.getAll(idEmp));
    }
}
