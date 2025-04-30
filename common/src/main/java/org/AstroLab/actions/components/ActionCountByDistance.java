package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.TypesOfActions;

@Setter
@Getter
public class ActionCountByDistance extends Action{
    private Double distance;

    public ActionCountByDistance(){
        this.actionType = TypesOfActions.COUNT_BY_DISTANCE;
    }
}
