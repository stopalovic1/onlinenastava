package sample;


import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class izmijeniSadrzajController {
    Sadrzaj s;
    public TextArea sadrzajCasa;
    public TextField dan;
    public TextField datumCasa;
    public TextField datumObjave;
    public TextArea izostaliUcenici;
    public izmijeniSadrzajController(Sadrzaj s) {
        this.s = s;
    }
    public Sadrzaj getS() {
        return s;
    }
    public void initialize()
    {
        sadrzajCasa.setWrapText(true);
        izostaliUcenici.setWrapText(true);
        sadrzajCasa.setText(s.getSadrzajCasa());
        dan.setText(s.getDan());
        datumCasa.setText(s.getDatumCasa());
        datumObjave.setText(s.getDatumObjave());
        izostaliUcenici.setText(s.getIzostaliUcenici());
    }
    public void buttonIzmijeni(ActionEvent actionEvent)
    {
        s.setSadrzajCasa(sadrzajCasa.getText());
        s.setDan(dan.getText());
        //s.setDatumCasa(datumCasa.getText());
        s.setDatumObjave(datumObjave.getText());
        s.setIzostaliUcenici(izostaliUcenici.getText());
        Stage stage=(Stage) sadrzajCasa.getScene().getWindow();
        stage.close();
    }
    public void izlaz(ActionEvent actionEvent)
    {
        s=null;
        Stage stage=(Stage) sadrzajCasa.getScene().getWindow();
        stage.close();
    }
}
