package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.model.CreateRouteDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionAddIfMin extends ClientServerAction {
    /**.
     * routeDTO
     */
    private CreateRouteDto createRouteDto;

    /**.
     * Create Action
     */
    public ActionAddIfMin(@JsonProperty("ownerLogin") String ownerLogin,
                          @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.ADD_IF_MIN);
    }
}
