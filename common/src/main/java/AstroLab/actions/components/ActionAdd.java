package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.model.CreateRouteDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionAdd extends ClientServerAction {
    /**.
     * createRouteDTO - route Data transfer object
     */
    private CreateRouteDto createRouteDto;

    /**.
     * Create Action
     */
    public ActionAdd() {
        this.setActionName(ActionsName.ADD);
    }
}
