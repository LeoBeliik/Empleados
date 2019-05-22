package data;

import dao.entities.TitlePK;
import dao.entities.Titles;
import dao.imp.TitlesImp;
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

public class CTitles implements Initializable {

    @FXML
    private TableView<Titles> tblTit;
    @FXML
    private Label lblTit;
    @FXML
    private TextField txtSearchTit, txtTitleTit;
    @FXML
    private DatePicker dateFromTit, dateToTit;
    @FXML
    private GridPane gpAdd;


    private static final TitlesImp TI = new TitlesImp();
    private static final ObservableList<Titles> list = FXCollections.observableArrayList();
    private final FilteredList<Titles> filtro = new FilteredList<>(list, p -> true);
    static int idEmp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateTable();
        Search();
        Footer(true);
    }

    public void AAdd() {
        Footer(false);
        txtTitleTit.clear();
        dateFromTit.setValue(null);
        dateToTit.setValue(null);
    }

    public void ADel() {
        try {
            TitlePK dato = tblTit.getSelectionModel().getSelectedItem().getIdTi();
            if (Alerts.confirmation("Realmente desea borrar estos datos?")) {
                TI.dltTit(dato.getEmpNo(), dato.getTitle());
                LoadTable();
            }
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void AEdit() {
        try {
            txtTitleTit.setDisable(true);
            txtTitleTit.setText(tblTit.getSelectionModel().getSelectedItem().getTitle());
            dateFromTit.setValue(tblTit.getSelectionModel().getSelectedItem().getFromDateTit());
            dateToTit.setValue(tblTit.getSelectionModel().getSelectedItem().getToDateTit());
            Footer(false);

        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a editar");
        }
    }

    public void AOk() {
        if (!txtTitleTit.getText().isEmpty() && dateFromTit.getValue() != null && dateToTit.getValue() != null) {
            if (TI.findTit(idEmp, txtTitleTit.getText()) != null && !txtTitleTit.isDisabled())
                Alerts.info("Esos datos ya se encuentran registrados.");
            else if (Alerts.confirmation("¿Desea guardar los cambios?")) {
                TI.addTit(idEmp, txtTitleTit.getText(), dateFromTit.getValue(), dateToTit.getValue());
                Footer(true);
                txtTitleTit.setDisable(false);
                LoadTable();
            }
        } else
            Alerts.info("Los datos no pueden estar en blanco");
    }

    public void ACancel() {
        Footer(true);
    }

    private void Footer(boolean footer) {
        lblTit.setVisible(footer);
        gpAdd.setVisible(!footer);
    }

    public void AExit() {
        TabManager.getInstance().fireEvent("Back");
    }

    private void Search() {
        txtSearchTit.textProperty().addListener((observable, oldValue, newValue) ->
                filtro.setPredicate(Titles -> {
                    //Si el filtro está vacio, muestra todos los datos.
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String filtroMin = newValue.toLowerCase();

                    if (String.valueOf(Titles.getTitle()).toLowerCase().contains(filtroMin))
                        return true; //filtra por titúlo.
                    else if ((String.valueOf(Titles.getFromDateTit()).toLowerCase().contains(filtroMin)))
                        return true; //filtra por fecha inicial.
                    else
                        return String.valueOf(Titles.getToDateTit()).toLowerCase().contains(filtroMin); //filtra por fecha final.
                })
        );

        SortedList<Titles> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tblTit.comparatorProperty());
        tblTit.setItems(datos);
    }

    private void CreateTable() {
        TableColumn<Titles, Integer> id = new TableColumn<>("");
        id.setVisible(false);
        id.setCellValueFactory(new PropertyValueFactory<>("idEmpTit"));
        TableColumn<Titles, String> name = new TableColumn<>("Títulos");
        name.setMinWidth(126);
        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Titles, LocalDate> fDate = new TableColumn<>("Inicio");
        fDate.setMinWidth(125);
        fDate.setCellValueFactory(new PropertyValueFactory<>("fromDateTit"));
        TableColumn<Titles, LocalDate> tDate = new TableColumn<>("Final");
        tDate.setMinWidth(125);
        tDate.setCellValueFactory(new PropertyValueFactory<>("toDateTit"));

        tblTit.setItems(list);
        tblTit.getColumns().addAll(Arrays.asList(id, name, fDate, tDate));
    }

    static void LoadTable() {
        list.clear();
        list.addAll(TI.getAll(idEmp));
    }
}
