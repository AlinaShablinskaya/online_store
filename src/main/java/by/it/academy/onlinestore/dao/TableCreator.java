package by.it.academy.onlinestore.dao;

import java.io.File;

import java.sql.Connection;

import org.hsqldb.cmdline.SqlFile;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TableCreator {
    private static final Logger LOGGER = LogManager.getLogger(TableCreator.class);

    private final DBConnector connector;

    public TableCreator(DBConnector connector) {
        this.connector = connector;
    }

    public void runScript(String scriptFile) {

        try (Connection connection = connector.getConnection()) {
            SqlFile file = new SqlFile(new File(scriptFile));
            file.setConnection(connection);
            file.execute();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }
}
