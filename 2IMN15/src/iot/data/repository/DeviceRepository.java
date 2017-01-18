package iot.data.repository;

import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.*;

import java.sql.SQLException;
import java.util.ArrayList;


public class DeviceRepository {

    private Database db;

    /**
     * Creates a new instance of the DeviceRepository class.
     * @param db The database used to make a connection.
     */
    public DeviceRepository(Database db) {
        this.db = db;
    }

    /**
     * Creates the provided device.
     * @param d The device that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(Device d) {
        String query = "INSERT INTO devices (ID, DeviceType, State, RoomNr, LocX, LocY, Deployment) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?);";

        ArrayList<Object> data = new ArrayList<>();
        data.add(d.getDeviceID());
        data.add(d.getDeviceType() ? "1" : "0");
        data.add(d.getState() ? "1" : "0");
        data.add(d.getRoomNr());
        data.add(d.getLocX());
        data.add(d.getLocY());

        if (d instanceof DeviceLight) {
            DeviceLight dl = (DeviceLight)d;

            data.add(dl.getDeployment().getType());
        } else {
            data.add(null);
        }

        try {
            this.db.executeQuery(query, data.toArray());
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
        String query = "DELETE FROM devices WHERE ID = ?;";
        Object[] data = { id };

        try {
            this.db.executeQuery(query, data);
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
        String query = "SELECT COUNT(*) FROM devices WHERE ID = ?;";
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
    public ArrayList<Device> getAll() {
        String query = "SELECT ID, DeviceType, State, RoomNr, LocX, LocY, Deployment FROM devices";
        Object[] data = {};

        try {
            RowConversionFunction<Device> rowConversion = rs -> {
                boolean type = rs.getBoolean("DeviceType");

                if (type) {
                    return DeviceLight.Make(
                        rs.getInt("ID"),
                        rs.getBoolean("State"),
                        rs.getInt("RoomNr"),
                        rs.getInt("LocX"),
                        rs.getInt("LocY"),
                        DeploymentType.Parse(rs.getInt("Deployment"))
                    );
                } else {
                    return DeviceSensor.Make(
                        rs.getInt("ID"),
                        rs.getBoolean("State"),
                        rs.getInt("RoomNr"),
                        rs.getInt("LocX"),
                        rs.getInt("LocY")
                    );
                }
            };

            return this.db.executeQuery(query, data, rowConversion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Updates the state of a device.
     * @param id The device that is to be updated.
     * @param newState The new state of the device
     * @return True if the update succeeded, otherwise false.
     */
    public boolean updateState(int id, boolean newState) {
        String query = "UPDATE devices " +
                "SET State = ?" +
                "WHERE ID = ?;";

        Object[] data = {
                newState,
                id
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
     * Updates the deployment of a light device.
     * @param id The device that is to be updated.
     * @param deployment The new deployment type of the light device.
     * @return True if the update succeeded, otherwise false.
     */
    public boolean updateDeployment(int id, DeploymentType deployment) {
        String query = "UPDATE devices " +
                "SET Deployment = ? " +
                "WHERE ID = ? AND DeviceType = 1;";

        Object[] data = {
                deployment.getType(),
                id
        };

        try {
            this.db.executeQuery(query, data);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
