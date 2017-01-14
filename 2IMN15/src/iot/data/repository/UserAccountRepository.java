package iot.data.repository;

import iot.Conversion;
import iot.data.Database;
import iot.data.RowConversionFunction;
import iot.domain.UserAccount;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Geert on 2017-01-11.
 */
public class UserAccountRepository {

    private Database db;

    /**
     * Creates a new instance of the UserAccountRepository class.
     */
    public UserAccountRepository() {
        try {
            this.db = Database.load();
        } catch (IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the provided user account.
     * @param ua The user account that is to be created.
     * @return True if the creation succeeded, otherwise false.
     */
    public boolean create(UserAccount ua) {
        String query = "INSERT INTO useraccount (GroupNr, RoomNr, FirstName, Prefix, LastName, Email, Password) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?);";

        Object[] data = {
            ua.getGroupNr(),
            ua.getRoomNr(),
            ua.getFirstName(),
            ua.getPrefix().orElse(""),
            ua.getLastName(),
            ua.getEmail(),
            ua.getPassword()
        };

        try {
            this.db.executeQuery(query, data);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Deletes the user account identified by the provided group number.
     * @param groupNr The group number for which the user account is to be deleted.
     * @return True if the deletion was successful. Otherwise false is returned.
     */
    public boolean delete(int groupNr) {
        String query = "DELETE FROM useraccount WHERE GroupNr = ?;";
        Object[] data = { groupNr };

        try {
            this.db.executeQuery(query, data);
            return  true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Returns true if an user account exists for the provided group number.
     * @param groupNr The group number for which the user account is to be checked.
     * @return True if the user account exists or an error has occurred. Otherwise false.
     */
    public boolean exists(int groupNr) {
        String query = "SELECT COUNT(*) FROM useraccount WHERE GroupNr = ?;";
        Object[] data = { groupNr };

        try {
            RowConversionFunction<Integer> rowConversion = rs -> {
              return rs.getInt(1);
            };

            return this.db.executeQuery(query, data, rowConversion).get(0) == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    /**
     * Retrieves all existing user accounts.
     * @return The collection of existing user accounts.
     */
    public ArrayList<UserAccount> getAll() {
        String query = "SELECT GroupNr, RoomNr, FirstName, Prefix, LastName, Email, Password FROM useraccount";
        Object[] data = {};

        try {
            RowConversionFunction<UserAccount> rowConversion = rs -> {
                return UserAccount.Make(
                    rs.getInt("GroupNr"),
                    rs.getInt("RoomNr"),
                    rs.getString("FirstName"),
                    Conversion.toOptional(rs.getString("Prefix")),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Password")
                );
            };

            return this.db.executeQuery(query, data, rowConversion);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }
}
