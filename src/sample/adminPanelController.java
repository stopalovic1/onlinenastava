package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class adminPanelController {
    private Profesori profesori;
    public Label adminLabel;
    private ObservableList<Profesori> listProfesori;
    private onDAO dao;
    public TableView<Profesori> tableViewProfesori;
    public TableColumn colProfesorId;
    public TableColumn colProfesorNaziv;
    public TableColumn colProfesorUsername;
    public TableColumn colProfesorSatiUTjednu;
    private double xOffset=0;
    private double yOffset=0;
    public Button buttonDodajProfesora;
    public Button buttonObrisiProfesora;
    public Button buttonIzlazAdminPanel;
    public Button buttonIzmijeni;
    public Button izvjestajButton;
    public adminPanelController(Profesori profesori) {
        this.profesori = profesori;
        dao=onDAO.getInstance();
        listProfesori= FXCollections.observableArrayList(dao.profesori());
    }
    public void initialize() {
        if(!listProfesori.isEmpty()) listProfesori.removeIf(profesor->profesor.getId()==profesori.getId());
        tableViewProfesori.setItems(listProfesori);
        adminLabel.setText(profesori.getNaziv()+" Panel");
        colProfesorId.setCellValueFactory(new PropertyValueFactory("id"));
        colProfesorNaziv.setCellValueFactory(new PropertyValueFactory("naziv"));
        colProfesorUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colProfesorSatiUTjednu.setCellValueFactory(new PropertyValueFactory("satiUTjednu"));
        buttonIzmijeni.setDisable(true);
        buttonObrisiProfesora.setDisable(true);
        tableViewProfesori.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!tableViewProfesori.getItems().isEmpty()) {
                    buttonIzmijeni.setDisable(false);
                    buttonObrisiProfesora.setDisable(false);

                }
                if(mouseEvent.getClickCount()==2 && !tableViewProfesori.getItems().isEmpty())
                {
                    buttonDodajProfesora.setDisable(true);
                    buttonIzlazAdminPanel.setDisable(true);
                    buttonObrisiProfesora.setDisable(true);
                    buttonIzmijeni.setDisable(true);
                    tableViewProfesori.setDisable(true);
                    izvjestajButton.setDisable(true);
                    Profesori p=tableViewProfesori.getSelectionModel().getSelectedItem();
                    if(p==null)
                    {
                        buttonDodajProfesora.setDisable(false);
                        buttonIzlazAdminPanel.setDisable(false);
                        tableViewProfesori.setDisable(false);
                        return;
                    }
                    Stage stage=new Stage();
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("profesorForma.fxml"));
                    profesoriFormaController cp=new profesoriFormaController();
                    cp.setProfesor(p);
                    loader.setController(cp);
                    Parent root= null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    stage.setScene(new Scene(root,750,400));
                    stage.initStyle(StageStyle.UNDECORATED);
                    root.setOnMousePressed(event -> {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    });
                    root.setOnMouseDragged(event -> {
                        stage.setX(event.getScreenX() - xOffset);
                        stage.setY(event.getScreenY() - yOffset);
                    });
                    stage.show();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            buttonDodajProfesora.setDisable(false);
                            buttonIzlazAdminPanel.setDisable(false);
                            buttonObrisiProfesora.setDisable(false);
                            buttonIzmijeni.setDisable(false);
                            tableViewProfesori.setDisable(false);
                            izvjestajButton.setDisable(false);
                        }
                    });
                }
            }
        }
        );
    }
    public void dodajProfesora(ActionEvent actionEvent) throws IOException {
        buttonDodajProfesora.setDisable(true);
        buttonIzmijeni.setDisable(true);
        buttonObrisiProfesora.setDisable(true);
        buttonIzlazAdminPanel.setDisable(true);
        tableViewProfesori.setDisable(true);
        izvjestajButton.setDisable(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registracijaForma.fxml"));
        registracijaController cp = new registracijaController();
        loader.setController(cp);
        Parent root = loader.load();
        Scene scene = new Scene(root, 290, 370);
        Stage appStage = new Stage();
        appStage.setScene(scene);
        appStage.setTitle("Registracija");
        appStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            appStage.setX(event.getScreenX() - xOffset);
            appStage.setY(event.getScreenY() - yOffset);
        });
        appStage.show();
        appStage.setOnHiding(event->
        {
            Profesori pr=cp.getProfesori();
            if(pr!=null)
            {
                dao.dodajProfesora(pr);
                listProfesori.setAll(dao.profesori());
                listProfesori.removeIf(profesor -> profesor.getId() == profesori.getId());
                //tableViewPredmeti.getItems().add(p1);
                tableViewProfesori.setItems(FXCollections.observableArrayList(listProfesori));
                System.out.println(cp.getProfesori().getNaziv());
            }
            buttonDodajProfesora.setDisable(false);
            buttonIzlazAdminPanel.setDisable(false);
            tableViewProfesori.setDisable(false);
            izvjestajButton.setDisable(false);
        });

    }
    public void izlaz(ActionEvent actionEvent)
    {
        Platform.exit();
        System.exit(0);
    }
    public void obrisiProfesora(ActionEvent actionEvent)
    {
        buttonObrisiProfesora.setDisable(true);
        buttonIzmijeni.setDisable(true);
        buttonDodajProfesora.setDisable(true);
        buttonIzlazAdminPanel.setDisable(true);
        izvjestajButton.setDisable(true);
        if(tableViewProfesori.getItems().isEmpty()) {
            buttonDodajProfesora.setDisable(false);
            return;
        }
        Profesori p=tableViewProfesori.getSelectionModel().getSelectedItem();
        if(p==null)
        {
            buttonObrisiProfesora.setDisable(false);
            buttonIzmijeni.setDisable(false);
            buttonDodajProfesora.setDisable(false);
            buttonIzlazAdminPanel.setDisable(false);
            izvjestajButton.setDisable(false);
            return;
        }
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje");
        alert.setHeaderText("Brisanje profesora: "+p.getNaziv());
        alert.setContentText("Da li ste sigurni da zelite obrisati predmet: "+p.getNaziv());
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            dao.obrisiProfesora(p);
            listProfesori.setAll(dao.profesori());
            listProfesori.removeIf(profesor -> profesor.getId() == profesori.getId());
            //tableViewPredmeti.getItems().add(p1);
            tableViewProfesori.setItems(FXCollections.observableArrayList(listProfesori));
            buttonObrisiProfesora.setDisable(false);
            buttonIzmijeni.setDisable(false);
            buttonDodajProfesora.setDisable(false);
            buttonIzlazAdminPanel.setDisable(false);
            izvjestajButton.setDisable(false);
        }
        else if(result.get()==ButtonType.CANCEL) {
            buttonDodajProfesora.setDisable(false);
            buttonIzlazAdminPanel.setDisable(false);
            buttonIzmijeni.setDisable(false);
            buttonObrisiProfesora.setDisable(false);
        }
        if(tableViewProfesori.getItems().isEmpty())
        {
            buttonObrisiProfesora.setDisable(true);
            buttonIzmijeni.setDisable(true);
            //buttonDodajProfesora.setDisable(true);
        }
    }
    public void izmijeniProfesora(ActionEvent actionEvent) throws IOException {
        if(tableViewProfesori.getItems().isEmpty())
            return;
        buttonObrisiProfesora.setDisable(true);
        buttonIzmijeni.setDisable(true);
        buttonDodajProfesora.setDisable(true);
        buttonIzlazAdminPanel.setDisable(true);
        tableViewProfesori.setDisable(true);
        izvjestajButton.setDisable(true);
        Profesori p=tableViewProfesori.getSelectionModel().getSelectedItem();
        if(p==null)
        {
            //buttonObrisiProfesora.setDisable(false);
            //buttonIzmijeni.setDisable(false);
            buttonDodajProfesora.setDisable(false);
            buttonIzlazAdminPanel.setDisable(false);
            tableViewProfesori.setDisable(false);
            return;
        }
        FXMLLoader loader=new FXMLLoader(getClass().getResource("izmijeniProfesora.fxml"));
        izmijeniProfesoraController cp=new izmijeniProfesoraController(p);
        loader.setController(cp);
        Stage stage=new Stage();
        Parent root=loader.load();
        stage.setScene(new Scene(root,290,370));
        stage.setResizable(false);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Profesori p=cp.getP();
                if(p!=null) {
                    dao.izmijeniProfesora(p);
                    listProfesori.setAll(dao.profesori());
                    listProfesori.removeIf(profesor -> profesor.getId() == profesori.getId());
                    //tableViewPredmeti.getItems().add(p1);
                    tableViewProfesori.setItems(FXCollections.observableArrayList(listProfesori));
                }
                buttonObrisiProfesora.setDisable(false);
                buttonIzmijeni.setDisable(false);
                buttonDodajProfesora.setDisable(false);
                buttonIzlazAdminPanel.setDisable(false);
                tableViewProfesori.setDisable(false);
                izvjestajButton.setDisable(false);
            }
        });
    }
    public void izvjestajAdmina(ActionEvent actionEvent) throws IOException {

            FXMLLoader loader=new FXMLLoader(getClass().getResource("adminIzvjestaj.fxml"));
            adminIzvjestajController cp=new adminIzvjestajController();
            loader.setController(cp);
            Stage stage=new Stage();
            Parent root=loader.load();
            buttonObrisiProfesora.setDisable(true);
            buttonIzmijeni.setDisable(true);
            buttonDodajProfesora.setDisable(true);
            buttonIzlazAdminPanel.setDisable(true);
            tableViewProfesori.setDisable(true);
            izvjestajButton.setDisable(true);
            stage.setScene(new Scene(root,1400,800));
            stage.setResizable(false);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    buttonObrisiProfesora.setDisable(false);
                    buttonIzmijeni.setDisable(false);
                    buttonDodajProfesora.setDisable(false);
                    buttonIzlazAdminPanel.setDisable(false);
                    tableViewProfesori.setDisable(false);
                    izvjestajButton.setDisable(false);
                }
            });
    }

    public void csvexport(ActionEvent actionEvent) throws IOException, SQLException {
        dao.izvestajTOCsv();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Obavijest");
        alert.setHeaderText("Uspjesno izvezeno u CSV");
        alert.showAndWait();
    }
}
//amadjetam