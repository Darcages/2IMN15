package iot;

import java.util.regex.Pattern;

/**
 * Created by Geert on 2017-01-10.
 */
public class Validation {

    /**
     * Validates that the email address is correct.
     * @param email The email address that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the provided email address is invalid.
     */
    public static void emailAddress(String email) throws IllegalArgumentException {
        Validation.NonEmptyString(email, "The email address is empty.");

        if (!Pattern.matches("^\\w+@\\w+\\.\\w+$", email)) {
            throw new IllegalArgumentException(
                String.format("The email address '%1s' is incorrectly formatted.", email));
        }
    }

    /**
     * Validates that the group number is non-negative.
     * @param groupNr The group number that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the provided group number is negative.
     */
    public static void groupNr(int groupNr) throws IllegalArgumentException {
        if (groupNr < 1) {
            throw new IllegalArgumentException(
                String.format("No group with number '%s' exists.", groupNr));
        }
    }

    /**
     * Validates that the room number is non-negative.
     * @param roomNr The room number that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the provided room number is negative.
     */
    public static void roomNr(int roomNr) throws IllegalArgumentException {
        if (roomNr < 1) {
            throw new IllegalArgumentException(
                String.format("No room with number '%s' exists.", roomNr));
        }
    }

    /**
     * Validates that the first name is correct.
     * @param name The first name that is to be validated.
     * @exception IllegalArgumentException This exception is thrown if the provided first name is invalid.
     */
    public static void firstName(String name) {
        Validation.NonEmptyString(name,"The first name is empty.");
    }

    /**
     * Validates that the last name is correct.
     * @param name The last name that is to be validated.
     * @exception IllegalArgumentException This exception is thrown if the provided last name is invalid.
     */
    public static void lastName(String name) {
        Validation.NonEmptyString(name, "The last name is empty.");
    }

    /**
     * Validates the provided string value to be non-empty. A string only consisting of whitespaces is also considered
     * empty.
     * @param value The string value that is to be validated.
     * @param message The error message that thrown if the validation fails.
     * @exception IllegalArgumentException This exception is thrown if the provided string value is invalid.
     */
    public static void NonEmptyString(String value, String message) {
        if (value == null || value.isEmpty() || value.trim().length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates the provided string value to be non-empty. A string only consisting of whitespaces is also considered
     * empty.
     * @param value The string value that is to be validated.
     * @exception IllegalArgumentException This exception is thrown if the provided string value is invalid.
     */
    public static void NonEmptyString(String value) {
        Validation.NonEmptyString(value, "The value is empty.");
    }

    /**
     * Validates the length of the password.
     * @param password The password that is to be validated.
     * @exception  IllegalArgumentException This exception is thrown if the provided password is invalid.
     */
    public static void password(String password) {
        Validation.NonEmptyString(password, "The password cannot be empty.");
    }
}
