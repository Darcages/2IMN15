package iot.domain;

import iot.Validation;

/**
 * Created by Geert on 2017-01-18.
 */
public class DeviceSensor extends Device {

    /**
     * Creates a new sensor device.
     * @param deviceID The unique identifier of the device.
     * @param state The state of the device.
     * @param roomNr The number of the room in which the device is located.
     * @param locX The X-coordinate of the device in the room.
     * @param locY The Y-coordinate of the device in the room.
     * @return A new sensor device.
     */
    public static DeviceSensor Make(
        int deviceID,
        int state,
        int roomNr,
        int locX,
        int locY
    ) {
        Validation.deviceID(deviceID);
        Validation.roomNr(roomNr);

        DeviceSensor d = new DeviceSensor();
        d.deviceID = deviceID;
        d.deviceType = false;
        d.state = state;
        d.roomNr = roomNr;
        d.locX = locX;
        d.locY = locY;

        return d;
    }
}
