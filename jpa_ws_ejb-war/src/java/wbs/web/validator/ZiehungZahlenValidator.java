/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.web.validator;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Master
 */
public class ZiehungZahlenValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Long zahlenAlsBits = (Long) value;
        if (Long.bitCount(zahlenAlsBits) != 6) {
            Locale locale=FacesContext.getCurrentInstance().getViewRoot().getLocale();
            
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            String msg = bundle.getString("errZiehungszahlen");
            FacesMessage facesMessage = new FacesMessage(msg);
            throw new ValidatorException(facesMessage);
        }

    }

}
