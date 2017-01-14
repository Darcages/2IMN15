package iot.domain;

import iot.Validation;




public class Device2Desk {
    private int deskID;
    private int deviceID;


    /**
     * Initializes a new instance of the UserAccount class.
     */
    private Device2Desk() { }

    public int getDeskID() {
        return this.deskID;
    }
    public int getDeviceID() {
        return this.deviceID;
    }


    /**
     * Creates a new instance of the Device2Desk.
     * @param deskID The desk id.
     * @param deviceID The device id.
     * @return A new instance of the Device class with the provided data.
     * @exception IllegalArgumentException This exception is thrown if one or more of the provided parameters is
     *                                     invalid.
     */
    public static Device2Desk Make(
            int deskID,
            int deviceID
    ) {
        Validation.deviceID(deskID);

        Device2Desk d2d = new Device2Desk();
        d2d.deskID = deskID;
        d2d.deviceID = deviceID;

        return d2d;
    }
}
