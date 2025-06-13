package AstroLab.actions.components;

import AstroLab.actions.utils.ActionsName;
import AstroLab.grpc.ActionsNameEnum;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.model.CreateRouteDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionRemoveGreater extends ClientServerAction {
    /**.
     * createRouteDTO - argument for Action
     */
    private CreateRouteDto createRouteDto;

    /**.
     * Create Action
     */
    public ActionRemoveGreater(@JsonProperty("ownerLogin") String ownerLogin,
                               @JsonProperty("ownerPassword") String ownerPassword){
        super(ownerLogin, ownerPassword);
        this.setActionName(ActionsNameEnum.REMOVE_GREATER);
    }

    @Override
    public ClientServerActionMessage toProtobuf() {
        return ClientServerActionMessage.newBuilder()
                .setActionName(getActionName())
                .setOwnerLogin(getOwnerLogin())
                .setOwnerPassword(getOwnerPassword())
                .setRouteDto(getCreateRouteDto().convertToProtobuf())
                .build();
    }
}
