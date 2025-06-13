package AstroLabClient.clientCommand;

import AstroLab.actions.components.Action;
import AstroLab.actions.components.ActionAddIfMax;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.grpc.ClientServerActionMessage;
import AstroLab.utils.command.CommandArgumentList;
import AstroLab.utils.model.CreateRouteDto;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientAddIfMax extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMax(ArgumentRequester argumentRequester, UserDTO userDTO) {
        super(userDTO);
        this.argumentRequester = argumentRequester;
    }

    /**.
     * Command 'Add'
     *
     * @return CommandArgumentList arguments of command
     */
    @Override
    public Action input(CommandArgumentList argumentList)
            throws IllegalArgumentException, SystemInClosedException {
        ActionAddIfMax action = new ActionAddIfMax(this.userDTO.getLogin(), this.userDTO.getPassword());
        CreateRouteDto createRouteDto = RouteDtoParser.parse(this.argumentRequester);
        createRouteDto.setOwnerLogin(this.userDTO.getLogin());
        action.setCreateRouteDto(createRouteDto);
        return action;
    }
}
