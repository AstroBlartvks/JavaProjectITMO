package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionHelp extends ClientServerAction {
    /**.
     * Create Action
     */
    public ActionHelp(@JsonProperty("ownerLogin") String ownerLogin,
                      @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.HELP);
    }
}
