package iot.data.repository;

import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Geert on 2017-01-11.
 */
public class RoomRepository {

    private Database db;

    public RoomRepository() {
        try {
            this.db = Database.load();
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the provided room.
     * @param room The room that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(Room room) {
        String query = "INSERT INTO room (RoomNr, Hostname, Port) " +
                "VALUES (?, ?, ?);";

        Object[] data = {
            room.getRoomNr(),
            room.getHostname(),
            room.getPort()
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
     * Updates the provided room.
     * @param room The room that is to be updated.
     * @return True if the update succeeded, otherwise false.
     */
    public boolean update(Room room) {
        String query = "UPDATE room " +
                "SET Hostname = ?, Port = ? " +
                "WHERE RoomNr = ?;";

        Object[] data = {
            room.getHostname(),
            room.getPort(),
            room.getRoomNr()
        };

        try {
            this.db.executeQuery(query, data);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Optional<Room> tryGet(int roomNr) {

        String query = "SELECT RoomNr, Hostname, Port FROM room WHERE RoomNr = ?;";
        Object[] data = { roomNr };

        try {
            RowConversionFunction<Room> rowConversion = rs -> {
                return Room.make(
                    rs.getInt("RoomNr"),
                    rs.getString("Hostname"),
                    rs.getInt("Port")
                );
            };

            ArrayList<Room> rooms = this.db.executeQuery(query, data, rowConversion);

            if (rooms.size() == 1) {
                return Optional.of(rooms.get(0));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    public ArrayList<Integer> getAllNrs() {
        String query = "SELECT RoomNr FROM room;";
        Object[] data = { };

        try {
            return this.db.executeQuery(query, data, rs -> rs.getInt("RoomNr"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Deletes the room identified by the provided room number.
     * @param roomNr The room number for which the room is to be deleted.
     * @return True if the deletion was successful. Otherwise false is returned.
     */
    public boolean delete(int roomNr) {
        String query = "DELETE FROM room WHERE RoomNr = ?;";
        Object[] data = { roomNr };

        try {
            this.db.executeQuery(query, data);
            return  true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
