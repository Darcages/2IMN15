package iot.domain;

import iot.Validation;

import java.util.Date;


public class Event {
    private Date timestamp;
    private int deviceID;
    private int userID;
    private boolean newState;


    /**
     * Initializes a new instance of the Event class.
     */
    private Event() { }

    public Date getTimestamp() {
        return this.timestamp;
    }
    public int getDeviceID() {
        return this.deviceID;
    }
    public int getUserID() { return this.userID; }
    public boolean getNewState() { return this.newState; }


    /**
     * Creates a new instance of the Event class.
     * @param timestamp .
     * @param deviceID .
     * @param userID .
     * @param newState .
     * @return A new instance of the Event class with the provided data.
     * @exception IllegalArgumentException This exception is thrown if one or more of the provided parameters is
     *                                     invalid.
     */
    public static Event Make(
            Date timestamp,
            int deviceID,
            int userID,
            boolean newState
    ) {
        Validation.deviceID(deviceID);

        Event e = new Event();
        e.timestamp = timestamp;
        e.deviceID = deviceID;
        e.userID = userID;
        e.newState = newState;

        return e;
    }
}
