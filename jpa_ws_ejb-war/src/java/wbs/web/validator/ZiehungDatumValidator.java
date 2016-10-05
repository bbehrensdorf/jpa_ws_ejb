/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wbs.web.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class ZiehungDatumValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        Calendar cal = new GregorianCalendar();
        cal.setTime((Date) value);
        if ((cal.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) && (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());
            String msg = bundle.getString("errZiehungsdatum");
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
            throw new ValidatorException(facesMessage);

        }

    }

}
