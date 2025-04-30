package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.TypesOfActions;
import org.AstroLab.utils.model.CreateRouteDTO;

@Setter
@Getter
public class ActionRemoveGreater extends Action{
    private CreateRouteDTO createRouteDTO;

    public ActionRemoveGreater(){
        this.actionType = TypesOfActions.REMOVE_GREATER;
    }
}
