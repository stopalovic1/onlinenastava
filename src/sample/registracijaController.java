package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class registracijaController {
    Profesori profesori;
    public TextField imeIPrezimeText;
    public TextField userNameText;
    public TextField lozinkaText;
    public TextField brojSatiText;
    public Profesori getProfesori() {
        return profesori;
    }

    public void potvrdi(ActionEvent actionEvent) {
        if(profesori==null) profesori=new Profesori();
        profesori.setNaziv(imeIPrezimeText.getText());
        profesori.setUsername(userNameText.getText());
        profesori.setPassword(lozinkaText.getText());
        profesori.setSatiUTjednu(brojSatiText.getText());
        Stage stage=(Stage) imeIPrezimeText.getScene().getWindow();
        stage.close();
    }
    public void izlaz(ActionEvent actionEvent)
    {
        profesori=null;
        Stage stage=(Stage) imeIPrezimeText.getScene().getWindow();
        stage.close();
    }
}
