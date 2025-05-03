package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.ActionsName;
import org.AstroLab.utils.model.CreateRouteDTO;

@Setter
@Getter
public class ActionUpdate extends ClientServerAction{
    private CreateRouteDTO createRouteDTO;
    private Integer id;

    public ActionUpdate(){
        this.actionName = ActionsName.UPDATE;
    }
}
