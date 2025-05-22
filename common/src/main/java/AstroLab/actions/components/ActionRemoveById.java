package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionRemoveById extends ClientServerAction {
    /**.
     * Integer id - argument for Action - route->id
     */
    private Integer id;

    /**.
     * Create Action
     */
    public ActionRemoveById(@JsonProperty("ownerLogin") String ownerLogin,
                            @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.REMOVE_BY_ID);
    }
}
