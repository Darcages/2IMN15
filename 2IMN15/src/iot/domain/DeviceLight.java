package iot.domain;

import iot.Validation;

/**
 * Created by Geert on 2017-01-18.
 */
public class DeviceLight extends Device {

    private DeploymentType deployment;

    public DeploymentType getDeployment() {
        return this.deployment;
    }

    /**
     * Creates a new light device.
     * @param deviceID The unique identifier of the device.
     * @param state The state of the device.
     * @param roomNr The number of the room in which the device is located.
     * @param locX The X-coordinate of the device in the room.
     * @param locY The Y-coordinate of the device in the room.
     * @param deployment The type of deployment.
     * @return A new light device.
     */
    public static DeviceLight Make(
        int deviceID,
        boolean state,
        int roomNr,
        int locX,
        int locY,
        DeploymentType deployment
    ) {
        Validation.deviceID(deviceID);
        Validation.roomNr(roomNr);

        DeviceLight d = new DeviceLight();
        d.deviceID = deviceID;
        d.deviceType = true;
        d.state = state;
        d.roomNr = roomNr;
        d.locX = locX;
        d.locY = locY;
        d.deployment = deployment;

        return d;
    }
}
