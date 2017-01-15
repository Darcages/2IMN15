package iot.data.repository;

import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.Event;
import iot.domain.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


public class EventRepository {

    private Database db;

    public EventRepository(Database db) {
        this.db = db;
    }

    /**
     * Creates the provided event.
     * @param event The event that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(Event event) {
        String query = "INSERT INTO events (timestamp, deviceID, userID, newState) " +
                "VALUES (?, ?, ?, ?);";

        Object[] data = {
            event.getTimestamp(),
            event.getDeviceID(),
            event.getUserID(),
            event.getNewState()
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
     * Returns true if an event exists for the provided timestamp and deviceid.
     * @param deviceID The device ID for which the device is to be checked.
     * @return True if the device exists or an error has occurred. Otherwise false.
     */
    public boolean exists(Date timestamp, int deviceID) {
        String query = "SELECT COUNT(*) FROM events WHERE timestamp = ? AND deviceID = ?;";
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] data = { pattern.format(timestamp), deviceID };

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
    public ArrayList<Event> getAll() {
        String query = "SELECT timestamp, deviceID, userID, newState FROM events";
        Object[] data = {};

        try {
            RowConversionFunction<Event> rowConversion = rs -> {
                return Event.Make(
                        rs.getDate("timestamp"),
                        rs.getInt("deviceID"),
                        rs.getInt("userID"),
                        rs.getBoolean("newState")
                );
            };

            return this.db.executeQuery(query, data, rowConversion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }
}
