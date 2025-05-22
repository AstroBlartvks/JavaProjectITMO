package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionInfo extends ClientServerAction {
    /**.
     * Create Action
     */
    public ActionInfo(@JsonProperty("ownerLogin") String ownerLogin,
                      @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.INFO);
    }
}
