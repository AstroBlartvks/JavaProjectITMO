package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public ActionCountGreaterThanDistance(@JsonProperty("ownerLogin") String ownerLogin,
                                          @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.COUNT_GREATER_THAN_DISTANCE);
    }
}
