package sample;

public class Sadrzaj {
    int id;
    String sadrzajCasa;
    String dan;
    String datumCasa;
    String datumObjave;
    String izostaliUcenici;
    int predmet_id;

    public Sadrzaj() {
    }

    public Sadrzaj(int id, String sadrzajCasa, String dan, String datumCasa, String datumObjave, String izostaliUcenici, int predmet_id) {
        this.id = id;
        this.sadrzajCasa = sadrzajCasa;
        this.dan = dan;
        this.datumCasa = datumCasa;
        this.datumObjave = datumObjave;
        this.izostaliUcenici = izostaliUcenici;
        this.predmet_id = predmet_id;
    }
    public Sadrzaj(Sadrzaj sadrzaj)
    {
        this.id = sadrzaj.id;
        this.sadrzajCasa = sadrzaj.sadrzajCasa;
        this.dan = sadrzaj.dan;
        this.datumCasa = sadrzaj.datumCasa;
        this.datumObjave = sadrzaj.datumObjave;
        this.izostaliUcenici = sadrzaj.izostaliUcenici;
        this.predmet_id = sadrzaj.predmet_id;
    }

    public String getSadrzajCasa() {
        return sadrzajCasa;
    }

    public void setSadrzajCasa(String sadrzajCasa) {
        this.sadrzajCasa = sadrzajCasa;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getDatumCasa() {
        return datumCasa;
    }

    public void setDatumCasa(String datumCasa) {
        this.datumCasa = datumCasa;
    }

    public String getDatumObjave() {
        return datumObjave;
    }

    public void setDatumObjave(String datumObjave) {
        this.datumObjave = datumObjave;
    }

    public String getIzostaliUcenici() {
        return izostaliUcenici;
    }

    public void setIzostaliUcenici(String izostaliUcenici) {
        this.izostaliUcenici = izostaliUcenici;
    }

    public int getPredmet_id() {
        return predmet_id;
    }

    public void setPredmet_id(int predmet_id) {
        this.predmet_id = predmet_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sadrzaj(int predmet_id) {
        this.predmet_id = predmet_id;
    }
}
