package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.ActionsName;
import org.AstroLab.utils.model.CreateRouteDTO;

@Setter
@Getter
public class ActionAdd extends ClientServerAction{
    private CreateRouteDTO createRouteDTO;

    public ActionAdd(){
        this.actionName = ActionsName.ADD;
    }
}
