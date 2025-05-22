package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.model.CreateRouteDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public ActionUpdate(@JsonProperty("ownerLogin") String ownerLogin,
                        @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.UPDATE);
    }
}
