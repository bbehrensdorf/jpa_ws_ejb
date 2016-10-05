package wbs.web.util;

import java.security.Principal;
import javax.faces.context.FacesContext;

public class JSFUtil {

    public static String getRemoteUser() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public static Principal getUserPrincipal() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
    }

    public static boolean isLoggedIn() {
        return getRemoteUser() != null;
    }

    public static String getSessionId() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
    }

    public static void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
