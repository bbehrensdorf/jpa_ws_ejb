package wbs.web.controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import wbs.lotto.initdb.CleanDatabaseLocal;

@RequestScoped
@Named
public class CleanController implements Serializable {

    private boolean resetLottoscheinZiehung = false;

    @Inject
    private CleanDatabaseLocal cleanDatabaseLocal;

    public String cleanDatabase() {
        cleanDatabaseLocal.cleanDatabase("mydb");
        return "cleaned";
    }

    public String resetZiehungsAuswertung() {
        try {
            cleanDatabaseLocal.resetZiehungsAuswertung(14,resetLottoscheinZiehung);
            
        } catch (Exception ex) {
            Logger.getLogger(CleanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "cleaned";
    }

    public boolean isResetLottoscheinZiehung() {
        return resetLottoscheinZiehung;
    }

    public void setResetLottoscheinZiehung(boolean resetLottoscheinZiehung) {
        this.resetLottoscheinZiehung = resetLottoscheinZiehung;
    }

}
