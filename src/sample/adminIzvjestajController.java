package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class adminIzvjestajController {

    private onDAO dao;
    public TableColumn profesorCol;
    public TableColumn predmetCol;
    public TableColumn spredmetCol;
    public TableColumn dcasaCol;
    public TableColumn dobjaveCol;
    public TableColumn iuceniciCol;
    public TableColumn odjelCol;
    public TableView<izvjestajAdmin> tableViewIzvjestaj;
    private ObservableList<izvjestajAdmin> listIzvjestaj;

    public adminIzvjestajController() {
        dao=onDAO.getInstance();
        listIzvjestaj= FXCollections.observableArrayList(dao.izvjestajAdmin());
    }
    public void initialize()
    {
        tableViewIzvjestaj.setItems(FXCollections.observableArrayList(listIzvjestaj));
        profesorCol.setCellValueFactory(new PropertyValueFactory("naziv"));
        predmetCol.setCellValueFactory(new PropertyValueFactory("predmet"));
        spredmetCol.setCellValueFactory(new PropertyValueFactory("sadrzajcasa"));
        dcasaCol.setCellValueFactory(new PropertyValueFactory("datumcasa"));
        dobjaveCol.setCellValueFactory(new PropertyValueFactory("datumobjave"));
        iuceniciCol.setCellValueFactory(new PropertyValueFactory("izostaliucenici"));
        odjelCol.setCellValueFactory(new PropertyValueFactory("odjeljenje"));

    }
    public void izlazIzIzvjestajaButton(ActionEvent actionEvent)
    {
        Stage stage=(Stage) tableViewIzvjestaj.getScene().getWindow();
        stage.close();
    }
}
