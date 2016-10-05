package wbs.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext context = session.getServletContext();
        String name = event.getName();
        Object value = event.getValue();
        context.log("added: " + name + "(" + value + ")");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext context = session.getServletContext();
        String name = event.getName();
        Object value = event.getValue();
        context.log("removed: " + name + "(" + value + ")");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        ServletContext context = session.getServletContext();
        String name = event.getName();
        Object value = event.getValue();
        context.log("replaced: " + name + "(" + value + ")");
    }
}
