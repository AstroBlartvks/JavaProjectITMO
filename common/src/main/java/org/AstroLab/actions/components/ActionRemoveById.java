package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.ActionsName;

@Setter
@Getter
public class ActionRemoveById extends ClientServerAction{
    private Integer id;

    public ActionRemoveById(){
        this.actionName = ActionsName.REMOVE_BY_ID;
    }
}
