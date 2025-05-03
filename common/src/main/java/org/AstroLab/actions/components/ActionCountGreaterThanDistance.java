package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.ActionsName;

@Setter
@Getter
public class ActionCountGreaterThanDistance extends ClientServerAction{
    private Double distance;

    public ActionCountGreaterThanDistance(){
        this.actionName = ActionsName.COUNT_GREATER_THAN_DISTANCE;
    }
}
