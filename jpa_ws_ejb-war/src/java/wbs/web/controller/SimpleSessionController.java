package wbs.web.controller;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import wbs.web.util.JSFUtil;

@SessionScoped
@Named
public class SimpleSessionController implements Serializable {

    private final AtomicInteger counter = new AtomicInteger();

    public String logInOutlabel() {
        return JSFUtil.isLoggedIn() ? "logout" : "login";
    }

    public String logInOut() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (!JSFUtil.isLoggedIn()) {
            try {
                //request.login("gz2", "test");
                request.login("lottospieler1", "lottospieler1");
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                request.logout();
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
        return "aha";
    }

    public void countClicks() {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.intValue();
    }

    public String getSessionId() {
        return JSFUtil.getSessionId();
    }

    public String getRemoteUser() {
        return JSFUtil.getRemoteUser();
    }

    public void invalidateSession() {
        JSFUtil.invalidateSession();
    }
}
