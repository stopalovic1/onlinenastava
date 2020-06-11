package sample;

public class izvjestajAdmin {
    private String naziv;
    private String predmet;
    private String datumcasa;
    private String datumobjave;
    private String sadrzajcasa;
    private String izostaliucenici;
    private String odjeljenje;
    public izvjestajAdmin(String naziv, String predmet, String datumcasa, String datumobjave, String sadrzajcasa,String izostaliucenici,String odjeljenje) {
        this.naziv = naziv;
        this.predmet = predmet;
        this.datumcasa = datumcasa;
        this.datumobjave = datumobjave;
        this.sadrzajcasa = sadrzajcasa;
        this.izostaliucenici=izostaliucenici;
        this.odjeljenje=odjeljenje;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public void setDatumcasa(String datumcasa) {
        this.datumcasa = datumcasa;
    }

    public void setDatumobjave(String datumobjave) {
        this.datumobjave = datumobjave;
    }

    public void setSadrzajcasa(String sadrzajcasa) {
        this.sadrzajcasa = sadrzajcasa;
    }

    public void setIzostaliucenici(String izostaliucenici) {
        this.izostaliucenici = izostaliucenici;
    }

    public void setOdjeljenje(String odjeljenje) {
        this.odjeljenje = odjeljenje;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getPredmet() {
        return predmet;
    }

    public String getDatumcasa() {
        return datumcasa;
    }

    public String getDatumobjave() {
        return datumobjave;
    }

    public String getSadrzajcasa() {
        return sadrzajcasa;
    }

    public String getIzostaliucenici() {
        return izostaliucenici;
    }

    public String getOdjeljenje() {
        return odjeljenje;
    }
}
