package iot.data.repository;

import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.Desk;
import iot.domain.Device2Desk;

import java.sql.SQLException;
import java.util.ArrayList;


public class Device2DeskRepository {

    private Database db;

    /**
     * Creates a new instance of the DeviceRepository class.
     * @param db The database used to make a connection.
     */
    public Device2DeskRepository(Database db) {
        this.db = db;
    }

    /**
     * Creates the provided device.
     * @param d The device that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(Device2Desk d) {
        String query = "INSERT INTO device2desk (DeskID, DeviceID) " +
                       "VALUES (?, ?);";

        Object[] data = {
            d.getDeskID(),
            d.getDeviceID()

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
     * Deletes the device identified by the provided group number.
     * @param deskID The desk ID for which the device is to be deleted.
     * @param deviceID The device ID for which the device is to be deleted.
     * @return True if the deletion was successful. Otherwise false is returned.
     */
    public boolean delete(int deskID, int deviceID) {


        String query2 = "DELETE FROM device2desk WHERE DeskID = ? AND DeviceID = ?;";
        Object[] data2 = { deskID, deviceID };

        try {
            this.db.executeQuery(query2, data2);
            return  true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Returns true if an device exists for the provided group number.
     * @param deskID The desk ID for which the device is to be checked.
     * @param deviceID The device ID for which the device is to be checked.
     * @return True if the device exists or an error has occurred. Otherwise false.
     */
    public boolean exists(int deskID, int deviceID) {
        String query = "SELECT COUNT(*) FROM device2desk WHERE DeskID = ? AND DeviceID = ?;";
        Object[] data = { deskID, deviceID };

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
     * Returns true if there is still room left for a binding between a device and a desk
     * Respects the limits of 2 lights and 1 sensor per desk.
     * @param deskID The desk ID for which is to be checked.
     * @param deviceID The device ID for which is to be checked.
     * @return True a device with the devicetype of deviceID can still be made
     */
    public boolean roomLeftForDevice(int deskID, int deviceID) {
        String query = "SELECT " +
                "COUNT(devices.DeviceType), devices.DeviceType " +
                "FROM device2desk, devices, devices as d2 " +
                "WHERE device2desk.DeskID = ? AND device2desk.DeviceID = devices.ID AND devices.DeviceType = d2.DeviceType AND d2.ID = ?;";
        Object[] data = { deskID, deviceID };

        try {
            RowConversionFunction<Integer[]> rowConversion = rs -> {
                return new Integer[]{rs.getInt(1), rs.getInt(2)};
            };

            Integer[] result = this.db.executeQuery(query, data, rowConversion).get(0);
            return (result[1] == 1 && result[0] < 2) || (result[1] == 0 && result[0] < 1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }


    /**
     * Retrieves all existing device
     * @return The collection of existing device.
     */
    public ArrayList<Device2Desk> getAll() {
        String query = "SELECT DeskID, DeviceID FROM device2desk";
        Object[] data = {};

        try {
            RowConversionFunction<Device2Desk> rowConversion = rs -> {
                return Device2Desk.Make(
                    rs.getInt("DeskID"),
                    rs.getInt("DeviceID")
                );
            };

            return this.db.executeQuery(query, data, rowConversion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }
}
