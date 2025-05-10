package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionCountGreaterThanDistance extends ClientServerAction {
    /**.
     * Double distance - argument for Action
     */
    private Double distance;

    /**.
     * Create Action
     */
    public ActionCountGreaterThanDistance() {
        this.setActionName(ActionsName.COUNT_GREATER_THAN_DISTANCE);
    }
}
