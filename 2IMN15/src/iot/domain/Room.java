package iot.domain;

import iot.Validation;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Geert on 2017-01-14.
 */
public class Room {

    private int roomNr;
    private String hostname;
    private int port;

    private Room(int roomNr, String hostname, int port) {
        this.roomNr = roomNr;
        this.hostname = hostname;
        this.port = port;
    }

    public int getRoomNr() {
        return this.roomNr;
    }

    public String getHostname() {
        return this.hostname;
    }

    public InetAddress getAddress() throws UnknownHostException {
        return InetAddress.getByName(this.hostname);
    }

    public int getPort() {
        return this.port;
    }

    public static Room make(int roomNr, String hostname, int port) {
        Validation.roomNr(roomNr);
        Validation.NonEmptyString(hostname, "The host name cannot be empty.");
        Validation.PositiveInt(port, "The port cannot be a negative number.");

        return new Room(roomNr, hostname, port);
    }
}
