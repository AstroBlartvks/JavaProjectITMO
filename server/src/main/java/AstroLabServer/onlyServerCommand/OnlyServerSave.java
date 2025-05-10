package AstroLabServer.onlyServerCommand;

import AstroLabServer.collection.CustomCollection;
import AstroLabServer.files.JsonWriter;
import AstroLabServer.files.Writer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OnlyServerSave extends OnlyServerCommand {
    private static final Logger LOGGER = LogManager.getLogger(OnlyServerSave.class);
    private final CustomCollection collection;

    public OnlyServerSave(CustomCollection collection) {
        this.collection = collection;
    }

    @Override
    public OnlyServerResult execute() {
        Writer writer = new Writer((new JsonWriter()));
        try {
            writer.writeToEnv(this.collection);
            LOGGER.info("Collection saved successfully!");
            return OnlyServerResult.OK;
        } catch (Exception e) {
            LOGGER.error("Ops... Program got some exception(s) while writing file!\n{}", String.valueOf(e));
            return OnlyServerResult.EXCEPTION;
        }
    }
}
