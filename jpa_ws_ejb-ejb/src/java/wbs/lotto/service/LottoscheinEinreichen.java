package wbs.lotto.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import wbs.lotto.domain.Lottoschein;
import wbs.lotto.domain.Lottoscheinziehung;
import wbs.lotto.domain.Ziehung;
import wbs.lotto.jpafeatures.JPQLQueryLocal;
import wbs.lotto.jpafeatures.NamedQueryLocal;
import wbs.lotto.persistence.LottoscheinFacadeLocal;
import wbs.lotto.persistence.LottoscheinziehungFacadeLocal;
import wbs.lotto.persistence.ZiehungFacadeLocal;
import wbs.lotto.util.LottoDatumUtil;

@Stateless
public class LottoscheinEinreichen implements LottoscheinEinreichenLocal {

    @EJB
    private LottoscheinFacadeLocal lottoscheinFacadeLocal;

    @EJB
    private LottoscheinziehungFacadeLocal lottoscheinziehungFacadeLocal;

    @EJB
    private NamedQueryLocal namedQueryLocal;

    @Override
    public void lottoscheinEinreichen(Lottoschein lottoschein, int laufzeit) {

        Date date = new Date();
        lottoschein.setAbgabeDatum(date);

        lottoschein.setCreated(date);
        lottoschein.setLastModified(date);
        lottoschein.setLaufzeit(laufzeit);
        lottoschein.setKosten(0);
        lottoschein.setLottoscheinziehungList(new ArrayList<>());

        List<Date> ziehungsTage = LottoDatumUtil.ziehungsTage(
                date,
                lottoschein.getIsMittwoch(),
                lottoschein.getIsSamstag(),
                18,
                18,
                laufzeit);
        for (int i = 0; i < ziehungsTage.size(); i++) {
            Date ziehungstag = ziehungsTage.get(i);
            List<Ziehung> l = namedQueryLocal.nq2(ziehungstag);
            Ziehung ziehung = null;

            if (l.size() > 0) {
                ziehung = (l.get(0));
            }

            Lottoscheinziehung lottoscheinziehung = new Lottoscheinziehung();

            lottoscheinziehung.setLottoscheinId(lottoschein);
            lottoscheinziehung.setZiehungId(ziehung);

            lottoscheinziehung.setCreated(date);
            lottoscheinziehung.setLastModified(date);

            lottoscheinziehung.setIsAbgeschlossen(false);
            lottoscheinziehung.setZiehungNr(i + 1);
            lottoscheinziehung.setIsLetzteZiehung(i == ziehungsTage.size() - 1);
            lottoscheinziehung.setLottoscheinId(lottoschein);

            //lottoscheinziehungFacadeLocal.create(lottoscheinziehung);
            lottoschein.getLottoscheinziehungList().add(lottoscheinziehung);

        }
        lottoscheinFacadeLocal.create(lottoschein);

    }

}
