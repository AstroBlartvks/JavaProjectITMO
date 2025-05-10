package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionCountByDistance extends ClientServerAction {
    /**.
     * Double distance - argument to action
     */
    private Double distance;

    /**.
     * Create Action
     */
    public ActionCountByDistance() {
        this.setActionName(ActionsName.COUNT_BY_DISTANCE);
    }
}
