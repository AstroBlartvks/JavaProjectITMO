package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.model.CreateRouteDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionRemoveGreater extends ClientServerAction {
    /**.
     * createRouteDTO - argument for Action
     */
    private CreateRouteDto createRouteDto;

    /**.
     * Create Action
     */
    public ActionRemoveGreater() {
        this.setActionName(ActionsName.REMOVE_GREATER);
    }
}
