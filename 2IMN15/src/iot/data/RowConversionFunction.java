package iot.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Geert on 2017-01-11.
 */
@FunctionalInterface
public interface RowConversionFunction<R> {
    R apply(ResultSet rs) throws SQLException;
}
