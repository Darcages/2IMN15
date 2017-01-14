import iot.dns.BrokerDiscovery;
import iot.domain.User2Device;
import iot.services.*;

import javax.jmdns.ServiceInfo;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Declares the root resource and provider classes.
 */
@ApplicationPath("/services")
public class Services extends Application {

    private static final BrokerDiscovery discovery = BrokerDiscovery.start();

    /**
     * The method returns a non-empty collection with classes, that must be included in the published JAX-RS
     * application.
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet classes = new HashSet<Class<?>>();
        classes.add(UserAccountService.class);
        classes.add(DeviceService.class);
        classes.add(DeskService.class);
        classes.add(Device2DeskService.class);
        classes.add(User2DeviceService.class);

        return classes;
    }
}