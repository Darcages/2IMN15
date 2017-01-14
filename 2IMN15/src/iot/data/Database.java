package iot.data;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import resources.ApplicationProperties;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

/**
 * Created by Geert on 2017-01-10.
 */
public class Database {

    private static final String PropertiesFile = "database.properties";

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

    /**
     * Executes the provided query and returns the result.
     * @param query The prepared query statement that is to be executed.
     * @param parameters The query parameters that will be put on the locations of the '?'in the query.
     * @param read The function that is called per result row.
     * @param <R> The type of result that is to be returned.
     * @return The collection of results.
     * @throws SQLException An error occurred during the execution of the provided query.
     */
    public <R> ArrayList<R> executeQuery(String query, Object[] parameters, RowConversionFunction<R> read) throws SQLException {
        ExecuteQueryFunction<R> execute = st -> {
            ResultSet rs = null;

            try {
                rs = st.executeQuery();

                ArrayList<R> result = new ArrayList<>();

                while (rs.next()) {
                    R obj = read.apply(rs);
                    result.add(obj);
                }

                return result;
            } finally {
                if (rs != null && !rs.isClosed()) rs.close();
            }
        };

        return this.executeQueryBasis(query, parameters, execute);
    }

    /**
     * Executes the provided query.
     * @param query The prepared query statement that is to be executed.
     * @param parameters The query parameters that will be put on the locations of the '?'in the query.
     * @throws SQLException An error occurred during the execution of the provided query.
     */
    public void executeQuery(String query, Object[] parameters) throws SQLException {
        ExecuteQueryFunction<Optional> execute = ps -> {
            ps.executeUpdate();
            return new ArrayList<>();
        };

        this.executeQueryBasis(query, parameters, execute);
    }

    /**
     * Creates a prepared statement from the query and its parameters, then executes the provided function.
     * @param query The prepared query statement that is to be executed.
     * @param parameters The query parameters that will be put on the locations of the '?'in the query.
     * @param execute The function that is to be executed. The created prepared statement is its parameter.
     * @param <R> The type of the elements in the result collection.
     * @return The result after executing the query.
     * @throws SQLException An error occurred during the execution of the provided query.
     */
    private <R> ArrayList<R> executeQueryBasis(String query, Object[] parameters, ExecuteQueryFunction<R> execute)
            throws SQLException {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = this.connect();
            st = conn.prepareStatement(query);

            for (int i = 0; i < parameters.length; i++) {
                st.setObject(i + 1, parameters[i]);
            }

            return execute.apply(st);
        } finally {
            if (st != null && !st.isClosed()) st.close();
            if (conn != null && !conn.isClosed()) conn.close();
        }
    }

    public static Database load() throws IOException, NoSuchFieldException {
        ApplicationProperties props = ApplicationProperties.open(PropertiesFile);

        String server = props.readString("server");
        int port = props.readInt("port");
        String username = props.readString("user");
        String password = props.readString("password");
        String database = props.readString("database");

        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(server);
        ds.setPort(port);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setDatabaseName(database);

        return new Database(ds);
    }

    /**
     * A new functional interface that will execute the provided prepared statement.
     * @param <R> The result of the query execution.
     */
    @FunctionalInterface
    interface ExecuteQueryFunction<R> {
        ArrayList<R> apply(PreparedStatement ps) throws SQLException;
    }
}
