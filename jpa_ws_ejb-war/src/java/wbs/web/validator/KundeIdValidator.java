package wbs.web.validator;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import wbs.lotto.persistence.KundeFacadeLocal;
import wbs.lotto.persistence.LottoscheinFacadeLocal;

@FacesValidator
public class KundeIdValidator implements Validator{

    @EJB
    KundeFacadeLocal kundeFacadeLocal;
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Long kundeId= (Long) value;
        
        if (kundeFacadeLocal.find(kundeId) == null) {
            Locale locale=FacesContext.getCurrentInstance().getViewRoot().getLocale();
            
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            String msg = bundle.getString("errKundeId");
            FacesMessage facesMessage = new FacesMessage(msg);
            throw new ValidatorException(facesMessage);
            
        }
        

        
    }
    
    
}
