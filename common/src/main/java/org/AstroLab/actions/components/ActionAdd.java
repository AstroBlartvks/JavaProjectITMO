package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.TypesOfActions;
import org.AstroLab.utils.model.CreateRouteDTO;

@Setter
@Getter
public class ActionAdd extends Action{
    private CreateRouteDTO createRouteDTO;

    public ActionAdd(){
        this.actionType = TypesOfActions.ADD;
    }
}
