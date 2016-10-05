package wbs.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

public class ZiehungConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        long zahlenAlsBits = 0L;
        int zahl;
        try {
            String[] sZahlen = value.split("\\s+");
            if (sZahlen.length == 6) {
                for (String sZahl : sZahlen) {
                    zahl = Integer.parseInt(sZahl);
                    if (zahl < 1 || zahl > 49) {
                        zahlenAlsBits = Long.MIN_VALUE;
                        break;
                    }
                    zahlenAlsBits = zahlenAlsBits | (1L << zahl);
                }
            } else {
                zahlenAlsBits = Long.MIN_VALUE;
            }
        } catch (RuntimeException e) {
            zahlenAlsBits = Long.MIN_VALUE;
        }
        return zahlenAlsBits;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value
    ) {
        String result = "";
        Long l = (Long) value;
        for (int i = 1; i <= 49; i++) {
            if ((l & 1L << i) != 0) {
                result += i + " ";
            }
        }

        return result.trim();

    }

}
