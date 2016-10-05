package wbs.web.controller;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;


import javax.inject.Named;
import wbs.lotto.service.ZiehungAuswertenDataLocal_neu;
import wbs.lotto.service.ZiehungAuswertenLocal;

@ViewScoped
@Named
public class ZiehungAuswertenView implements Serializable {

    private boolean editMode;

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        System.out.println("Edit Mode:" +  editMode);
        this.editMode = editMode;
    }

}
