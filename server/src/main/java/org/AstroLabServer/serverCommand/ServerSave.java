package org.AstroLabServer.serverCommand;

import org.AstroLabServer.collection.CustomCollection;
import org.AstroLab.utils.ClientServer.ResponseStatus;
import org.AstroLab.utils.ClientServer.ServerResponse;
import org.AstroLab.utils.command.CommandArgumentList;
import org.AstroLabServer.files.JsonWriter;
import org.AstroLabServer.files.Writer;

public class ServerSave extends ServerCommand{
    private final CustomCollection collection;

    public ServerSave(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public ServerResponse execute(CommandArgumentList args) throws Exception {

        Writer writer = new Writer((new JsonWriter()));
        try {
            writer.writeToEnv(collection);
        } catch (Exception e) {
            throw new Exception("Ops... Program got some exception(s) while writing file!\n" + e);
        }

        return new ServerResponse(ResponseStatus.OK, "File saved successfully!");
    }
}
