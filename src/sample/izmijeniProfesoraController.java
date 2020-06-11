package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class izmijeniProfesoraController {

    Profesori p;
    public TextField imeIPrezimeText;
    public TextField userNameText;
    public TextField lozinkaText;
    public TextField brojSatiText;

    public Profesori getP() {
        return p;
    }

    public izmijeniProfesoraController(Profesori p) {
        this.p = p;
    }
    public void initialize() {
        imeIPrezimeText.setText(p.getNaziv());
        userNameText.setText(p.getUsername());
        lozinkaText.setText(p.getPassword());
        brojSatiText.setText(String.valueOf(p.getSatiUTjednu()));
    }
    public void izmijeni(ActionEvent actionEvent) {
        p.setNaziv(imeIPrezimeText.getText());
        p.setUsername(userNameText.getText());
        p.setPassword(lozinkaText.getText());
        p.setSatiUTjednu(brojSatiText.getText());
        Stage stage=(Stage) imeIPrezimeText.getScene().getWindow();
        stage.close();
    }
    public void izlaz(ActionEvent actionEvent) {
        p=null;
        Stage stage=(Stage) imeIPrezimeText.getScene().getWindow();
        stage.close();
    }

}
