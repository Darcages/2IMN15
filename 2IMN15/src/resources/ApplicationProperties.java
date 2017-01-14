package resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class contains methods related to the configuration properties of the application.
 */
public class ApplicationProperties {

    private final static String directory = "resources/";

    private final Properties properties;

    private ApplicationProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Retrieves the specified properties file.
     * @param file The properties file that is to be retrieved.
     * @return The properties in the specified properties file.
     * @throws IOException An exception has occurred during the retrieval of the properties.
     */
    public static ApplicationProperties open(String file) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(ApplicationProperties.directory + file);

        if (input == null) {
            throw new IOException(String.format("Could not find the properties file '%s'.", file));
        }

        try {
            Properties props = new Properties();
            props.load(input);

            return new ApplicationProperties(props);
        } finally {
            input.close();
        }
    }

    /**
     * Reads the property from the set of properties.
     * @param name The name of the property for which the value is to be retrieved.
     * @return The value of the property.
     * @throws NoSuchFieldException The property is not defined.
     */
    public String readString(String name) throws NoSuchFieldException {
        if (!this.properties.containsKey(name)) {
            throw new NoSuchFieldException(String.format("No property '%s' is defined.", name));
        }

        return this.properties.getProperty(name);
    }

    /**
     * Reads the property from the set of properties.
     * @param name The name of the property for which the value is to be retrieved.
     * @return The value of the property.
     * @throws NoSuchFieldException The property is not defined.
     * @throws NumberFormatException The property does not contain an integer value.
     */
    public int readInt(String name) throws NoSuchFieldException, NumberFormatException {
        return Integer.parseInt(this.readString(name));
    }
}
