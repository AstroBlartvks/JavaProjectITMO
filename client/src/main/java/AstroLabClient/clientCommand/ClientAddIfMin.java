package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionAddIfMin;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;
import AstroLab.utils.model.CreateRouteDto;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientAddIfMin extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientAddIfMin(ArgumentRequester argumentRequester, UserDTO userDTO) {
        super(userDTO);
        this.argumentRequester = argumentRequester;
    }

    /**.
     * Command 'Add'
     *
     * @return CommandArgumentList arguments of command
     */
    @Override
    public ClientServerAction input(CommandArgumentList argumentList)
            throws IllegalArgumentException, SystemInClosedException {
        ActionAddIfMin action = new ActionAddIfMin(this.userDTO.getLogin(), this.userDTO.getPassword());
        CreateRouteDto createRouteDto = RouteDtoParser.parse(this.argumentRequester);
        createRouteDto.setOwnerLogin(this.userDTO.getLogin());
        action.setCreateRouteDto(createRouteDto);
        return action;
    }
}
