package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionShow extends ClientServerAction {
    /**.
     * Create Action
     */
    public ActionShow(@JsonProperty("ownerLogin") String ownerLogin,
                      @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.SHOW);
    }
}
