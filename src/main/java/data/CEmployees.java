package data;

import dao.entities.Employees;
import dao.imp.EmployeesImp;
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

public class CEmployees implements Initializable {

    @FXML
    private TableView<Employees> tblEmp;
    @FXML
    private TextField txtNameEmp, txtApeEmp;
    @FXML
    private ComboBox<Character> cmbSexEmp;
    @FXML
    private DatePicker dateNacEmp, dateContEmp;
    @FXML
    private GridPane gpAdd;
    @FXML
    private TextField txtSearchEmp;
    @FXML
    private Label lblEmp, lblSexo;

    private static final EmployeesImp EI = new EmployeesImp();
    private static final ObservableList<Employees> list = FXCollections.observableArrayList();
    private final FilteredList<Employees> filtro = new FilteredList<>(list, p -> true);
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateTable();
        Search();
        Footer(true);
    }

    public void AAdd() {
        id = tblEmp.getItems().get(tblEmp.getItems().size() - 1).getEmpNo() + 1;
        txtNameEmp.clear();
        txtApeEmp.clear();
        cmbSexEmp.getItems().setAll('M', 'F');
        lblSexo.setVisible(true);
        dateNacEmp.setValue(null);
        dateContEmp.setValue(null);
        Footer(false);
    }

    public void ADel() {
        try {
            int dato = tblEmp.getSelectionModel().getSelectedItem().getEmpNo();
            if (Alerts.confirmation("Realmente desea borrar estos datos?")) {
                EI.dltEmp(dato);
                LoadTable();
            }
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void AEdit() {
        try {
            id = tblEmp.getSelectionModel().getSelectedItem().getEmpNo();
            txtNameEmp.setText(tblEmp.getSelectionModel().getSelectedItem().getFirstName());
            txtApeEmp.setText(tblEmp.getSelectionModel().getSelectedItem().getLastName());
            cmbSexEmp.getSelectionModel().select(tblEmp.getSelectionModel().getSelectedItem().getSex() == 'M' ? 0 : 1);
            dateContEmp.setValue(tblEmp.getSelectionModel().getSelectedItem().getHireDate());
            dateNacEmp.setValue(tblEmp.getSelectionModel().getSelectedItem().getBirthDate());
            Footer(false);
        } catch (NullPointerException e) {
            Alerts.info("Debe seleccionar el elemento de la tabla a editar.");
        }
    }

    public void AOk() {
        if (txtNameEmp.getText().isEmpty() || txtApeEmp.getText().isEmpty() || cmbSexEmp.getValue() == null || dateContEmp.getValue() == null || dateNacEmp.getValue() == null)
            Alerts.info("Los datos no pueden estar en blanco.");
        else if (Alerts.confirmation("¿Desea guardar los cambios?")) { //Al ser el ID de empleado autoincremental no se pueden duplicar.
            String name = txtNameEmp.getText();
            String ap = txtApeEmp.getText();
            char sex = cmbSexEmp.getValue();
            LocalDate hire = dateContEmp.getValue();
            LocalDate nac = dateNacEmp.getValue();
            EI.addEmp(id, name, ap, sex, hire, nac);
            LoadTable();
            Footer(true);
        }
    }

    public void Acmb() {
        try {
            cmbSexEmp.getSelectionModel().getSelectedItem();
            lblSexo.setVisible(false);
        } catch (NullPointerException ignored) {}
    }

    public void ASalEmp() {
        try {
            CSalaries.idEmp = tblEmp.getSelectionModel().getSelectedItem().getEmpNo();
            CSalaries.LoadTable();
            TabManager.getInstance().fireEvent("Salary");
        } catch (NullPointerException e) {
            Alerts.info("Debes seleccionar un elemento de la lista");
        }
    }

    public void ATitEmp() {
        try {
            CTitles.idEmp = tblEmp.getSelectionModel().getSelectedItem().getEmpNo();
            CTitles.LoadTable();
            TabManager.getInstance().fireEvent("Title");
        } catch (NullPointerException e) {
            Alerts.info("Debes seleccionar un elemento de la lista");
        }
    }

    public void ADeptEmp() {
        try {
            int empNo = tblEmp.getSelectionModel().getSelectedItem().getEmpNo();
            String choose = Alerts.choose();

            if (!choose.equals("Exit")) {
                TabManager.getInstance().fireEvent(choose);

            }
            if (choose.equals("Manager")) {
                CDeptoManagers.idEmp = empNo;
                CDeptoManagers.LoadTable();
            }
            if (choose.equals("Employee")) {
                CDeptoEmployees.idEmp = empNo;
                CDeptoEmployees.LoadTable();
            }
        } catch (NullPointerException e) {
            Alerts.info("Debes seleccionar un elemento de la lista");
        }
    }

    public void ACancel() {
        Footer(true);
    }

    private void Footer(boolean footer) {
        lblEmp.setVisible(footer);
        gpAdd.setVisible(!footer);
    }

    private void Search() {
        txtSearchEmp.textProperty().addListener((observable, oldValue, newValue) ->
                filtro.setPredicate(Employees -> {
                    //Si el filtro está vacio, muestra todos los datos.
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String filtroMin = newValue.toLowerCase();

                    if (String.valueOf(Employees.getSex()).toLowerCase().contains(filtroMin))
                        return true; //filtra por sexo;
                    else if (Employees.getFirstName().toLowerCase().contains(filtroMin))
                        return true; //filtra por nombre.
                    else if (Employees.getLastName().toLowerCase().contains(filtroMin))
                        return true; //filtra por apellido.
                    else if (String.valueOf(Employees.getEmpNo()).toLowerCase().contains(filtroMin))
                        return true; //filtra por numero de empleado.
                    else if (String.valueOf(Employees.getHireDate()).toLowerCase().contains(filtroMin))
                        return true; //filtra por fecha de contratación.
                    else
                        return String.valueOf(Employees.getBirthDate()).toLowerCase().contains(filtroMin); //filtra por fecha de nacimiento.
                })
        );

        SortedList<Employees> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tblEmp.comparatorProperty());
        tblEmp.setItems(datos);
    }

    private void CreateTable() {
        TableColumn<Employees, Integer> id = new TableColumn<>("");
        id.setVisible(false);
        id.setCellValueFactory(new PropertyValueFactory<>("EmpNo"));
        TableColumn<Employees, String> name = new TableColumn<>("Nombre");
        name.setMaxWidth(80);
        name.setSortable(false);
        name.setReorderable(false);
        name.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        TableColumn<Employees, String> ap = new TableColumn<>("Apellido");
        ap.setMaxWidth(80);
        ap.setSortable(false);
        ap.setReorderable(false);
        ap.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        TableColumn<Employees, Character> sex = new TableColumn<>("Sexo");
        sex.setMaxWidth(42);
        sex.setSortable(false);
        sex.setReorderable(false);
        sex.setCellValueFactory(new PropertyValueFactory<>("Sex"));
        TableColumn<Employees, LocalDate> hireDate = new TableColumn<>("Contratación");
        hireDate.setMaxWidth(83);
        hireDate.setSortable(false);
        hireDate.setReorderable(false);
        hireDate.setCellValueFactory(new PropertyValueFactory<>("HireDate"));
        TableColumn<Employees, LocalDate> birthDate = new TableColumn<>("Nacimiento");
        birthDate.setMaxWidth(83);
        birthDate.setSortable(false);
        birthDate.setReorderable(false);
        birthDate.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));
        tblEmp.setItems(list);
        tblEmp.getColumns().addAll(Arrays.asList(id, name, ap, sex, hireDate, birthDate));
    }

    public static void LoadTable() {
        list.clear();
        list.addAll(EI.getAll());
    }

}
