package iot;

import java.util.Optional;

/**
 * Created by Geert on 2017-01-10.
 */
public class Conversion {

    /**
     * Wrappes the provided value in an optional. If the value is null, empty, or consists solely of white spaces, then
     * the optional is empty.
     * @param value The value that is to be wrapped.
     * @return An empty optional if the provided value is null, empty, or consists solely of white spaces. Otherwise the
     *         provided value is included in the returned optional.
     */
    public static Optional<String> toOptional(String value) {
        try
        {
            Validation.NonEmptyString(value);
            return Optional.of(value);
        }
        catch (IllegalArgumentException ex)
        {
            return Optional.empty();
        }
    }
}
