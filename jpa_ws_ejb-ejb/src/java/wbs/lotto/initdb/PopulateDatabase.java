package wbs.lotto.initdb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PopulateDatabase implements PopulateDatabaseLocal {

    @EJB
    private PopulateGebuehrLocal populateGebuehrLocal;
    @EJB
    private PopulateKundeLocal populateKundeLocal;
    @EJB
    private PopulateZiehungLocal populateZiehungLocal;
    @EJB
    private CleanDatabaseLocal cleanDatabaseLocal;

    @Override
    public void populateDatabase() {
        cleanDatabaseLocal.cleanDatabase("mydb");
        populateGebuehrLocal.populateGebuehr();
        populateKundeLocal.populateKunde();
        populateZiehungLocal.populateZiehungen(40);
    }

}
