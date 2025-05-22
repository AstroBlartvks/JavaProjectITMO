package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.utils.model.CreateRouteDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionAddIfMax extends ClientServerAction {
    /**.
     * createRouteDTO - route DTO
     */
    private CreateRouteDto createRouteDto;

    /**.
     * Create Action
     */
    public ActionAddIfMax(@JsonProperty("ownerLogin") String ownerLogin,
                          @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.ADD_IF_MAX);
    }
}
