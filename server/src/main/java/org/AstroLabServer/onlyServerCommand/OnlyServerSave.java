package org.AstroLabServer.onlyServerCommand;

import org.AstroLabServer.collection.CustomCollection;
import org.AstroLabServer.files.JsonWriter;
import org.AstroLabServer.files.Writer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OnlyServerSave extends OnlyServerCommand{
    private static final Logger logger = LogManager.getLogger(OnlyServerSave.class);
    private final CustomCollection collection;

    public OnlyServerSave(CustomCollection collection){
        this.collection = collection;
    }

    @Override
    public OnlyServerResult execute() throws Exception{
        Writer writer = new Writer((new JsonWriter()));
        try {
            writer.writeToEnv(this.collection);
            logger.info("Collection saved successfully!");
            return OnlyServerResult.OK;
        } catch (Exception e) {
            logger.error("Ops... Program got some exception(s) while writing file!\n{}", String.valueOf(e));
            return OnlyServerResult.EXCEPTION;
        }
    }
}
