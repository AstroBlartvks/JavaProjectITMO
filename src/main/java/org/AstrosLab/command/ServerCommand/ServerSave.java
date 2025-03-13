package org.AstrosLab.command.ServerCommand;

import org.AstrosLab.collection.CustomCollection;
import org.AstrosLab.command.CommandArgumentList;
import org.AstrosLab.files.JsonWriter;
import org.AstrosLab.files.Writer;

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
