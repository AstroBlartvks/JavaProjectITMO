package AstroLabServer.onlyServerCommand;

import AstroLab.actions.components.Action;
import AstroLab.utils.ClientServer.ClientRequest;
import AstroLab.utils.ClientServer.ResponseStatus;
import AstroLab.utils.ClientServer.ServerResponse;
import AstroLabServer.auth.AuthService;
import AstroLabServer.serverCommand.CommandManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class ServerUtils {
    public static final Logger LOGGER = LogManager.getLogger(ServerUtils.class);
    private final ServerCommandManager serverCommandManager;
    private final AuthService authService;
    private final ExecutorService processPool;
    private final CommandManager commandManager;

    public ServerUtils(ServerCommandManager serverCommandManager,
                       AuthService authService,
                       ExecutorService processPool,
                       CommandManager commandManager){
        this.serverCommandManager = serverCommandManager;
        this.authService = authService;
        this.processPool = processPool;
        this.commandManager = commandManager;
    }

    public OnlyServerResult readConsoleCommand() throws IOException {
        if (System.in.available() > 0) {
            byte[] buffer = new byte[System.in.available()];
            int res = System.in.read(buffer);
            if (res == '\n' || res == -1) {
                return OnlyServerResult.OK;
            }
            String str = new String(buffer, StandardCharsets.UTF_8);
            try {
                return serverCommandManager.executeCommand(str.trim());
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                return OnlyServerResult.EXCEPTION;
            }
        }
        return OnlyServerResult.NOTHING;
    }

    public ServerResponse executeCommandSafely(ClientRequest clientRequest) {
        try {
            Action action = clientRequest.getRequest();
            return commandManager.executeCommand(action);
        } catch (Exception e) {
            return new ServerResponse(ResponseStatus.EXCEPTION, e.getMessage());
        }
    }
}