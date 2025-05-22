package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public ActionCountByDistance(@JsonProperty("ownerLogin") String ownerLogin,
                                 @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.COUNT_BY_DISTANCE);
    }
}
