package iot.dns;

import iot.data.repository.RoomRepository;
import iot.domain.Room;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.net.InetAddress;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Geert on 2017-01-11.
 */
public class RoomListener implements ServiceListener {

    private RoomRepository repos;

    public RoomListener() {
        this.repos = new RoomRepository();
    }

    /**
     * A service has been added.
     * @param serviceEvent The ServiceEvent providing the name and fully qualified type of the service.
     */
    @Override
    public void serviceAdded(ServiceEvent serviceEvent) {
        Optional<Integer> roomNr = this.tryParseRoomNumber(serviceEvent);

        if (roomNr.isPresent()) {
            System.out.println(String.format(
                    "New broker service '%1s' discovered. Trying to request additional information.",
                    serviceEvent.getName()));

            // Request the service info such that the IP address and port can be determined.
            serviceEvent
                .getDNS()
                .requestServiceInfo(serviceEvent.getType(), serviceEvent.getName(), 10000);
        } else {
            System.out.println(String.format(
                    "New unknown service '%1s' discovered. It is ignored.",
                    serviceEvent.getName()));
        }
    }

    /**
     * A service has been removed.
     * @param serviceEvent The ServiceEvent providing the name and fully qualified type of the service.
     */
    @Override
    public void serviceRemoved(ServiceEvent serviceEvent) {
        Optional<Integer> roomNr = this.tryParseRoomNumber(serviceEvent);

        if (roomNr.isPresent()) {
            System.out.println(String.format(
                    "Connection lost to broker service '%1s'. The corresponding room is being removed.",
                    serviceEvent.getName()));

            boolean success = this.repos.delete(roomNr.get());

            if (success) {
                System.out.println(String.format("Successfully deleted room '%s'.", roomNr.get()));
            } else {
                System.out.print(String.format("Failed to delete room '%s'.", roomNr.get()));
            }
        } else {
            System.out.println(String.format(
                    "Connection lost to unknown service '%1s'.",
                    serviceEvent.getName()));
        }
    }

    /**
     * A service has been resolved after requesting information about a service.
     * @param serviceEvent The ServiceEvent providing the name and fully qualified type of the service.
     */
    @Override
    public void serviceResolved(ServiceEvent serviceEvent) {
        Optional<Integer> roomNr = this.tryParseRoomNumber(serviceEvent);

        if (roomNr.isPresent()) {
            System.out.println(String.format(
                    "Resolved broker service '%1s'. Creating the corresponding room.",
                    serviceEvent.getName()));

            InetAddress[] addresses = serviceEvent.getInfo().getInetAddresses();

            if (addresses.length != 1) {
                System.out.println("Multiple IP addresses have been found:");

                for (InetAddress address : addresses) {
                    System.out.println("  - " + address.getHostAddress());
                }

                System.out.println("Aborting.");
                return;
            }

            String hostname = addresses[0].getHostName();
            int port = serviceEvent.getInfo().getPort();

            Room room = Room.make(roomNr.get(), hostname, port);

            if (this.repos.tryGet(room.getRoomNr()).isPresent()) {
                boolean success = this.repos.update(room);

                if (success) {
                    System.out.println(String.format("Successfully updated the room '%s'.", room.getRoomNr()));
                } else {
                    System.out.println(String.format("Failed to update the room '%s'.", room.getRoomNr()));
                }
            } else {
                boolean success = this.repos.create(room);

                if (success) {
                    System.out.println(String.format("Successfully created the room '%s'.", room.getRoomNr()));
                } else {
                    System.out.println(String.format("Failed to create the room '%s'.", room.getRoomNr()));
                }
            }
        } else {
            System.out.println(String.format(
                    "Resolved unknown service '%1s'.",
                    serviceEvent.getName()));
        }
    }

    /**
     * Tries to parse the room number from the given service event.
     * @param serviceEvent The service event from which te room number is to be parsed.
     * @return The number of the room to which the service event belongs or an empty value if the event did not belong
     *         to a room.
     */
    private Optional<Integer> tryParseRoomNumber(ServiceEvent serviceEvent) {
        Matcher matcher = Pattern
                .compile("^Broker-Room-(\\d{1,2})$")
                .matcher(serviceEvent.getName());

        if (matcher.matches() && matcher.groupCount() == 1) {
            int roomNr = Integer.parseInt(matcher.group(1));
            return Optional.of(roomNr);
        }

        return Optional.empty();
    }

    private Room GetRoom(ServiceEvent serviceEvent) {
        ServiceInfo info = serviceEvent.getDNS().getServiceInfo(serviceEvent.getType(), serviceEvent.getName());

        return null;
    }
}
