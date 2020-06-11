package sample;

public class Profesori {
    private int id;
    private String naziv;
    private String username;
    private String password;
    private String satiUTjednu;

    public Profesori(int id, String naziv, String username, String password, String satiUTjednu) {
        this.id = id;
        this.naziv = naziv;
        this.username = username;
        this.password = password;
        this.satiUTjednu = satiUTjednu;
    }

    public Profesori(Profesori dajProfesora) {
        this.id = dajProfesora.id;
        this.naziv = dajProfesora.naziv;
        this.username = dajProfesora.username;
        this.password = dajProfesora.password;
        this.satiUTjednu = dajProfesora.satiUTjednu;
    }

    public Profesori() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSatiUTjednu() {
        return satiUTjednu;
    }

    public void setSatiUTjednu(String satiUTjednu) {
        this.satiUTjednu = satiUTjednu;
    }

}
