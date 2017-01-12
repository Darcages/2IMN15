package iot.domain;

import iot.Validation;

import java.util.Optional;



public class Device {
    private int deviceID;
    private boolean deviceType;
    private boolean state;
    private int roomNr;
    private int locX;
    private int locY;


    /**
     * Initializes a new instance of the UserAccount class.
     */
    private Device() { }

    public int getDeviceID() {
        return this.deviceID;
    }

    public boolean getDeviceType() { return this.deviceType; }

    public boolean getState() {
        return this.state;
    }

    public int getRoomNr() {
        return this.roomNr;
    }

    public int getLocX() {
        return this.locX;
    }

    public int getLocY() {
        return this.locY;
    }

    /**
     * Creates a new instance of the UserAccount class.
     * @param deviceID The device id.
     * @param deviceType Light or Sensor
     * @param state For light On (=true) or Off (=false), for sensor Occupied (=true) or free (=false)
     * @param roomNr The number of the room.
     * @return A new instance of the Device class with the provided data.
     * @exception IllegalArgumentException This exception is thrown if one or more of the provided parameters is
     *                                     invalid.
     */
    public static Device Make(
            int deviceID,
            boolean deviceType,
            boolean state,
            int roomNr,
            int locX,
            int locY
    ) {
        Validation.deviceID(deviceID);
        Validation.roomNr(roomNr);

        Device d = new Device();
        d.deviceID = deviceID;
        d.deviceType = deviceType;
        d.state = state;
        d.roomNr = roomNr;
        d.locX = locX;
        d.locY = locY;

        return d;
    }
}
