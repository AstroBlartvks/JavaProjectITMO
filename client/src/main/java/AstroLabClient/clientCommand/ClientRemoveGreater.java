package AstroLabClient.clientCommand;

import AstroLab.actions.components.ActionRemoveGreater;
import AstroLab.actions.components.ClientServerAction;
import AstroLab.auth.UserDTO;
import AstroLab.utils.command.CommandArgumentList;
import AstroLab.utils.model.CreateRouteDto;
import AstroLabClient.inputManager.ArgumentRequester;
import AstroLabClient.inputManager.SystemInClosedException;

public class ClientRemoveGreater extends ClientCommand {
    private final ArgumentRequester argumentRequester;

    public ClientRemoveGreater(ArgumentRequester argumentRequester, UserDTO userDTO) {
        super(userDTO);
        this.argumentRequester = argumentRequester;
    }

    @Override
    public ClientServerAction input(CommandArgumentList argumentList)
            throws IllegalArgumentException, SystemInClosedException {
        ActionRemoveGreater action = new ActionRemoveGreater(this.userDTO.getLogin(), this.userDTO.getPassword());
        CreateRouteDto createRouteDto = RouteDtoParser.parse(this.argumentRequester);
        createRouteDto.setOwnerLogin(this.userDTO.getLogin());
        action.setCreateRouteDto(createRouteDto);
        return action;
    }
}
