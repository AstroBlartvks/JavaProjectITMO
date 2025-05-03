package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.ActionsName;

@Setter
@Getter
public class ActionCountByDistance extends ClientServerAction{
    private Double distance;

    public ActionCountByDistance(){
        this.actionName = ActionsName.COUNT_BY_DISTANCE;
    }
}
