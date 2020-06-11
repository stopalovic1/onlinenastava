package sample;

public class profesorIzvjestaj {

    private String predmet;
    private String datumcasa;
    private String datumobjave;
    private String sadrzajcasa;
    private String izostaliucenici;
    private String odjeljenje;
    public profesorIzvjestaj(String predmet, String datumcasa, String datumobjave, String sadrzajcasa, String izostaliucenici,String odjeljenje) {
        this.predmet = predmet;
        this.datumcasa = datumcasa;
        this.datumobjave = datumobjave;
        this.sadrzajcasa = sadrzajcasa;
        this.izostaliucenici = izostaliucenici;
        this.odjeljenje=odjeljenje;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getDatumcasa() {
        return datumcasa;
    }

    public void setDatumcasa(String datumcasa) {
        this.datumcasa = datumcasa;
    }

    public String getDatumobjave() {
        return datumobjave;
    }

    public void setDatumobjave(String datumobjave) {
        this.datumobjave = datumobjave;
    }

    public String getSadrzajcasa() {
        return sadrzajcasa;
    }

    public void setSadrzajcasa(String sadrzajcasa) {
        this.sadrzajcasa = sadrzajcasa;
    }

    public String getIzostaliucenici() {
        return izostaliucenici;
    }

    public void setIzostaliucenici(String izostaliucenici) {
        this.izostaliucenici = izostaliucenici;
    }

    public String getOdjeljenje() {
        return odjeljenje;
    }

    public void setOdjeljenje(String odjeljenje) {
        this.odjeljenje = odjeljenje;
    }
}
