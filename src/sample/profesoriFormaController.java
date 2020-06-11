package sample;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

public class profesoriFormaController {

    public Profesori profesor;
    public Label naslov;
    public TableView<Predmeti> tableViewPredmeti;
    public TableColumn colPredmetId;
    public TableColumn colPredmetOdjel;
    public TableColumn colPredmetGrupa;
    public TableColumn colPredmetDuzina;
    public TableColumn colPredmetPredmet;
    public TableColumn colPredmetBrojSati;
    private ObservableList<Predmeti> listPredmeti;
    private onDAO dao;
    private double xOffset=0;
    private double yOffset=0;
    public Button dodajPr;
    public Button obrisiPr;
    public Button izlazIzForme;
    public Button buttonIzmijeniPredmet;
    public Button izvjestajPbutton;
    public profesoriFormaController(Profesori profesor) {
        this.profesor = profesor;
    }

    public profesoriFormaController() {
        dao=onDAO.getInstance();
        //lista=dao.predmeti();
        listPredmeti=FXCollections.observableArrayList(dao.predmeti());
    }
    public void setProfesor(Profesori profesor) {
        this.profesor = profesor;
    }

    public void initialize() {
        listPredmeti.removeIf(id->id.profesor_id!=profesor.getId());
        //listPredmeti=FXCollections.observableArrayList(lista);
        tableViewPredmeti.setItems(FXCollections.observableArrayList(listPredmeti));
        colPredmetId.setCellValueFactory(new PropertyValueFactory("id"));
        colPredmetOdjel.setCellValueFactory(new PropertyValueFactory("odjel"));
        colPredmetGrupa.setCellValueFactory(new PropertyValueFactory("grupa"));
        colPredmetDuzina.setCellValueFactory(new PropertyValueFactory("duzina"));
        colPredmetPredmet.setCellValueFactory(new PropertyValueFactory("predmet"));
        colPredmetBrojSati.setCellValueFactory(new PropertyValueFactory("brojsati"));
        obrisiPr.setDisable(true);
        buttonIzmijeniPredmet.setDisable(true);
        tableViewPredmeti.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent ) {
                if(!tableViewPredmeti.getItems().isEmpty())
                {
                    obrisiPr.setDisable(false);
                    buttonIzmijeniPredmet.setDisable(false);
                }
                if(mouseEvent.getClickCount()==2)
                {
                    try {
                        if (tableViewPredmeti.getItems().isEmpty()) return;
                        //if(Platform.)
                        obrisiPr.setDisable(true);
                        dodajPr.setDisable(true);
                        izlazIzForme.setDisable(true);
                        buttonIzmijeniPredmet.setDisable(true);
                        tableViewPredmeti.setDisable(true);
                        Predmeti p = tableViewPredmeti.getSelectionModel().getSelectedItem();
                        //if(p==null) return;
                        Stage stage = new Stage();
                        Parent root;
                        Sadrzaj s = null;
                        int id = tableViewPredmeti.getSelectionModel().getSelectedItem().id;
                        //String pr=tableViewPredmeti.getSelectionModel().getSelectedItem().predmet;

                        System.out.println(id);
                        s = new Sadrzaj(id);
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("sadrzajForma.fxml"));
                            sadrzajFormaController cp = new sadrzajFormaController();
                            cp.setS(s);
                            cp.setP(p);
                            loader.setController(cp);
                            root = loader.load();
                            //stage.setTitle("Grad");
                            stage.setScene(new Scene(root, 750, 400));
                            stage.setTitle("Sadrzaj");
                            stage.setResizable(false);
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
                                    obrisiPr.setDisable(false);
                                    dodajPr.setDisable(false);
                                    izlazIzForme.setDisable(false);
                                    buttonIzmijeniPredmet.setDisable(false);
                                    tableViewPredmeti.setDisable(false);
                                    izvjestajPbutton.setDisable(false);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }catch (NullPointerException e)
                    {
                        obrisiPr.setDisable(false);
                        dodajPr.setDisable(false);
                        izlazIzForme.setDisable(false);
                        buttonIzmijeniPredmet.setDisable(false);
                        tableViewPredmeti.setDisable(false);
                        izvjestajPbutton.setDisable(false);
                    }
                }
            }
        });
        if(profesor!=null)
        {
            naslov.setText(profesor.getNaziv());
            //System.out.println(listPredmeti.size());
        }
        else
        {
            //naslov.setText("hmmmmmmm");
        }

    }
    public void dodajPredmet(ActionEvent actionEvent) {
        Stage stage=new Stage();
        Parent root=null;
        Predmeti p=null;
        dodajPr.setDisable(true);
        obrisiPr.setDisable(true);
        izlazIzForme.setDisable(true);
        tableViewPredmeti.setDisable(true);
        buttonIzmijeniPredmet.setDisable(true);
        izvjestajPbutton.setDisable(true);
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dodajPredmet.fxml"));
            dodajPredmetController cp=new dodajPredmetController();
            p=new Predmeti(profesor.getId());
            cp.setPredmet(p);
            loader.setController(cp);
            root=loader.load();
            stage.setScene(new Scene(root ,295,385));
            stage.setResizable(false);
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
            stage.setOnHiding( event -> {
                Predmeti p1 = cp.getPredmet();
                if(p1!=null) {
                    dao.dodajPredmet(p1);
                    listPredmeti.setAll(dao.predmeti());
                    listPredmeti.removeIf(id -> id.profesor_id != profesor.getId());
                    //tableViewPredmeti.getItems().add(p1);
                    tableViewPredmeti.setItems(FXCollections.observableArrayList(listPredmeti));
                    System.out.println(cp.getPredmet().getPredmet());
                }
                dodajPr.setDisable(false);
               //if(!tableViewPredmeti.getItems().isEmpty()) obrisiPr.setDisable(false);
                izlazIzForme.setDisable(false);
                tableViewPredmeti.setDisable(false);
                //buttonIzmijeniPredmet.setDisable(false);
                izvjestajPbutton.setDisable(false);

            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void izlaz(ActionEvent actionEvent)
    {
       Stage stage=(Stage) dodajPr.getScene().getWindow();
       stage.close();
    }
    public void obrisiPredmet(ActionEvent actionEvent)
    {
        Predmeti predmet=tableViewPredmeti.getSelectionModel().getSelectedItem();
        if(predmet==null) return;
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje");
        alert.setHeaderText("Brisanje predmeta: "+predmet.getPredmet());
        alert.setContentText("Da li ste sigurni da zelite obrisati predmet: "+predmet.getPredmet());
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            dao.obrisiPredmet(predmet);
            tableViewPredmeti.getItems().remove(predmet);
            if(tableViewPredmeti.getItems().isEmpty()) {
                obrisiPr.setDisable(true);
                buttonIzmijeniPredmet.setDisable(true);
            }
        }

    }
    public void izmijeniPredmet(ActionEvent actionEvent) throws IOException {
        dodajPr.setDisable(true);
        obrisiPr.setDisable(true);
        izlazIzForme.setDisable(true);
        tableViewPredmeti.setDisable(true);
        buttonIzmijeniPredmet.setDisable(true);
        izvjestajPbutton.setDisable(true);
        Predmeti p=tableViewPredmeti.getSelectionModel().getSelectedItem();
        if(p==null)
        {
            dodajPr.setDisable(false);
            izlazIzForme.setDisable(false);
            tableViewPredmeti.setDisable(false);
            izvjestajPbutton.setDisable(false);
            return;
        }
        if(tableViewPredmeti.getItems().isEmpty()) return;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("izmijeniPredmet.fxml"));
        izmijeniPredmetController cp=new izmijeniPredmetController(p);
        loader.setController(cp);
        Parent root=loader.load();
        Stage stage=new Stage();
        stage.setScene(new Scene(root,295,385));
        stage.setResizable(false);
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
                Predmeti p1=cp.getP();
                if(p1!=null)
                {
                    dao.izmijeniPredmet(p1);
                    listPredmeti.setAll(dao.predmeti());
                    listPredmeti.removeIf(id -> id.profesor_id != profesor.getId());
                    //tableViewPredmeti.getItems().add(p1);
                    tableViewPredmeti.setItems(FXCollections.observableArrayList(listPredmeti));
                }
                dodajPr.setDisable(false);
                obrisiPr.setDisable(false);
                izlazIzForme.setDisable(false);
                tableViewPredmeti.setDisable(false);
                buttonIzmijeniPredmet.setDisable(false);
                izvjestajPbutton.setDisable(false);
            }
        });
    }
    public void izvjestajProfesora(ActionEvent actionEvent) throws IOException {
        dodajPr.setDisable(true);
        obrisiPr.setDisable(true);
        izlazIzForme.setDisable(true);
        tableViewPredmeti.setDisable(true);
        buttonIzmijeniPredmet.setDisable(true);
        izvjestajPbutton.setDisable(true);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("profesorIzvjestaj.fxml"));
        profesorIzvjestajController cp=new profesorIzvjestajController(profesor);
        loader.setController(cp);
        Stage stage=new Stage();
        Parent root=loader.load();
        stage.setScene(new Scene(root,1200,800));
        stage.setResizable(false);
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
                dodajPr.setDisable(false);
                obrisiPr.setDisable(false);
                izlazIzForme.setDisable(false);
                tableViewPredmeti.setDisable(false);
                buttonIzmijeniPredmet.setDisable(false);
                izvjestajPbutton.setDisable(false);
            }
        });
    }

}
