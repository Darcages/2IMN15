import iot.data.repository.UserAccountRepository;
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

        return classes;
    }
}