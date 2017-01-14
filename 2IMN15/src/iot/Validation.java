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
     * Validates that the user exists/
     * @param userID The device that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the provided room number is negative.
     */
    public static void userExist(int userID) throws IllegalArgumentException {
        if (userID < 1) {
            throw new IllegalArgumentException(
                String.format("No user with id '%s' exists.", userID));
        }
        //TODO actuall checking whether or not user exists

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


    /**
     * Validates that the room number is non-negative.
     * @param deviceID The room number that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the provided room number is negative.
     */
    public static void deviceID(int deviceID) throws IllegalArgumentException {
        if (deviceID < 1) {
            throw new IllegalArgumentException(
                    String.format("No deviceID with number '%s' exists.", deviceID));
        }
    }

    /**
     * Validates that the color is withing range
     * @param c The color that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the color is not within bounds
     */
    public static void color(int c) throws IllegalArgumentException {
        if (c < 0 || c > 255) {
            throw new IllegalArgumentException(
                    "A color has to be withing the range 0-255");
        }
    }

    /**
     * Validates that the priority level is withing range
     * @param p The level that is to be validated.
     * @throws IllegalArgumentException This exception is thrown if the prio level is not within bounds
     */
    public static void priolevel(int p) throws IllegalArgumentException {
        if (p < 1 || p > 3) {
            throw new IllegalArgumentException(
                    "A priority level has to be withing the range 1-3");
        }
    }


}
