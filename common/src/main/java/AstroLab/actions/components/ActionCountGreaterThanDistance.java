package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.grpc.ActionsNameEnum;
import AstroLab.grpc.ClientServerActionMessage;
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
        this.setActionName(ActionsNameEnum.COUNT_GREATER_THAN_DISTANCE);
    }

    @Override
    public ClientServerActionMessage toProtobuf() {
        return ClientServerActionMessage.newBuilder()
                .setActionName(getActionName())
                .setOwnerLogin(getOwnerLogin())
                .setOwnerPassword(getOwnerPassword())
                .setDistance(getDistance())
                .build();
    }
}
