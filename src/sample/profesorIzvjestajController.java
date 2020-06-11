package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class profesorIzvjestajController {

    private onDAO dao;
    public TableView<profesorIzvjestaj>tableViewIzvjestajP;
    public TableColumn predmetCol;
    public TableColumn dcasaCol;
    public TableColumn dobjaveCol;
    public TableColumn spredmetCol;
    public TableColumn iuceniciCol;
    public TableColumn odjeljenjeCol;
    private ObservableList<profesorIzvjestaj> listIzvjestaj;

    public profesorIzvjestajController(Profesori prof) {
        dao=onDAO.getInstance();
        listIzvjestaj= FXCollections.observableArrayList(dao.profesorIzvjestaj(prof));
    }
    public void initialize()
    {
        tableViewIzvjestajP.setItems(FXCollections.observableArrayList(listIzvjestaj));
        predmetCol.setCellValueFactory(new PropertyValueFactory("predmet"));
        dcasaCol.setCellValueFactory(new PropertyValueFactory("datumcasa"));
        dobjaveCol.setCellValueFactory(new PropertyValueFactory("datumobjave"));
        spredmetCol.setCellValueFactory(new PropertyValueFactory("sadrzajcasa"));
        iuceniciCol.setCellValueFactory(new PropertyValueFactory("izostaliucenici"));
        odjeljenjeCol.setCellValueFactory(new PropertyValueFactory("odjeljenje"));

    }
    public void izlazIzIzvjestajaButton(ActionEvent actionEvent)
    {
        Stage stage=(Stage) tableViewIzvjestajP.getScene().getWindow();
        stage.close();
    }
}
