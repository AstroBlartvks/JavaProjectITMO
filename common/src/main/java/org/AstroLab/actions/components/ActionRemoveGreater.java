package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.ActionsName;
import org.AstroLab.utils.model.CreateRouteDTO;

@Setter
@Getter
public class ActionRemoveGreater extends ClientServerAction{
    private CreateRouteDTO createRouteDTO;

    public ActionRemoveGreater(){
        this.actionName = ActionsName.REMOVE_GREATER;
    }
}
