import iot.services.DeskService;
import iot.services.Device2DeskService;
import iot.services.DeviceService;
import iot.services.UserAccountService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Declares the root resource and provider classes.
 */
@ApplicationPath("/services")
public class Services extends Application {

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

        return classes;
    }
}