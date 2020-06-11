package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {


    public TextField username;
    public TextField password;
    public Label a;
    private onDAO dao;
    private double xOffset=0;
    private double yOffset=0;

    public LoginController() {
        dao=onDAO.getInstance();
    }

    public void initialize(){

    }
    public void ulazNaFormuProfesora(ActionEvent actionEvent) throws IOException {
        String u=username.getText();
        String p=password.getText();
        if (u.isEmpty() || p.isEmpty())
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            //alert.setContentText("Nijedno polje ne smije biti prazno");
            alert.setHeaderText("Greska: Nijedno polje ne smije biti prazno!");
            //alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait();
            return;
        }
        if(u.equals("admin") && p.equals("adminadmin")) {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("adminPanel.fxml"));
                    adminPanelController cp=new adminPanelController(dao.dajProfesora(u,p));
                    loader.setController(cp);
                    Parent root=loader.load();
                    Scene scene=new Scene(root,750,400);
                    Stage stage=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
        }
        else {
            Profesori pr = new Profesori(dao.dajProfesora(u, p));
            if (pr != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("profesorForma.fxml"));
                profesoriFormaController cp = new profesoriFormaController();
                cp.setProfesor(pr);
                loader.setController(cp);
                Parent root = loader.load();
                Scene scene = new Scene(root, 750, 400);
                Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                appStage.setScene(scene);
                appStage.setTitle("Profesori");
                root.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    appStage.setX(event.getScreenX() - xOffset);
                    appStage.setY(event.getScreenY() - yOffset);
                });
                appStage.show();
            } else {
                a.setText(pr.getNaziv());
            }
        }
    }
    public void registracija(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registracijaForma.fxml"));
        registracijaController cp = new registracijaController();
        //cp.setProfesor(pr);
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
            Profesori profesori=cp.getProfesori();
            if(profesori!=null)
            {
                dao.dodajProfesora(profesori);
            }

        });
    }

    public void Izlaz(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
