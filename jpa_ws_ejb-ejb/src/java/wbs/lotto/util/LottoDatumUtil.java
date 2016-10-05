package wbs.lotto.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

// return- wert einheitlich (uhrzeit) liefern, immer neues Date- objekt erzeugen
// TODO	beautify
// TODO ggf. rückdatierung abfangen
// TODO ggf. enum Teilnahme
public final class LottoDatumUtil {

    public static Date ersterZiehungstag(Date abgabeDatum, boolean isMittwoch,
            boolean isSamstag, int abgabeSchlussMittwoch,
            int abgabeschlussSamstag) {
        if (!isMittwoch && !isSamstag) {
            throw new IllegalArgumentException("invalid...");
        } else if (isMittwoch && isSamstag) {
            return mittwochUndSamstag(abgabeDatum, abgabeSchlussMittwoch,
                    abgabeschlussSamstag);
        } else if (isMittwoch) {
            return nurMittwoch(abgabeDatum, abgabeSchlussMittwoch);
        } else {
            return nurSamstag(abgabeDatum, abgabeschlussSamstag);
        }
    }

    public static Date naechsterZiehungstag(Date datum) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(datum);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return ersterZiehungstag(cal.getTime(), true,
            true, 18,18);
    }

    // liefert alle ziehungstage, an denen ein lottoschein mit gegebenem abgabedatum teilnimmt
    // TODO parameter check komplettieren
    public static List<Date> ziehungsTage(Date abgabeDatum, boolean isMittwoch,
            boolean isSamstag, int abgabeSchlussMittwoch,
            int abgabeSchlussSamstag, int laufzeit) {
        if (!isMittwoch && !isSamstag) {
            throw new IllegalArgumentException("illegal...");
        }
        List<Date> ziehungsTage = new ArrayList<>();
        Date ersterZiehungstag = ersterZiehungstag(abgabeDatum, isMittwoch,
                isSamstag, abgabeSchlussMittwoch, abgabeSchlussSamstag);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(ersterZiehungstag);
        ziehungsTage.add(ersterZiehungstag);
        if (isMittwoch != isSamstag) {
            for (int n = 1; n < laufzeit; n++) {
                cal.add(Calendar.DAY_OF_YEAR, 7);
                ziehungsTage.add(cal.getTime());
            }
        } else {
            for (int n = 1, m = (2 * laufzeit); n < m; n++) {
                cal.add(Calendar.DAY_OF_YEAR,
                        cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY ? 3
                        : 4);
                ziehungsTage.add(cal.getTime());
            }
        }
        return ziehungsTage;
    }

    private static Date nurMittwoch(Date abgabeDatum, int abgabeSchlussMittwoch) {
        Calendar cal = new GregorianCalendar();
        int stunde;
        cal.setTime(abgabeDatum);
        stunde = cal.get(Calendar.HOUR_OF_DAY);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
                && stunde < abgabeSchlussMittwoch) {
            return abgabeDatum;
        } else {
            return nachsterMittwoch(abgabeDatum);
        }
    }

    private static Date nurSamstag(Date abgabeDatum, int abgabeschlussSamstag) {
        Calendar cal = new GregorianCalendar();
        int stunde;
        cal.setTime(abgabeDatum);
        stunde = cal.get(Calendar.HOUR_OF_DAY);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                && stunde < abgabeschlussSamstag) {
            return abgabeDatum;
        } else {
            return nachsterSamstag(abgabeDatum);
        }
    }

    private static Date mittwochUndSamstag(Date abgabeDatum,
            int abgabeschlussMittwoch, int abgabeschlussSamstag) {
        // mi und stunde zu gross, do, fr, sa und stunde klein genug: nächster
        // samstag
        Calendar cal = new GregorianCalendar();
        cal.setTime(abgabeDatum);
        int tag = cal.get(Calendar.DAY_OF_WEEK);
        int stunde = cal.get(Calendar.HOUR_OF_DAY);
        if (tag == Calendar.WEDNESDAY && stunde < abgabeschlussMittwoch) {
            return cal.getTime();
        } else if (tag == Calendar.SATURDAY && stunde < abgabeschlussSamstag) {
            return cal.getTime();
        } else if ((tag == Calendar.WEDNESDAY && stunde >= abgabeschlussMittwoch)
                || tag == Calendar.THURSDAY
                || tag == Calendar.FRIDAY
                || (tag == Calendar.SATURDAY && stunde < abgabeschlussSamstag)) {
            return nachsterSamstag(abgabeDatum);
        } else {
            return nachsterMittwoch(abgabeDatum);
        }
    }

    private static Date nachsterMittwoch(Date abgabeDatum) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(abgabeDatum);
        int calDay = cal.get(Calendar.DAY_OF_WEEK);
        int mittwoch = Calendar.WEDNESDAY;
        int diff = calDay - mittwoch;
        if (diff < 0) {
            cal.add(Calendar.DAY_OF_MONTH, -diff);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 7 - diff);
        }
        return cal.getTime();
    }

    private static Date nachsterSamstag(Date abgabeDatum) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(abgabeDatum);
        int calDay = cal.get(Calendar.DAY_OF_WEEK);
        int samstag = Calendar.SATURDAY;
        int diff = calDay - samstag;
        if (diff < 0) {
            cal.add(Calendar.DAY_OF_MONTH, -diff);
        } else {
            cal.add(Calendar.DAY_OF_MONTH, 7 - diff);
        }
        return cal.getTime();
    }
}
