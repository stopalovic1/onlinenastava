package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class dodajSadrzajController {



    Sadrzaj s;
    public TextArea sadrzajCasa;
    public ChoiceBox<String> danChoiceBox;
    public DatePicker datumCasaPicker;
    public DatePicker datumObjavePicker;
    public TextArea izostaliUcenici;
    //public TextField predmetid;
    public Sadrzaj getS() {
        return s;
    }

    public dodajSadrzajController(Sadrzaj s) {
        this.s = s;
    }
    public void initialize() {
        ObservableList<String> lista = FXCollections.observableArrayList("Ponedjeljak", "Utorak", "Srijeda", "Cetvrtak", "Petak", "Subota", "Nedjelja");
        danChoiceBox.setItems(lista);
        sadrzajCasa.setWrapText(true);
        izostaliUcenici.setWrapText(true);
        datumObjavePicker.setEditable(false);
        datumCasaPicker.setEditable(false);
    }

    public void buttonDodaj(ActionEvent actionEvent)
    {
        //s.setId(Integer.parseInt(id.getText()));
        s.setDan(danChoiceBox.getSelectionModel().getSelectedItem().toString());
        String datumCasa=datumCasaPicker.getValue().getDayOfMonth()+". "+datumCasaPicker.getValue().getMonthValue()+". "+datumCasaPicker.getValue().getYear()+".";
        String datumObjave=datumObjavePicker.getValue().getDayOfMonth()+". "+datumObjavePicker.getValue().getMonthValue()+". "+datumObjavePicker.getValue().getYear()+".";
        s.setDatumCasa(datumCasa);
        s.setDatumObjave(datumObjave);
        s.setSadrzajCasa(sadrzajCasa.getText());
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
