package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {
    /**.
     * ActionsName actionName - name of action
     */
    private ActionsName actionName;

    /**.
     * Return description of class
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Action{" + "actionName=" + actionName + '}';
    }
}
