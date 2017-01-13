package iot.services;

import iot.Conversion;
import iot.domain.Desk;
import iot.domain.Device;
import iot.domain.UserAccount;

import javax.json.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Geert on 2017-01-10.
 */
public class JsonConverter {

    /**
     * Creates a JSON object representing an exception.
     * @param ex The exception that has occurred.
     * @return A JSON object containing a failure.
     */
    public static JsonObject Exception(Exception ex) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("successful", JsonValue.FALSE);
        builder.add("message", ex.getMessage());

        return builder.build();
    }

    /**
     * Creates a JSON object representing an internal exception.
     * @return A JSON object containing a general message for an interal exception.
     */
    public static JsonObject ExceptionInternal() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("successful", JsonValue.FALSE);
        builder.add("message", "An internal exception has occurred.");

        return builder.build();
    }

    /**
     * Creates a JSON object representing an internal exception.
     * @return A JSON object containing a general message for an interal exception.
     */
    public static JsonObject ExceptionInternal(Exception e) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("successful", JsonValue.FALSE);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exception = sw.toString(); // stack trace as a string

        builder.add("message", "An internal exception has occurred: " + exception);

        return builder.build();
    }

    /**
     * Creates a JSON object representing a success.
     * @param obj The object that is to be returned.
     * @return A JSON object containing a success.
     */
    public static JsonObject Success(JsonObject obj) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("successful", JsonValue.TRUE);
        builder.add("data", obj);

        return builder.build();
    }

    /**
     * Creates a JSON object representing a success.
     * @param array The array that is to be returned.
     * @return A JSON object containing a success.
     */
    public static JsonObject Success(JsonArray array) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("successful", JsonValue.TRUE);
        builder.add("data", array);

        return builder.build();
    }

    /**
     * Creates a JSON object representing a success.
     * @return An empty JSON object containing a success, but no data.
     */
    public static JsonObject Success() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("successful", JsonValue.TRUE);

        return builder.build();
    }

    /**
     * Converts the provided collection of elements to a JSON array by using the specified converion function.
     * @param collection The collection that is to be converted.
     * @param convert The function used to convert each element in the collection to its JSON object.
     * @param <T> The type of the elements in the collection.
     * @return A JSON array representing the provided collection.
     */
    public static <T> JsonArray toJsonArray(Collection<T> collection, Function<T, JsonObject> convert) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (T element: collection) {
            JsonObject obj = convert.apply(element);
            builder.add(obj);
        }

        return builder.build();
    }

    /**
     * Converts the provided JSON object to an UserAccount object.
     * @param userAccount The user account JSON that is to be converted.
     * @return a new UserAccount object.
     * @throws NoSuchElementException The provided JSON object is null.
     * @throws NoSuchFieldException One of the required fields is missing.
     */
    public static UserAccount toUserAccount(JsonObject userAccount)
            throws NoSuchElementException, NoSuchFieldException {

        if (userAccount == null)
            throw new NoSuchElementException("No user account data has been provided.");

        int groupNr = JsonConverter.getInt(userAccount, "groupNr");
        int roomNr = JsonConverter.getInt(userAccount, "roomNr");
        String firstName = JsonConverter.getString(userAccount, "firstName");
        Optional<String> prefix = JsonConverter.getStringOptional(userAccount, "prefix");
        String lastName = JsonConverter.getString(userAccount, "lastName");
        String email = JsonConverter.getString(userAccount, "email");
        String password = JsonConverter.getString(userAccount, "password");

        return UserAccount.Make(groupNr, roomNr, firstName, prefix, lastName, email, password);
    }

    /**
     * Converts the provided user account to its JSON object representation.
     * @param userAccount The user account that is to be converted.
     * @return The JSON representation of the provided object.
     */
    public static JsonObject toJson(UserAccount userAccount) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("groupNr", userAccount.getGroupNr());
        builder.add("roomNr", userAccount.getRoomNr());
        builder.add("firstName", userAccount.getFirstName());
        builder.add("prefix", userAccount.getPrefix().orElse(""));
        builder.add("lastName", userAccount.getLastName());
        builder.add("email", userAccount.getEmail());

        return builder.build();
    }

    /**
     * Converts the provided JSON object to an Device object.
     * @param device The device JSON that is to be converted.
     * @return a new UserAccount object.
     * @throws NoSuchElementException The provided JSON object is null.
     * @throws NoSuchFieldException One of the required fields is missing.
     */
    public static Device toDevice(JsonObject device)
            throws NoSuchElementException, NoSuchFieldException {

        if (device == null)
            throw new NoSuchElementException("No user account data has been provided.");

        int deviceID = JsonConverter.getInt(device, "deviceID");
        boolean type = JsonConverter.getBoolean(device, "deviceType");
        boolean state = JsonConverter.getBoolean(device, "state");
        int roomNr = JsonConverter.getInt(device, "roomNr");
        int locX = JsonConverter.getInt(device, "locX");
        int locY = JsonConverter.getInt(device, "locY");

        return Device.Make(deviceID, type, state, roomNr, locX, locY);
    }


    /**
     * Converts the provided device to its JSON object representation.
     * @param device The device that is to be converted.
     * @return The JSON representation of the provided object.
     */
    public static JsonObject toJson(Device device) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("deviceID", device.getDeviceID());
        builder.add("deviceType", device.getDeviceType());
        builder.add("state", device.getState());
        builder.add("roomNr", device.getRoomNr());
        builder.add("locX", device.getLocX());
        builder.add("locY", device.getLocY());

        return builder.build();
    }


    /**
     * Converts the provided JSON object to an Desk object.
     * @param desk The device JSON that is to be converted.
     * @return a new Desk object.
     * @throws NoSuchElementException The provided JSON object is null.
     * @throws NoSuchFieldException One of the required fields is missing.
     */
    public static Desk toDesk(JsonObject desk)
            throws NoSuchElementException, NoSuchFieldException {

        if (desk == null)
            throw new NoSuchElementException("No desk data has been provided.");

        int deskID = JsonConverter.getInt(desk, "deskID");
        int userID = JsonConverter.getInt(desk, "userID");
        int locX = JsonConverter.getInt(desk, "locX");
        int locY = JsonConverter.getInt(desk, "locY");

        return Desk.Make(deskID, userID, locX, locY);
    }


    /**
     * Converts the provided desk to its JSON object representation.
     * @param desk The desk that is to be converted.
     * @return The JSON representation of the provided object.
     */
    public static JsonObject toJson(Desk desk) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("deskID", desk.getDeskID());
        builder.add("userID", desk.getUserID());
        builder.add("locX", desk.getLocX());
        builder.add("locY", desk.getLocY());

        return builder.build();
    }


    /**
     * Tries to retrieve the specified field from the JSON object.
     * @param obj The JSON object from which the field value is to be retrieved.
     * @param name The name of the property on the JSON object.
     * @return The int value of the specified property on the provided JSON object.
     * @throws NoSuchFieldException The property does not exist on the provided JSON object.
     */
    private static int getInt(JsonObject obj, String name) throws NoSuchFieldException {
        if (!obj.containsKey(name))
        {
            throw new NoSuchFieldException(
                String.format("The field '%1s' is missing.", name));
        }
        if (obj.get(name).getValueType() != JsonValue.ValueType.NUMBER) {
            throw new NoSuchFieldException(
                String.format("The field '%1s' is not a number." + obj.toString() + " " + obj.get(name).getValueType(), name));
        }
        if (!obj.getJsonNumber(name).isIntegral())
        {
            throw new NoSuchFieldException(
                String.format("The field '%1s' is not an integer.", name));
        }

        return obj.getInt(name);
    }

    /**
     * Tries to retrieve the specified field from the JSON object.
     * @param obj The JSON object from which the field value is to be retrieved.
     * @param name The name of the property on the JSON object.
     * @return The string value of the specified property on the provided JSON object.
     * @throws NoSuchFieldException The property does not exist on the provided JSON object.
     */
    private static String getString(JsonObject obj, String name) throws NoSuchFieldException {
        Optional<JsonString> value = Optional.ofNullable(obj.getJsonString(name));

        if (!value.isPresent()) {
            throw new NoSuchFieldException(
                    String.format("The field '%1s' is missing.", name));
        }

        return value.get().getString();
    }

    /**
     * Tries to retrieve the specified field from the JSON object.
     * @param obj The JSON object from which the field value is to be retrieved.
     * @param name The name of the property on the JSON object.
     * @return The string value of the specified property on the provided JSON object.
     * @throws NoSuchFieldException The property does not exist on the provided JSON object.
     */
    private static boolean getBoolean(JsonObject obj, String name) throws NoSuchFieldException {

        if (!obj.containsKey(name))
        {
            throw new NoSuchFieldException(
                    String.format("The field '%1s' is missing.", name));
        }
        /*
        if (obj.get(name).getValueType() != JsonValue.ValueType.NUMBER) {
            throw new NoSuchFieldException(
                    String.format("The field '%1s' is not a boolean.", obj.getJsonString(name)));
        }
        if (!obj.getJsonNumber(name).isIntegral())
        {
            throw new NoSuchFieldException(
                    String.format("The field '%1s' is not an integer.", name));
        }
*/


        return obj.getBoolean(name);
    }


    /**
     * Tries to retrieve the specified field from the JSON object.
     * @param obj The JSON object from which the field value is to be retrieved.
     * @param name The name of the property on the JSON object.
     * @return The string value of the specified property on the provided JSON object. The optional is empty if the
     *         property is not defined or it is empty.
     */
    private static Optional<String> getStringOptional(JsonObject obj, String name) {
        Optional<JsonString> value = Optional.ofNullable(obj.getJsonString(name));

        return value
            .map(v -> v.getString())
            .filter(v -> !v.isEmpty() || v.trim().length() != 0);
    }
}
