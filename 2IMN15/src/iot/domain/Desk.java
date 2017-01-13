package iot.domain;

import iot.Validation;


public class Desk {
    private int deskID;
    private int userID;
    private int locX;
    private int locY;


    /**
     * Initializes a new instance of the UserAccount class.
     */
    private Desk() { }

    public int getDeskID() {
        return this.deskID;
    }
    public int getUserID() {
        return this.userID;
    }
    public int getLocX() {
        return this.locX;
    }
    public int getLocY() {
        return this.locY;
    }


    /**
     * Creates a new instance of the Desk class.
     * @param deskID The desk id.
     * @return A new instance of the Device class with the provided data.
     * @exception IllegalArgumentException This exception is thrown if one or more of the provided parameters is
     *                                     invalid.
     */
    public static Desk Make(
            int deskID,
            int userID,
            int locX,
            int locY
    ) {
        Validation.deviceID(deskID);

        Desk dg = new Desk();
        dg.deskID = deskID;
        dg.userID = userID;
        dg.locX = locX;
        dg.locY = locY;

        return dg;
    }
}
