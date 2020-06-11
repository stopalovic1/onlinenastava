package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class izmijeniPredmetController {
    Predmeti p;
    public TextField odjel;
    public TextField grupa;
    public TextField duzina;
    public TextField labelPredmet;
    public TextField brojSati;
    public izmijeniPredmetController(Predmeti p) {
        this.p = p;
    }

    public Predmeti getP() {
        return p;
    }

    public void initialize()
    {
        odjel.setText(p.getOdjel());
        grupa.setText(p.getGrupa());
        duzina.setText(p.getDuzina());
        labelPredmet.setText(p.getPredmet());
        brojSati.setText(String.valueOf(p.getBrojsati()));
    }
    public void buttonIzmijeni(ActionEvent actionEvent)
    {
        p.setOdjel(odjel.getText());
        p.setGrupa(grupa.getText());
        p.setDuzina(duzina.getText());
        p.setPredmet(labelPredmet.getText());
        p.setBrojsati(Integer.parseInt(brojSati.getText()));
        Stage stage=(Stage) odjel.getScene().getWindow();
        stage.close();
    }
    public void buttonIzlaz(ActionEvent actionEvent)
    {
        p=null;
        Stage stage=(Stage) odjel.getScene().getWindow();
        stage.close();
    }
}
