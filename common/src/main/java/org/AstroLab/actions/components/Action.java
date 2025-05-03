package org.AstroLab.actions.components;

import lombok.Getter;
import org.AstroLab.actions.utils.ActionsName;

@Getter
public abstract class Action {
    protected ActionsName actionName;

    @Override
    public String toString() {
        return "Action{" +
                "actionName=" + actionName +
                '}';
    }
}
