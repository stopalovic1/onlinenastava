package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Optional;

public class sadrzajFormaController {
    Sadrzaj s;
    Predmeti p;
    public Label labelaPredmet;
    private onDAO dao;
    public TableView<Sadrzaj> tableViewSadrzaj;
    public TableColumn colSadrzajId;
    public TableColumn colSadrzajSadrzajCasa;
    public TableColumn colSadrzajDan;
    public TableColumn colSadrzajDatumCasa;
    public TableColumn colSadrzajDatumObjave;
    public TableColumn colSadrzajIzostaliUcenici;
    private ObservableList<Sadrzaj> listSadrzaji;
    private double xOffset=0;
    private double yOffset=0;
    public Button buttonOpis;
    public Button buttonObrisi;
    public Button buttonIzmijeniOpis;
    public Button buttonIzlaz;
    public Stage stage;
    public Sadrzaj getS() {
        return s;
    }

    public Label getLabelaPredmet() {
        return labelaPredmet;
    }

    public void setLabelaPredmet(String s){
        this.labelaPredmet.setText(s);
    }

    public void setP(Predmeti p) {
        this.p = p;
    }

    public sadrzajFormaController(Predmeti p) {
        this.p = p;
    }

    public void setS(Sadrzaj s) {
        this.s = s;
    }

    public sadrzajFormaController()
    {
        dao=onDAO.getInstance();
        listSadrzaji= FXCollections.observableArrayList(dao.sadrzaji());
    }

    public void initialize() {
            listSadrzaji.removeIf(id -> id.predmet_id != s.getPredmet_id());
            tableViewSadrzaj.setItems(FXCollections.observableArrayList(listSadrzaji));
            colSadrzajId.setCellValueFactory(new PropertyValueFactory("id"));
            colSadrzajSadrzajCasa.setCellValueFactory(new PropertyValueFactory("sadrzajCasa"));
            colSadrzajDan.setCellValueFactory(new PropertyValueFactory("dan"));
            colSadrzajDatumCasa.setCellValueFactory(new PropertyValueFactory("datumCasa"));
            colSadrzajDatumObjave.setCellValueFactory(new PropertyValueFactory("datumObjave"));
            colSadrzajIzostaliUcenici.setCellValueFactory(new PropertyValueFactory("izostaliUcenici"));
            buttonObrisi.setDisable(true);
            buttonIzmijeniOpis.setDisable(true);
            tableViewSadrzaj.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(!tableViewSadrzaj.getItems().isEmpty()) {
                        buttonObrisi.setDisable(false);
                        buttonIzmijeniOpis.setDisable(false);
                    }
                }
            });
        {
            labelaPredmet.setText(p.getPredmet());
        }
    }
    public void dodajOpis(ActionEvent actionEvent)  {
        Stage stage=new Stage();
        Parent root=null;
        buttonOpis.setDisable(true);
        buttonObrisi.setDisable(true);
        buttonIzmijeniOpis.setDisable(true);
        buttonIzlaz.setDisable(true);
        tableViewSadrzaj.setDisable(true);
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dodajSadrzaj.fxml"));
            dodajSadrzajController cp=new dodajSadrzajController(s);
            cp.getS().setPredmet_id(s.getPredmet_id());
            loader.setController(cp);
            root=loader.load();
            stage.setScene(new Scene(root ,385,370));
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
                Sadrzaj p1 = cp.getS();
                if(p1!=null) {
                    dao.dodajSadrzaj(p1);
                    listSadrzaji.setAll(dao.sadrzaji());
                    listSadrzaji.removeIf(id -> id.predmet_id != s.getPredmet_id());
                    //tableViewSadrzaj.getItems().add(p1);
                    tableViewSadrzaj.setItems(FXCollections.observableArrayList(listSadrzaji));
                    System.out.println(cp.getS().getSadrzajCasa());
                }
                buttonOpis.setDisable(false);
                buttonIzlaz.setDisable(false);
                tableViewSadrzaj.setDisable(false);
                buttonObrisi.setDisable(false);
                buttonIzmijeniOpis.setDisable(false);
                if(tableViewSadrzaj.getItems().isEmpty())
                {
                    buttonObrisi.setDisable(true);
                    buttonIzmijeniOpis.setDisable(true);
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void obrisiOpis(ActionEvent actionEvent) {
        Sadrzaj sadrzaj=tableViewSadrzaj.getSelectionModel().getSelectedItem();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje");
        alert.setHeaderText("Brisanje sadrzaja za predmet: "+p.getPredmet());
        alert.setContentText("Da li ste sigurni da zelite obrisati sadrzaj ? ");
        Optional<ButtonType> buttonType=alert.showAndWait();
        if(buttonType.get()==ButtonType.OK && sadrzaj!=null) {
            dao.obrisiSadrzaj(sadrzaj);
            tableViewSadrzaj.getItems().remove(sadrzaj);
            if(tableViewSadrzaj.getItems().isEmpty()) buttonObrisi.setDisable(true);
        }

    }
    public void izmijeniOpis(ActionEvent actionEvent) throws IOException {
        buttonOpis.setDisable(true);
        buttonIzlaz.setDisable(true);
        tableViewSadrzaj.setDisable(true);
        buttonObrisi.setDisable(true);
        buttonIzmijeniOpis.setDisable(true);
        Sadrzaj s1=tableViewSadrzaj.getSelectionModel().getSelectedItem();
        if(s1==null)
        {
            buttonIzlaz.setDisable(false);
            tableViewSadrzaj.setDisable(false);
            buttonOpis.setDisable(false);
            return;
        }
        Stage stage=new Stage();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("izmijeniSadrzaj.fxml"));
        izmijeniSadrzajController cp=new izmijeniSadrzajController(s1);
        loader.setController(cp);
        Parent root=loader.load();
        stage.setScene(new Scene(root,380,370));
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
                Sadrzaj s2=cp.getS();
                if(s2!=null)
                {
                    dao.izmijeniSadrzaj(s2);
                    listSadrzaji.setAll(dao.sadrzaji());
                    listSadrzaji.removeIf(id -> id.predmet_id != s.getPredmet_id());
                    //tableViewSadrzaj.getItems().add(p1);
                    tableViewSadrzaj.setItems(FXCollections.observableArrayList(listSadrzaji));
                }
                buttonOpis.setDisable(false);
                buttonIzlaz.setDisable(false);
                tableViewSadrzaj.setDisable(false);
                buttonObrisi.setDisable(false);
                buttonIzmijeniOpis.setDisable(false);

            }
        });
    }
    public void izlaz(ActionEvent actionEvent) {
            Stage stage=(Stage) buttonOpis.getScene().getWindow();
            stage.close();
    }
}
