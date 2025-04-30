package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.TypesOfActions;
import org.AstroLab.utils.model.CreateRouteDTO;

@Setter
@Getter
public class ActionUpdate extends Action{
    private CreateRouteDTO createRouteDTO;
    private Integer id;

    public ActionUpdate(){
        this.actionType = TypesOfActions.UPDATE;
    }
}
