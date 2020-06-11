package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class dodajPredmetController {

    Predmeti predmet;
    public TextField odjel;
    public TextField grupa;
    public TextField duzina;
    public TextField labelPredmet;
    public TextField brojSati;

    public dodajPredmetController(Predmeti predmet) {
        this.predmet = predmet;
    }
    public void setPredmet(Predmeti predmet) {
        this.predmet = predmet;
    }
    public Predmeti getPredmet() {
        return predmet;
    }
    public dodajPredmetController() {
    }
    public void initialize()
    {

    }
    public void buttonOk(ActionEvent actionEvent)
    {
        //predmet.setId(Integer.parseInt(id.getText()));
        predmet.setBrojsati(Integer.parseInt(brojSati.getText()));
        predmet.setDuzina(duzina.getText());
        predmet.setGrupa(grupa.getText());
        predmet.setPredmet(labelPredmet.getText());
        predmet.setOdjel(odjel.getText());
        Stage stage=(Stage) odjel.getScene().getWindow();
        stage.close();
    }
    public void izlaz(ActionEvent actionEvent)
    {
        Stage stage=(Stage) odjel.getScene().getWindow();
        predmet=null;
        stage.close();
    }
}
