package iot.data.repository;

import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.Device;
import iot.domain.Desk;

import java.sql.SQLException;
import java.util.ArrayList;


public class DeskRepository {

    private Database db;

    /**
     * Creates a new instance of the DeviceRepository class.
     * @param db The database used to make a connection.
     */
    public DeskRepository(Database db) {
        this.db = db;
    }

    /**
     * Creates the provided device.
     * @param d The device that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(Desk d) {
        String query = "INSERT INTO desks (ID, UserID, LocX, LocY) " +
                       "VALUES (?, ?, ?, ?);";

        Object[] data = {
            d.getDeskID(),
            d.getUserID(),
            d.getLocX(),
            d.getLocY()

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
     * @param id The device ID for which the device is to be deleted.
     * @return True if the deletion was successful. Otherwise false is returned.
     */
    public boolean delete(int id) {


        String query2 = "DELETE FROM desks WHERE ID = ?;";
        Object[] data2 = { id };

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
     * @param id The device ID for which the device is to be checked.
     * @return True if the device exists or an error has occurred. Otherwise false.
     */
    public boolean exists(int id) {
        String query = "SELECT COUNT(*) FROM desks WHERE ID = ?;";
        Object[] data = { id };

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
     * Retrieves all existing device
     * @return The collection of existing device.
     */
    public ArrayList<Desk> getAll() {
        String query = "SELECT ID, UserID, LocX, LocY FROM desks";
        Object[] data = {};

        try {
            RowConversionFunction<Desk> rowConversion = rs -> {
                return Desk.Make(
                    rs.getInt("ID"),
                    rs.getInt("UserID"),
                    rs.getInt("LocX"),
                    rs.getInt("LocY")
                );
            };

            return this.db.executeQuery(query, data, rowConversion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }
}
