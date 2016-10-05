package wbs.web.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import wbs.lotto.initdb.CleanDatabaseLocal;
import wbs.lotto.initdb.PopulateDatabaseLocal;

@RequestScoped
@Named
public class PopulateController {
    @Inject
    private PopulateDatabaseLocal populateDatabaseLocal;

    public String populateDatabase() {
        populateDatabaseLocal.populateDatabase();
        return "populated";
    }

    
    
}
