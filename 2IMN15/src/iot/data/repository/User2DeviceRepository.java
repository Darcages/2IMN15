package iot.data.repository;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.User2Device;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class User2DeviceRepository {

    private Database db;

    /**
     * Creates a new instance of the User2DeviceRepository class.
     * @param db The database used to make a connection.
     */
    public User2DeviceRepository(Database db) {
        this.db = db;
    }

    /**
     * Creates the provided user2device.
     * @param d The device that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(User2Device d) {
        String query = "INSERT INTO user2device (UserID, DeviceID, PrioLevel, Red, Green, Blue, LowLight) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?);";

        Object[] data = {
            d.getUserID(),
            d.getDeviceID(),
            d.getPrioLevel(),
            d.getRed(),
            d.getGreen(),
            d.getBlue(),
            d.getLowLight()
        };

        try {
            this.db.executeQuery(query, data);
            return true;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            this.processUniqueKeyViolation(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes the user2device identified by the provided group number.
     * @param userID The user ID for which the device is to be deleted.
     * @param deviceID The device ID for which the device is to be deleted.
     * @return True if the deletion was successful. Otherwise false is returned.
     */
    public boolean delete(int userID, int deviceID) {
        String query = "DELETE FROM user2device WHERE UserID = ? AND DeviceID = ?;";
        Object[] data = { userID, deviceID };

        try {
            this.db.executeQuery(query, data);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Returns true if an user2device link exists for given userID and deviceID
     * @param userID The desk ID for which the device is to be checked.
     * @param deviceID The device ID for which the device is to be checked.
     * @return True if the device exists or an error has occurred. Otherwise false.
     */
    public boolean exists(int userID, int deviceID) {
        String query = "SELECT COUNT(*) FROM user2device WHERE UserID = ? AND DeviceID = ?;";
        Object[] data = { userID, deviceID };

        try {
            RowConversionFunction<Integer> rowConversion = rs -> {
              return rs.getInt(1);
            };

            return this.db.executeQuery(query, data, rowConversion).get(0) == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }


    /**
     * Retrieves all existing bindings between users and devices
     * @return The collection of existing bindings.
     */
    public ArrayList<User2Device> getAll() {
        String query = "SELECT UserID, DeviceID, PrioLevel, Red, Green, Blue, LowLight FROM user2device";
        Object[] data = {};

        try {
            RowConversionFunction<User2Device> rowConversion = rs -> {
                return User2Device.Make(
                    rs.getInt("UserID"),
                    rs.getInt("DeviceID"),
                    rs.getInt("PrioLevel"),
                    rs.getInt("Red"),
                    rs.getInt("Green"),
                    rs.getInt("Blue"),
                    rs.getBoolean("LowLight")

                );
            };

            return this.db.executeQuery(query, data, rowConversion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Updates the provided user2device.
     * @param d The user2device that is to be updated.
     * @return True if the update succeeded, otherwise false.
     */
    public boolean update(User2Device d) {
        String query = "UPDATE user2device " +
                "SET PrioLevel = ?, Red = ?, Green = ?, Blue = ?, LowLight = ? " +
                "WHERE UserID = ? AND DeviceID = ?;";

        Object[] data = {
                d.getPrioLevel(),
                d.getRed(),
                d.getGreen(),
                d.getBlue(),
                d.getLowLight(),
                d.getUserID(),
                d.getDeviceID()
        };

        try {
            this.db.executeQuery(query, data);
            return true;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            this.processUniqueKeyViolation(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Processes the unique key violation thrown by the database.
     * @param ex The exception that was thrown.
     * @throws IllegalArgumentException An expected unique key violation happened. The corresponding message is build.
     */
    private void processUniqueKeyViolation(MySQLIntegrityConstraintViolationException ex) throws IllegalArgumentException {
        Matcher matcher = Pattern
            .compile("^Duplicate entry '(\\d{1,11})-(\\d{1,11})' for key '(.+)'$")
            .matcher(ex.getMessage());

        if (matcher.matches()) {
            String key = matcher.group(3);

            switch (key) {
                case "Prio_UNIQUE":
                    int deviceId = Integer.parseInt(matcher.group(1));
                    int prio = Integer.parseInt(matcher.group(2));

                    throw new IllegalArgumentException(String.format(
                            "Another user is already assigned with priority '%s' to device '%s'.",
                            prio,
                            deviceId));
                default:
                    ex.printStackTrace();
                    break;
            }
        } else {
            ex.printStackTrace();
        }
    }
}
