package wbs.web.controller;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import wbs.lotto.service.ZiehungAuswertenLocal;
import wbs.lotto.service.ZiehungAuswertenDataLocal_neu;

@RequestScoped
@Named
public class ZiehungAuswertenController implements Serializable {

    @EJB
    private ZiehungAuswertenLocal ziehungAuswertenLocal;
    @EJB
    private ZiehungAuswertenDataLocal_neu ziehungAuswertenData_neu;

        public String auswerten() {
        System.out.println("Rufe 'ziehungAuswertenLocal' auf...");
        double startZeit = System.nanoTime();
        ziehungAuswertenLocal.mainLoop(14L);
        ziehungAuswertenData_neu.setAuswertungslaufzeit((System.nanoTime() - startZeit) / 1000_000_000L);
        

        System.out.println("Zurück im Controller");
        return "";
    }

    
    // Ab hier: Spielwiese für eine evtl. erweiterte Version
    
    
    public String analyze() {
        System.out.println("Auswertung starten...");
        //ziehungAuswertenData1.setAuswertungsFuture(ziehungAuswertenLocal.auswertung_async(14));
        ziehungAuswertenLocal.auswertung(14);
        long anz = ziehungAuswertenData_neu.getAnzahlScheineBearbeitet();
        ziehungAuswertenData_neu.setAuswertungsMeldung("Auswertung erstmal beendet.");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Auswertung beendet.", "Es sind jetzt " + anz + " Lottoscheine bearbeitet."));
        return "/admin/ziehungAuswerten.faces";
    }

    
    public void setMeldungStart() {
        ziehungAuswertenData_neu.setAuswertungsMeldung("Auswertung läuft...");
    } 
    public void testExecute() {
        System.out.println("Klick");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Executed", "Using RemoteCommand."));
    }


    public void initZiehung() {
        ziehungAuswertenData_neu.initZiehung();

    }

    public ZiehungAuswertenDataLocal_neu getZiehungAuswertenData_neu() {
        return ziehungAuswertenData_neu;
    }

    public void checkAuswertung() {
        System.out.println("Checking...");
        Future<String> future = ziehungAuswertenData_neu.getAuswertungsFuture();
        if (future != null) {
            System.out.println("Back to the Future!");
            if (future.isDone()) {
                System.out.println("Future is here!!!");
                try {
                    ziehungAuswertenData_neu.setAuswertungsMeldung(future.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(ZiehungAuswertenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Waiting for Future...");
                ziehungAuswertenData_neu.setAuswertungsMeldung("Auswertung läuft immer noch...");
            }
        } else {
            System.out.println("No future");
            ziehungAuswertenData_neu.setAuswertungsMeldung("Da läuft wohl nix...");
        }
    }

//    @PostConstruct 
//    public void init() {
//        System.out.println("Init...");
//        ziehungAuswertenLocal.initZiehung(14);
//    }
}
