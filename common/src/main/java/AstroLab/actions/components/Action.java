package AstroLab.actions.components;

import AstroLab.actions.utils.ActionVisitable;
import AstroLab.grpc.ActionsNameEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {
    /**.
     * ActionsName actionName - name of action
     */
    private ActionsNameEnum actionName;

    public abstract void accept(ActionVisitable visitor) throws Exception;
    
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
