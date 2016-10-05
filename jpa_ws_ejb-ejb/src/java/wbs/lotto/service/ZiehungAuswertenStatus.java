package wbs.lotto.service;

import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;

@Singleton
@ApplicationScoped
public class ZiehungAuswertenStatus implements ZiehungAuswertenStatusLocal {
    private int status=0;

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(int status) {
        this.status=status;
    }
    
    
}
