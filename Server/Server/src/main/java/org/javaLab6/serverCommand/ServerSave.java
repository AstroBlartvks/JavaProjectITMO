package org.javaLab6.serverCommand;

import org.javaLab6.collection.CustomCollection;
import org.javaLab6.utils.ClientServer.ResponseStatus;
import org.javaLab6.utils.ClientServer.ServerResponse;
import org.javaLab6.utils.command.CommandArgumentList;
import org.javaLab6.files.JsonWriter;
import org.javaLab6.files.Writer;

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
