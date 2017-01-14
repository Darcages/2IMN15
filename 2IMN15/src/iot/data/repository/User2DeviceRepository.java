package iot.data.repository;

import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.Device2Desk;
import iot.domain.User2Device;

import java.sql.SQLException;
import java.util.ArrayList;


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


        String query2 = "DELETE FROM user2device WHERE UserID = ? AND DeviceID = ?;";
        Object[] data2 = { userID, deviceID };

        try {
            this.db.executeQuery(query2, data2);
            return  true;
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
}
