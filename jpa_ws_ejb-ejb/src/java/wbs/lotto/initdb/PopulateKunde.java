/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import wbs.lotto.domain.Adresse;
import wbs.lotto.domain.Bankverbindung;
import wbs.lotto.domain.Kunde;
import wbs.lotto.persistence.KundeFacadeLocal;

// legt in der Methode populateKunde() 10 Kunden an und für jeden dieser Kunden
// jeweils eine Adresse und eine Bankverbindung
// aufgerufen von PupulateDatabase (populateDatabase())
// Beachten:
// Cascade-Optionen
// Sind Mengenwertige Felder initialisiert?
// Bidirektionale Mappings
@Stateless
public class PopulateKunde implements PopulateKundeLocal {

    @Resource(lookup = "lotto_jndi")
    private DataSource ds;

    @EJB
    private KundeFacadeLocal kundeFacadeLocal;

    @Override
    public void populateKunde() {
//        for (int i = 1; i <= 10; i++) {
//            populateSingleKunde(i);
//        }
        ResultSet rs;
        String tableName;
        Statement statement;
        String sql;
        try (Connection connection = ds.getConnection()) {
            statement = connection.createStatement();
            sql = "SELECT * FROM mydb.fakenames";
            rs = statement.executeQuery(sql);
            int i=0;
            while (rs.next()) {
                populateSingleKunde(rs, i);
                i++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PopulateKunde.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void populateSingleKunde(ResultSet rs, int i) throws SQLException {
        Kunde kunde = new Kunde();
        Date date = new Date();

        kunde.setName(rs.getString("Name"));
        kunde.setVorname(rs.getString("Vorname"));
        kunde.setEmail(rs.getString("Email"));
        kunde.setGuthaben(BigInteger.TEN);
        kunde.setGesperrt(null);
        kunde.setIsAnnahmestelle(Boolean.FALSE);
        kunde.setCreated(date);
        kunde.setLastModified(date);
        kunde.setVersion(0);

        List<Adresse> adrList = new ArrayList<>();
        kunde.setAdresseList(adrList);

        Adresse adresse = new Adresse();
        adresse.setAdresseNr(adrList.size());
        String str=rs.getString("Strasse");
        
        int hNrTrenner=str.lastIndexOf(' ');
        String hnr=str.substring(hNrTrenner+1);
        str=str.substring(0, hNrTrenner);
        
        adresse.setStrasse(str);
        adresse.setHausnummer(hnr);
        adresse.setOrt(rs.getString("Ort"));
        adresse.setPlz(rs.getString("PLZ"));
        adresse.setLand(rs.getString("Bundesland"));
        adresse.setKundeId(kunde);

        adresse.setCreated(date);
        adresse.setLastModified(date);
        adresse.setVersion(0);
        adrList.add(adresse);
        
        List<Bankverbindung> bankverbindungList = new ArrayList<>();
        kunde.setBankverbindungList(bankverbindungList);
        
        Bankverbindung bankverbindung = new Bankverbindung();
        bankverbindung.setBic("BIC_" + i);
        bankverbindung.setIban("IBAN" + i);
        bankverbindung.setName("Bankname" + i);
        bankverbindung.setOrt(rs.getString("Ort"));
        bankverbindung.setBankverbindungNr(bankverbindungList.size());
        bankverbindung.setKundeId(kunde);
        bankverbindung.setCreated(date);
        bankverbindung.setLastModified(date);
        bankverbindung.setVersion(0);
        bankverbindungList.add(bankverbindung);

        bankverbindung = new Bankverbindung();
        bankverbindung.setBic("BIC_" + i + 1);
        bankverbindung.setIban("IBAN" + i + 1);
        bankverbindung.setName("Bankname" + i + 1);
        bankverbindung.setOrt(rs.getString("Ort"));
        bankverbindung.setBankverbindungNr(bankverbindungList.size());
        bankverbindung.setKundeId(kunde);
        bankverbindung.setCreated(date);
        bankverbindung.setLastModified(date);
        bankverbindung.setVersion(0);
        bankverbindungList.add(bankverbindung);
        kundeFacadeLocal.create(kunde);

        
        
    }

    public void populateSingleKunde0(int i) {

        // Kundenobjekt anlegen und füllen
        // Adressobjekt anlegen und füllen
        // Adressobjekt der Adressliste beim Kunden hinzufügen
        // Kundenobjekt im Adressobjekt eintragen
        // Bankverbindungsobjekt anlegen und füllen
        // Bankverbindungsobjekt der Bankverbindungsliste beim Kunden hinzufügen
        // Kundenobjekt im Bankverbindungsobjekt eintragen
        // persistieren
        Kunde kunde = new Kunde();
        Date date = new Date();

        kunde.setName("Name_" + i);
        kunde.setVorname("Vorname_" + i);
        kunde.setEmail("user" + i + "@mail.com");
        kunde.setGuthaben(BigInteger.TEN);
        kunde.setGesperrt(null);
        kunde.setIsAnnahmestelle(Boolean.FALSE);
        kunde.setCreated(date);
        kunde.setLastModified(date);
        kunde.setVersion(0);

        List<Adresse> adrList = new ArrayList<>();
        kunde.setAdresseList(adrList);

        Adresse adresse = new Adresse();
        adresse.setAdresseNr(adrList.size());
        adresse.setStrasse("Straße" + i);
        adresse.setHausnummer("30");
        adresse.setOrt("Ort_" + i);
        adresse.setPlz(String.format("301%02d", i % 10));
        adresse.setLand("LAND");
        adresse.setKundeId(kunde);

        adresse.setCreated(date);
        adresse.setLastModified(date);
        adresse.setVersion(0);
        adrList.add(adresse);

        List<Bankverbindung> bankverbindungList = new ArrayList<>();
        kunde.setBankverbindungList(bankverbindungList);

        Bankverbindung bankverbindung = new Bankverbindung();
        bankverbindung.setBic("BIC_" + i);
        bankverbindung.setIban("IBAN" + i);
        bankverbindung.setName("Bankname" + i);
        bankverbindung.setOrt("Ort_" + i);
        bankverbindung.setBankverbindungNr(bankverbindungList.size());
        bankverbindung.setKundeId(kunde);
        bankverbindung.setCreated(date);
        bankverbindung.setLastModified(date);
        bankverbindung.setVersion(0);
        bankverbindungList.add(bankverbindung);

        bankverbindung = new Bankverbindung();
        bankverbindung.setBic("BIC_" + i + 1);
        bankverbindung.setIban("IBAN" + i + 1);
        bankverbindung.setName("Bankname" + i + 1);
        bankverbindung.setOrt("Ort_" + i + 1);
        bankverbindung.setBankverbindungNr(bankverbindungList.size());
        bankverbindung.setKundeId(kunde);
        bankverbindung.setCreated(date);
        bankverbindung.setLastModified(date);
        bankverbindung.setVersion(0);
        bankverbindungList.add(bankverbindung);
        kundeFacadeLocal.create(kunde);

    }

}
