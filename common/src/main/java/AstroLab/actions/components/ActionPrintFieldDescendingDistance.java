package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionPrintFieldDescendingDistance extends ClientServerAction {
    /**.
     * Create Action
     */
    public ActionPrintFieldDescendingDistance(@JsonProperty("ownerLogin") String ownerLogin,
                                              @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsName.PRINT_FIELD_DESCENDING_DISTANCE);
    }
}
