package org.AstroLab.actions.components;

import lombok.Getter;
import lombok.Setter;
import org.AstroLab.actions.utils.TypesOfActions;

@Setter
@Getter
public class ActionRemoveById extends Action{
    private Integer id;

    public ActionRemoveById(){
        this.actionType = TypesOfActions.REMOVE_BY_ID;
    }
}
