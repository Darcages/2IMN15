package iot.dns;

import resources.ApplicationProperties;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Geert on 2017-01-11.
 */
public class BrokerDiscovery {

    private static final String PropertiesFile = "discovery.properties";

    private final JmDNS dns;
    private final RoomListener listener;
    private final String serviceType;

    private BrokerDiscovery(String serviceType, JmDNS dns) {
        this.serviceType = serviceType;
        this.dns = dns;
        this.listener = new RoomListener();

        this.dns.registerServiceType(this.serviceType);
        this.dns.addServiceListener(this.serviceType, this.listener);
    }

    /**
     * Starts the broker discovery.
     * @return The newly instantiated broker discovery object.
     */
    public static BrokerDiscovery start() {
        try {
            ApplicationProperties props = ApplicationProperties.open(PropertiesFile);
            String serviceType = props.readString("type");

            JmDNS dns = JmDNS.create(InetAddress.getLocalHost());

            return new BrokerDiscovery(serviceType, dns);
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Stops the broker discovery.
     */
    public void stop() {
        try {
            if (this.dns != null) {
                if (this.listener != null) {
                    this.dns.removeServiceListener(this.serviceType, this.listener);
                }

                this.dns.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
