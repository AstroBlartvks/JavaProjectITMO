package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.model.CreateRouteDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionUpdate extends ClientServerAction {
    /**.
     *  createRouteDTO for action
     */
    private CreateRouteDto createRouteDto;
    /**.
     * Integer id for action
     */
    private Integer id;

    /**.
     * Create Action
     */
    public ActionUpdate() {
        this.setActionName(ActionsName.UPDATE);
    }
}
