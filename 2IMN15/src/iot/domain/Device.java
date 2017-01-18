package iot.domain;

import iot.Validation;

import java.util.Optional;



public abstract class Device {
    protected int deviceID;
    protected boolean deviceType;
    protected int state;
    protected int roomNr;
    protected int locX;
    protected int locY;
    protected Optional<DeploymentType> deployment;

    public int getDeviceID() {
        return this.deviceID;
    }

    public boolean getDeviceType() { return this.deviceType; }

    public int getState() {
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
}
