/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.lotto.initdb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import wbs.lotto.jpafeatures.NamedQueryLocal;
import wbs.lotto.persistence.ZiehungFacadeLocal;
import wbs.lotto.service.ZiehungAuswertenDataLocal_neu;

/**
 *
 * @author Master
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class CleanDatabase implements CleanDatabaseLocal {

    @Resource(lookup = "lotto_jndi")
    private DataSource ds;

    @EJB
    NamedQueryLocal namedQueryLocal;

    @EJB
    ZiehungFacadeLocal ziehungFacadeLocal;

    @EJB
    ZiehungAuswertenDataLocal_neu ziehungAuswertenData;

    @PersistenceContext(unitName = "jpa_ws_ejb-ejbPU")
    private EntityManager em;

    @Override
    public void cleanDatabase(String schema) {
        ResultSet rs;
        String tableName;
        Statement statementFKCheckOff;
        Statement statementFKCheckOn;
        Statement statement;
        String sql;
        try (Connection connection = ds.getConnection()) {
            DatabaseMetaData mt = connection.getMetaData();
            rs = mt.getTables(schema, null, null, null);
            statementFKCheckOff = connection.createStatement();
            statementFKCheckOff.execute("SET FOREIGN_KEY_CHECKS = 0;");
            while (rs.next()) {
                tableName = rs.getString("TABLE_NAME");
                if (!tableName.equals("fakenames")
                        && !tableName.equals("users")
                        && !tableName.equals("group")) {
                    statement = connection.createStatement();
                    sql = "TRUNCATE " + schema + "." + tableName;
                    statement.execute(sql);
                }
            }
            statementFKCheckOn = connection.createStatement();
            statementFKCheckOn.executeQuery("SET FOREIGN_KEY_CHECKS = 1;");

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public void resetZiehungsAuswertung(long ziehungId, boolean resetLottoscheinZiehung) {
        ResultSet rs;
        Statement stmt;
        String sql;
        try (Connection connection = ds.getConnection()) {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            if (resetLottoscheinZiehung) {

                stmt.execute("DELETE lz49 FROM ziehung AS z "
                        + "INNER JOIN lottoscheinziehung AS lz "
                        + "ON lz.ZiehungId = z.ZiehungId "
                        + "INNER JOIN lottoscheinziehung6aus49 AS lz49 "
                        + "ON lz49.LottoscheinZiehungId = lz.LottoscheinZiehungId "
                        + "WHERE z.ZiehungId = " + Long.toString(ziehungId));

                stmt.execute("UPDATE LottoscheinZiehung AS lz "
                        + "INNER JOIN ziehung AS z "
                        + "ON lz.ZiehungId = z.ZiehungId "
                        + "SET lz.GewinnklasseIdSpiel77 = NULL, "
                        + "lz.GewinnklasseIdSuper6 = NULL, "
                        + "lz.isAbgeschlossen = NULL "
                        + "WHERE z.ZiehungId = " + Long.toString(ziehungId));

                stmt.execute("UPDATE ziehung AS z "
                        + "SET z.EinsatzLotto = 0, "
                        + "z.EinsatzSpiel77 = 0, "
                        + "z.EinsatzSuper6 = 0, "
                        + "z.Status=5 "
                        + "WHERE z.ZiehungId = " + Long.toString(ziehungId));
            } else {
                stmt.execute("UPDATE ziehung AS z "
                        + "SET z.Status=6 "
                        + "WHERE z.ZiehungId = " + Long.toString(ziehungId));
            }
            stmt.execute("DELETE FROM GewinnklasseZiehungQuote "
                    + "WHERE ZiehungId = " + Long.toString(ziehungId));
            sql = "DELETE j FROM jackpot j "
                    + "WHERE j.ZiehungId = "
                    + "( "
                    + "SELECT z1.ZiehungId FROM ziehung z1 "
                    + "WHERE z1.Ziehungsdatum > "
                    + "( SELECT z2.Ziehungsdatum FROM ziehung z2 "
                    + "WHERE z2.ZiehungId = 14 ) "
                    + "LIMIT 1 "
                    + ")";
            stmt.execute(sql);
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");

            connection.setAutoCommit(true);

            em.getEntityManagerFactory().getCache().evictAll();

            if (resetLottoscheinZiehung) {
                ziehungAuswertenData.setAnzahlScheineBearbeitet(0);
            }

            for (int i = 0; i < 10; i++) {
                if (resetLottoscheinZiehung) {
                    ziehungAuswertenData.getGklLotto()[i] = 0;
                }
                ziehungAuswertenData.getQuoteGklLotto()[i] = 0;
            }

            for (int i = 0; i < 8; i++) {
                if (resetLottoscheinZiehung) {
                    ziehungAuswertenData.getGklSpiel77()[i] = 0;
                }
                ziehungAuswertenData.getQuoteGklSpiel77()[i] = 0;
            }

            for (int i = 0; i < 7; i++) {
                if (resetLottoscheinZiehung) {
                    ziehungAuswertenData.getGklSuper6()[i] = 0;
                }
                ziehungAuswertenData.getQuoteGklSuper6()[i] = 0;
            }
            ziehungAuswertenData.setZiehungId(ziehungId);
            System.out.println("Ziehungsstatus: " +  ziehungAuswertenData.getZiehung().getStatus());

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
