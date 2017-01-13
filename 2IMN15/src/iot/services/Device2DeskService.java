package iot.services;

import iot.data.Database;
import iot.data.repository.DeskRepository;
import iot.data.repository.Device2DeskRepository;
import iot.domain.Desk;
import iot.domain.Device2Desk;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.NoSuchElementException;


@Path("/Device2DeskService")
public class Device2DeskService {

    private Device2DeskRepository repos;

    public Device2DeskService() {
        try {
            this.repos = new Device2DeskRepository(Database.load());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            ArrayList<Device2Desk> accounts = this.repos.getAll();

            JsonArray array = JsonConverter.toJsonArray(accounts, d2d -> JsonConverter.toJson(d2d));
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
    public JsonObject create(JsonObject desk) {
        try {
            Device2Desk d2d = JsonConverter.toDevice2Desk(desk);

            if (this.repos.exists(d2d.getDeskID(), d2d.getDeviceID())) {
                throw new IllegalArgumentException(
                    String.format(
                        "There already exists an desk with ID '%1s' in combination with device ID '%1s'.",
                        d2d.getDeskID(), d2d.getDeviceID()));
            }

            this.repos.create(d2d);

            return JsonConverter.Success(JsonConverter.toJson(d2d));
        }
        catch (IllegalArgumentException | NoSuchFieldException | NoSuchElementException ex)
        {
            return JsonConverter.Exception(ex);
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
            return JsonConverter.ExceptionInternal(ex);
        }
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject delete(@QueryParam("deskID") int deskID, @QueryParam("deviceID") int deviceID) {
        try {
            this.repos.delete(deskID, deviceID);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }
}
