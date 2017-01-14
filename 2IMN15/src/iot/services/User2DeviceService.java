package iot.services;

import iot.data.Database;
import iot.data.repository.User2DeviceRepository;
import iot.data.repository.UserAccountRepository;
import iot.domain.User2Device;
import iot.domain.UserAccount;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.NoSuchElementException;


@Path("/User2DeviceService")
public class User2DeviceService {

    private User2DeviceRepository repos;

    public User2DeviceService() {
        try {
            this.repos = new User2DeviceRepository(Database.load());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            ArrayList<User2Device> accounts = this.repos.getAll();

            JsonArray array = JsonConverter.toJsonArray(accounts, ua -> JsonConverter.toJson(ua));
            return JsonConverter.Success(array);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(JsonObject user2device) {
        try {
            User2Device ua = JsonConverter.toUser2Device(user2device);

            if (this.repos.exists(ua.getUserID(), ua.getDeviceID())) {
                throw new IllegalArgumentException(
                    String.format(
                        "There already exists a binding between user account with ID '%1s' and device with ID '%1s'.",
                        ua.getUserID(), ua.getDeviceID()));
            }

            this.repos.create(ua);

            return JsonConverter.Success(JsonConverter.toJson(ua));
        }
        catch (IllegalArgumentException | NoSuchFieldException | NoSuchElementException ex)
        {
            return JsonConverter.Exception(ex);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject delete(@QueryParam("userID") int userID, @QueryParam("deviceID") int deviceID) {
        try {
            this.repos.delete(userID, deviceID);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }
}
