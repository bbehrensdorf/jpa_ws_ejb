/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import wbs.lotto.domain.Lottoschein;

/**
 *
 * @author Master
 */
@Stateless
public class PopulateLottoschein implements PopulateLottoscheinLocal {

    @Resource(lookup = "lotto_jndi")
    private DataSource ds;
    Random rnd = new Random();

    @Override
    public void populateLottoschein(List<Lottoschein> lottoscheine) {
        String schema = "mydb";
        ResultSet rs;
        String tableName = "lottoschein";
        Statement statementFKCheckOff;
        Statement statementFKCheckOn;
        PreparedStatement statement;
        Statement statementTruncate;
        Statement statementAutoCommit;
        Statement statementCommit;
        String sql;
        sql = "INSERT INTO " + schema + "." + tableName;
        sql += " (Losnummer, KundeId, Laufzeit, isMittwoch, isSamstag, Tipps) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection connection = ds.getConnection()) {

            statement = connection.prepareStatement(sql);
            statementFKCheckOff = connection.createStatement();
            statementFKCheckOff.execute("SET FOREIGN_KEY_CHECKS = 0;");
            statementTruncate = connection.createStatement();
            statementTruncate.execute("TRUNCATE " + schema + "." + tableName);
            statementAutoCommit = connection.createStatement();
  //          statementAutoCommit.execute("SET AUTOCOMMIT=0;");
            statementCommit=connection.createStatement();
            int counter = 0;
            for (Lottoschein lottoschein : lottoscheine) {
                statement.setInt(1, lottoschein.getLosnummer());
                statement.setInt(2, 1);
                statement.setInt(3, 4);
                boolean isMittwoch = Math.random() > 0.5;
                boolean isSamstag = (Math.random() > 0.5) || !isMittwoch;
                boolean isSpiel77 = Math.random() > 0.5;
                boolean isSuper6 = (Math.random() > 0.5);
                statement.setBoolean(4, isMittwoch);
                statement.setBoolean(5, isSamstag);
                statement.setBytes(6, lottoschein.getTipps());
                statement.execute();
                counter++;
//                if (counter % 100 == 0) {
//                        statementCommit.execute("COMMIT;");
//                }
//                if (counter>100) {
//                    break;
//                }
            }
            
            Statement stmt = connection.createStatement();
            stmt.execute("TRUNCATE mydb.lottoscheinziehung;");
            sql= "INSERT INTO mydb.lottoscheinziehung (ZiehungId, LottoscheinId, ZiehungNr) ";
            sql += "(SELECT 14, LottoscheinId, 1 FROM mydb.lottoschein where mydb.lottoschein.isMittwoch = 1);";
            System.out.println(sql);
            stmt.execute(sql);

            sql= "INSERT INTO mydb.lottoscheinziehung (ZiehungId, LottoscheinId, ZiehungNr) ";
            sql += "(SELECT 31, LottoscheinId, 2 FROM mydb.lottoschein where mydb.lottoschein.isSamstag = 1);";
            System.out.println(sql);
            stmt.execute(sql);


            sql= "INSERT INTO mydb.lottoscheinziehung (ZiehungId, LottoscheinId, ZiehungNr) ";
            sql += "(SELECT 17, LottoscheinId, 3 FROM mydb.lottoschein where mydb.lottoschein.isMittwoch = 1);";
            System.out.println(sql);
            stmt.execute(sql);

            sql= "INSERT INTO mydb.lottoscheinziehung (ZiehungId, LottoscheinId, ZiehungNr) ";
            sql += "(SELECT 18, LottoscheinId, 4 FROM mydb.lottoschein where mydb.lottoschein.isSamstag = 1);";
            System.out.println(sql);
            stmt.execute(sql);

            statementFKCheckOn = connection.createStatement();
            statementFKCheckOn.execute("SET FOREIGN_KEY_CHECKS = 1;");
            
            //statementAutoCommit.execute("SET AUTOCOMMIT=1;");

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

}
