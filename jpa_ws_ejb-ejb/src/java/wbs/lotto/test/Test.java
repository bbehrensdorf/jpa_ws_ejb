package wbs.lotto.test;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import wbs.lotto.domain.Gebuehr;
import wbs.lotto.persistence.GebuehrFacade;
import wbs.lotto.persistence.GebuehrFacadeLocal;
@Stateless
public class Test implements TestLocal {

    @EJB
    private GebuehrFacadeLocal gebuehrFacadeLocal;

    @Override
    public Gebuehr getGebuehr() {
        return gebuehrFacadeLocal.getCurrentGebuehr();
    }

}
