package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.grpc.ActionsNameEnum;
import AstroLab.grpc.ClientServerActionMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionPrintFieldDescendingDistance extends ClientServerAction {
    /**.
     * Create Action
     */
    public ActionPrintFieldDescendingDistance(@JsonProperty("ownerLogin") String ownerLogin,
                                              @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsNameEnum.PRINT_FIELD_DESCENDING_DISTANCE);
    }

    @Override
    public ClientServerActionMessage toProtobuf() {
        return ClientServerActionMessage.newBuilder()
                .setActionName(getActionName())
                .setOwnerLogin(getOwnerLogin())
                .setOwnerPassword(getOwnerPassword())
                .build();
    }
}
