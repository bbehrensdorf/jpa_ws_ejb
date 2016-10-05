package wbs.lotto.service;

import java.util.concurrent.Future;
import javax.ejb.Local;

@Local
public interface ZiehungAuswertenLocal {

    public void initZiehung(long ziehungId);
    public void mainLoop(long ziehungId);

    public Future<String> auswertung_async(long ziehungId);

    public void auswertung(long ziehungId);



}
