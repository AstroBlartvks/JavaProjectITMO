package AstroLabServer.onlyServerCommand;

public class OnlyServerExit extends OnlyServerCommand {
    @Override
    public OnlyServerResult execute() {
        return OnlyServerResult.EXIT;
    }
}
