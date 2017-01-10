package iot.domain;

import iot.Validation;

import javax.json.JsonObject;
import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Geert on 2017-01-10.
 */
public class UserAccount {
    private int groupNr;
    private int roomNr;
    private String firstName;
    private Optional<String> prefix;
    private String lastName;
    private String email;
    private String password;

    /**
     * Initializes a new instance of the UserAccount class.
     */
    private UserAccount() { }

    public int getGroupNr() {
        return this.groupNr;
    }

    public int getRoomNr() { return this.roomNr; }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Optional<String> getPrefix() {
        return this.prefix;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserId() {
        return String.format("Office-Worker-%s", this.groupNr);
    }

    /**
     * Creates a new instance of the UserAccount class.
     * @param groupNr The group number.
     * @param roomNr The number of the room.
     * @param firstName The first name.
     * @param prefix The prefix.
     * @param lastName The last name.
     * @param email The email address.
     * @param password The password.
     * @return A new instance of the UserAccount class with the provided data.
     * @exception IllegalArgumentException This exception is thrown if one or more of the provided parameters is
     *                                     invalid.
     */
    public static UserAccount Make(
            int groupNr,
            int roomNr,
            String firstName,
            Optional<String> prefix,
            String lastName,
            String email,
            String password
    ) {
        Validation.groupNr(groupNr);
        Validation.roomNr(roomNr);
        Validation.firstName(firstName);
        Validation.lastName(lastName);
        Validation.emailAddress(email);
        Validation.password(password);

        if (prefix.isPresent())
            Validation.NonEmptyString(prefix.get(), "The prefix cannot be empty if provided.");

        UserAccount ua = new UserAccount();
        ua.groupNr = groupNr;
        ua.roomNr = roomNr;
        ua.firstName = firstName;
        ua.prefix = prefix;
        ua.lastName = lastName;
        ua.email = email;
        ua.password = password;

        return ua;
    }
}
