package iot.domain;

import iot.Validation;


public class User2Device {
    private int userID;
    private int deviceID;
    private int prioLevel;
    private int red;
    private int green;
    private int blue;
    private boolean lowLight;


    /**
     * Initializes a new instance of the UserAccount class.
     */
    private User2Device() { }

    public int getUserID() {
        return this.userID;
    }
    public int getDeviceID() {
        return this.deviceID;
    }
    public int getPrioLevel() {
        return this.prioLevel;
    }
    public int getRed() {
        return this.red;
    }
    public int getGreen() {
        return this.green;
    }
    public int getBlue() {
        return this.blue;
    }
    public boolean getLowLight() {
        return this.lowLight;
    }




    public static User2Device Make(
            int userID,
            int deviceID,
            int prioLevel,
            int red,
            int green,
            int blue,
            boolean lowLight

    ) {
        Validation.userExist(userID);
        Validation.deviceID(deviceID);
        Validation.priolevel(prioLevel);
        Validation.color(red);
        Validation.color(green);
        Validation.color(blue);


        User2Device u2d = new User2Device();
        u2d.userID = userID;
        u2d.deviceID = deviceID;
        u2d.prioLevel = prioLevel;
        u2d.red = red;
        u2d.green = green;
        u2d.blue = blue;
        u2d.lowLight = lowLight;


        return u2d;
    }
}
