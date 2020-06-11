package sample;

public class Predmeti {
    int id;
    String odjel;
    String  grupa;
    String  duzina;
    String  predmet;
    int brojsati;
    int profesor_id;

    public Predmeti(int id, String odjel, String grupa, String duzina, String predmet, int brojsati, int profesor_id) {
        this.id = id;
        this.odjel = odjel;
        this.grupa = grupa;
        this.duzina = duzina;
        this.predmet = predmet;
        this.brojsati = brojsati;
        this.profesor_id = profesor_id;
    }

    public Predmeti(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    public Predmeti() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOdjel() {
        return odjel;
    }

    public void setOdjel(String odjel) {
        this.odjel = odjel;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getDuzina() {
        return duzina;
    }

    public void setDuzina(String duzina) {
        this.duzina = duzina;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public int getBrojsati() {
        return brojsati;
    }

    public void setBrojsati(int brojsati) {
        this.brojsati = brojsati;
    }

    public int getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }
}
