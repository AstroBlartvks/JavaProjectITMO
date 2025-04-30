package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.TypesOfActions;

@Setter
@Getter
public class ActionCountGreaterThanDistance extends Action{
    private Double distance;

    public ActionCountGreaterThanDistance(){
        this.actionType = TypesOfActions.COUNT_GREATER_THAN_DISTANCE;
    }
}
