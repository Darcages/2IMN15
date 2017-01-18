package iot.domain;

import iot.Validation;

import java.util.Date;


public class Event {
    private Date timestamp;
    private int deviceID;
    private int userID;
    private int newState; //0 = off, 1 = 0n, 2 = dimmed on
    private int userType;


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
    public int getNewState() { return this.newState; }
    public int getUserType() { return this.userType; }


    /**
     * Creates a new instance of the Event class.
     * @param timestamp .
     * @param deviceID .
     * @param userID .
     * @param newState .
     * @param userType .
     * @return A new instance of the Event class with the provided data.
     * @exception IllegalArgumentException This exception is thrown if one or more of the provided parameters is
     *                                     invalid.
     */
    public static Event Make(
            Date timestamp,
            int deviceID,
            int userID,
            int newState,
            int userType
    ) {
        Validation.deviceID(deviceID);

        Event e = new Event();


        e.timestamp = timestamp;
        e.deviceID = deviceID;
        e.userID = userID;
        e.newState = newState;
        e.userType = userType;

        return e;
    }
}
