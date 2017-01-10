package iot.data;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Geert on 2017-01-10.
 */
public class Database {

    private static final String PropertiesFile = "/resources/database.properties";

    private DataSource source;

    private Database(DataSource source) {
        this.source = source;
    }

    /**
     * Creates a new connection to the database.
     * @return The new connection.
     * @throws SQLException An error has occurred while trying to connect.
     */
    public Connection connect() throws SQLException {
        return this.source.getConnection();
    }

    public static Database load() throws IOException, NoSuchFieldException {
        Properties props = Database.getProperties();

        String server = Database.readStringProperty(props, "server");
        int port = Database.readIntProperty(props, "port");
        String username = Database.readStringProperty(props, "user");
        String password = Database.readStringProperty(props, "password");
        String database = Database.readStringProperty(props, "database");

        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(server);
        ds.setPort(port);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setDatabaseName(database);

        return new Database(ds);
    }

    /**
     * Retrieves the database properties.
     * @return The database properties.
     * @throws IOException An exception has occurred during the retrieval of the properties.
     */
    private static Properties getProperties() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(PropertiesFile);

        try {
            Properties props = new Properties();
            props.load(input);

            return props;
        } finally {
            input.close();
        }
    }

    /**
     * Reads the property from the set of properties.
     * @param props The set of properties.
     * @param name The name of the property for which the value is to be retrieved.
     * @return The value of the property.
     * @throws NoSuchFieldException The property is not defined.
     */
    private static String readStringProperty(Properties props, String name) throws NoSuchFieldException {
        if (!props.containsKey("server")) {
            throw new NoSuchFieldException("No property 'server' is defined.");
        }

        return props.getProperty(name);
    }

    /**
     * Reads the property from the set of properties.
     * @param props The set of properties.
     * @param name The name of the property for which the value is to be retrieved.
     * @return The value of the property.
     * @throws NoSuchFieldException The property is not defined.
     * @throws NumberFormatException The property does not contain an integer value.
     */
    private static int readIntProperty(Properties props, String name) throws NoSuchFieldException, NumberFormatException {
        String value = readStringProperty(props, name);

        return Integer.parseInt(value);
    }
}
