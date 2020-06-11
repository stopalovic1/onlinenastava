package sample;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class onDAO {
    private static onDAO instance;
    private Connection connection;
    private PreparedStatement ucitajProfesore, ucitajPredmete, ucitajSadrzaj, dodajProfesora, dodajPredmet, dodajSadrzaj, odrediIdPredmeta, odrediIdSadrzaja,
            obrisiSadrzaj, obrisiPredmet, odrediIdProfesora, obrisiProfesora, izmijeniPredmet, izmijeniProfesora, izmijeniSadrzaj, izvjestajZaAdmina, izvjestajZaProfesora,
            izvjestajCSVadmin;

    public static onDAO getInstance() {
        if (instance == null) instance = new onDAO();
        return instance;
    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private onDAO() {
        try {
            String veza = "jdbc:mysql://35.228.161.184:3306/baza?autoReconnect=true&useSSL=false";
            connection = DriverManager.getConnection(veza, "onlinenastava", "admin");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            ucitajProfesore = connection.prepareStatement("SELECT profesori.id, profesori.naziv, profesori.username, profesori.password, profesori.satiutjednu FROM profesori");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                ucitajProfesore = connection.prepareStatement("SELECT profesori.id, profesori.naziv, profesori.username, profesori.password, profesori.satiutjednu FROM profesori");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            ucitajPredmete = connection.prepareStatement("SELECT predmeti.id, predmeti.odjel, predmeti.grupa, predmeti.duzina, predmeti.predmet, predmeti.brojsati, predmeti.profesor_id FROM predmeti");
        } catch (SQLException exception) {
            //regenerisiBazu();
            try {
                ucitajPredmete = connection.prepareStatement("SELECT predmeti.id, predmeti.odjel, predmeti.grupa, predmeti.duzina, predmeti.predmet, predmeti.brojsati, predmeti.profesor_id FROM predmeti");
            } catch (SQLException exception1) {
                exception1.printStackTrace();
            }
        }
        try {
            ucitajSadrzaj = connection.prepareStatement("SELECT sadrzaj.id, sadrzaj.sadrzajcasa, sadrzaj.dan, sadrzaj.datumcasa, sadrzaj.datumobjave, sadrzaj.izostaliucenici, sadrzaj.predmet_id FROM sadrzaj");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            dodajProfesora = connection.prepareStatement("INSERT INTO profesori VALUES(?,?,?,?,?)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            dodajPredmet = connection.prepareStatement("INSERT INTO predmeti VALUES(?,?,?,?,?,?,?)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            dodajSadrzaj = connection.prepareStatement("INSERT INTO sadrzaj VALUES(?,?,?,?,?,?,?)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            odrediIdPredmeta = connection.prepareStatement("SELECT MAX(id)+1 from predmeti");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            odrediIdSadrzaja = connection.prepareStatement("SELECT MAX(id)+1 from sadrzaj");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            odrediIdProfesora = connection.prepareStatement("SELECT MAX(id)+1 from profesori");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            obrisiSadrzaj = connection.prepareStatement("DELETE FROM sadrzaj where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            obrisiPredmet = connection.prepareStatement("DELETE FROM predmeti where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            izmijeniPredmet = connection.prepareStatement("UPDATE predmeti SET odjel=?, grupa=?, duzina=?, predmet=?, brojsati=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            izmijeniProfesora = connection.prepareStatement("UPDATE profesori SET naziv=?, username=?, password=?, satiutjednu=? WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            izmijeniSadrzaj = connection.prepareStatement("UPDATE sadrzaj SET sadrzajcasa=?, dan=?, datumcasa=?, datumobjave=?, izostaliucenici=? WHERE id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            obrisiProfesora = connection.prepareStatement("DELETE FROM profesori where id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            izvjestajZaAdmina = connection.prepareStatement("SELECT p.naziv,pr.predmet,pr.odjel,s.datumcasa,s.datumobjave,s.sadrzajcasa,s.izostaliucenici FROM profesori p,predmeti pr,sadrzaj s WHERE p.id=pr.profesor_id AND pr.id=s.predmet_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            izvjestajZaProfesora = connection.prepareStatement("SELECT pr.predmet,pr.odjel,s.datumcasa,s.datumobjave,s.sadrzajcasa,s.izostaliucenici FROM profesori p,predmeti pr,sadrzaj s WHERE p.id=pr.profesor_id AND pr.id=s.predmet_id AND p.id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try
        {
            izvjestajCSVadmin=connection.prepareStatement("SELECT p.naziv AS Profesor,pr.predmet AS Predmet,pr.odjel AS Odjeljenje,s.datumcasa as 'Datum casa' ,s.datumobjave AS 'Datum objave',s.sadrzajcasa AS 'Sadrzaj casa' ,s.izostaliucenici AS 'Izostali ucenici' FROM profesori p,predmeti pr,sadrzaj s WHERE p.id=pr.profesor_id AND pr.id=s.predmet_id");
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = connection.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Profesori dajProfesora(String username, String password) {
        try {
            ResultSet rs = ucitajProfesore.executeQuery();
            //if (!rs.next()) return null;
            while (rs.next()) {
                if (rs.getString(3).equals(username) && password.equals(rs.getString(4))) {
                    System.out.println(rs.getString(3) + " " + rs.getString(4));
                    return new Profesori(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ArrayList<Predmeti> predmeti() {
        ArrayList<Predmeti> rezultat = new ArrayList();
        try {
            ResultSet rs = ucitajPredmete.executeQuery();
            while (rs.next()) {
                Predmeti predmet = new Predmeti(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
                rezultat.add(predmet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<Profesori> profesori() {
        ArrayList<Profesori> rezultat = new ArrayList();
        try {
            ResultSet rs = ucitajProfesore.executeQuery();
            while (rs.next()) {
                Profesori profesori = new Profesori(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                rezultat.add(profesori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public Sadrzaj dajSadrzaj(int predmet_id) {
        try {
            ResultSet rs = ucitajSadrzaj.executeQuery();
            while (rs.next()) {
                if (rs.getInt(7) == predmet_id)
                    return new Sadrzaj(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public ArrayList<Sadrzaj> sadrzaji() {
        ArrayList<Sadrzaj> rezultat = new ArrayList();
        try {
            ResultSet rs = ucitajSadrzaj.executeQuery();
            while (rs.next()) {
                //if(rs.getInt(7)==id){
                Sadrzaj sadrzaj = new Sadrzaj(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
                rezultat.add(sadrzaj);
                //System.out.println(rs.getString(2)+" "+rs.getString(3));
                //}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void dodajPredmet(Predmeti predmet) {
        try {
            ResultSet rs = odrediIdPredmeta.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            dodajPredmet.setInt(1, id);
            dodajPredmet.setString(2, predmet.getOdjel());
            dodajPredmet.setString(3, predmet.getGrupa());
            dodajPredmet.setString(4, predmet.getDuzina());
            dodajPredmet.setString(5, predmet.getPredmet());
            dodajPredmet.setInt(6, predmet.getBrojsati());
            dodajPredmet.setInt(7, predmet.getProfesor_id());
            dodajPredmet.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajSadrzaj(Sadrzaj sadrzaj) {
        try {
            ResultSet rs = odrediIdSadrzaja.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            dodajSadrzaj.setInt(1, id);
            dodajSadrzaj.setString(2, sadrzaj.getSadrzajCasa());
            dodajSadrzaj.setString(3, sadrzaj.getDan());
            dodajSadrzaj.setString(4, sadrzaj.getDatumCasa());
            dodajSadrzaj.setString(5, sadrzaj.getDatumObjave());
            dodajSadrzaj.setString(6, sadrzaj.getIzostaliUcenici());
            dodajSadrzaj.setInt(7, sadrzaj.getPredmet_id());
            dodajSadrzaj.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajProfesora(Profesori profesori) {
        try {
            ResultSet rs = odrediIdProfesora.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            dodajProfesora.setInt(1, id);
            dodajProfesora.setString(2, profesori.getNaziv());
            dodajProfesora.setString(3, profesori.getUsername());
            dodajProfesora.setString(4, profesori.getPassword());
            dodajProfesora.setString(5, profesori.getSatiUTjednu());
            dodajProfesora.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiSadrzaj(Sadrzaj sadrzaj) {
        try {
            obrisiSadrzaj.setInt(1, sadrzaj.getId());
            obrisiSadrzaj.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void obrisiPredmet(Predmeti predmet) {
        try {
            obrisiPredmet.setInt(1, predmet.getId());
            obrisiPredmet.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniPredmet(Predmeti predmeti) {
        try {
            izmijeniPredmet.setString(1, predmeti.getOdjel());
            izmijeniPredmet.setString(2, predmeti.getGrupa());
            izmijeniPredmet.setString(3, predmeti.getDuzina());
            izmijeniPredmet.setString(4, predmeti.getPredmet());
            izmijeniPredmet.setInt(5, predmeti.getBrojsati());
            izmijeniPredmet.setInt(6, predmeti.getId());
            izmijeniPredmet.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void izmijeniProfesora(Profesori profesori) {
        try {
            izmijeniProfesora.setString(1, profesori.getNaziv());
            izmijeniProfesora.setString(2, profesori.getUsername());
            izmijeniProfesora.setString(3, profesori.getPassword());
            izmijeniProfesora.setString(4, profesori.getSatiUTjednu());
            izmijeniProfesora.setInt(5, profesori.getId());
            izmijeniProfesora.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void izmijeniSadrzaj(Sadrzaj sadrzaj) {
        try {
            izmijeniSadrzaj.setString(1, sadrzaj.getSadrzajCasa());
            izmijeniSadrzaj.setString(2, sadrzaj.getDan());
            izmijeniSadrzaj.setString(3, sadrzaj.getDatumCasa());
            izmijeniSadrzaj.setString(4, sadrzaj.getDatumObjave());
            izmijeniSadrzaj.setString(5, sadrzaj.getIzostaliUcenici());
            izmijeniSadrzaj.setInt(6, sadrzaj.getId());
            izmijeniSadrzaj.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiProfesora(Profesori profesori) {
        try {
            obrisiProfesora.setInt(1, profesori.getId());
            obrisiProfesora.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<izvjestajAdmin> izvjestajAdmin() {
        ArrayList<izvjestajAdmin> rezultat = new ArrayList();
        try {
            ResultSet rs = izvjestajZaAdmina.executeQuery();
            while (rs.next()) {
                String naziv = rs.getString("p.naziv");
                String predmet = rs.getString("pr.predmet");
                String datumcasa = rs.getString("s.datumcasa");
                String datumobjave = rs.getString("s.datumobjave");
                String sadrzajcasa = rs.getString("s.sadrzajcasa");
                String iucenici = rs.getString("s.izostaliucenici");
                String odjeljenje = rs.getString("pr.odjel");
                rezultat.add(new izvjestajAdmin(naziv, predmet, datumcasa, datumobjave, sadrzajcasa, iucenici, odjeljenje));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rezultat;
    }

    public ArrayList<profesorIzvjestaj> profesorIzvjestaj(Profesori profesor) {
        ArrayList<profesorIzvjestaj> rezultat = new ArrayList();
        try {
            izvjestajZaProfesora.setInt(1, profesor.getId());
            ResultSet rs = izvjestajZaProfesora.executeQuery();
            while (rs.next()) {
                String predmet = rs.getString("pr.predmet");
                String datumcasa = rs.getString("s.datumcasa");
                String datumobjave = rs.getString("s.datumobjave");
                String sadrzajcasa = rs.getString("s.sadrzajcasa");
                String izostaliucenici = rs.getString("s.izostaliucenici");
                String odjeljenje = rs.getString("pr.odjel");
                //System.out.println(predmet+" "+datumcasa+" "+datumobjave+" "+sadrzajcasa+" "+izostaliucenici);
                rezultat.add(new profesorIzvjestaj(predmet, datumcasa, datumobjave, sadrzajcasa, izostaliucenici, odjeljenje));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rezultat;
    }

    public void izvestajTOCsv() throws IOException, SQLException {
        LocalDateTime date=LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm");
        Files.createDirectories(Paths.get("Izvjestaji"));
        File file1=new File("Izvjestaji/izvestaj-"+ formatter.format(date) +".csv");
        FileWriter izlaznifile1=new FileWriter(file1);
        CSVWriter writer1=new CSVWriter(izlaznifile1);
        ResultSet myResultSet1 = izvjestajCSVadmin.executeQuery();
        writer1.writeAll(myResultSet1, true);
        writer1.close();
    }
}
