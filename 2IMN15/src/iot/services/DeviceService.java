package iot.services;

import iot.data.Database;
import iot.data.repository.DeviceRepository;
import iot.data.repository.EventRepository;
import iot.domain.DeploymentType;
import iot.domain.Device;
import iot.domain.Event;
import jdk.nashorn.internal.parser.JSONParser;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;


@Path("/DeviceService")
public class DeviceService {

    private DeviceRepository repos;

    public DeviceService() {
        try {
            this.repos = new DeviceRepository(Database.load());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            ArrayList<Device> devices = this.repos.getAll();

            JsonArray array = JsonConverter.toJsonArray(devices, d -> JsonConverter.toJson(d));
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
    public JsonObject create(JsonObject device) {
        try {
            Device d = JsonConverter.toDevice(device);

            if (this.repos.exists(d.getDeviceID())) {
                throw new IllegalArgumentException(
                    String.format(
                        "There already exists an device with ID '%1s'.",
                        d.getDeviceID()));
            }

            this.repos.create(d);

            return JsonConverter.Success(JsonConverter.toJson(d));
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
    public JsonObject delete(@QueryParam("deviceID") int deviceID) {
        try {
            this.repos.delete(deviceID);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }

    @POST
    @Path("/updateState")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject updateState(@QueryParam("deviceID") int deviceID, @QueryParam("newState") boolean newState, @QueryParam("userID") int userID){
        try {
            this.repos.updateState(deviceID, newState);
            Event e = Event.Make(new Date(), deviceID, userID, newState);
            new EventRepository(Database.load()).create(e);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }

    @POST
    @Path("/updateDeployment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject updateDeployment(JsonObject data) {
        try {
            int id = JsonConverter.getInt(data,  "deviceID");
            int type = JsonConverter.getInt(data, "deployment");

            DeploymentType deployment = DeploymentType.Parse(type);

            if (deployment == DeploymentType.Unknown) {
                throw new IllegalArgumentException(String.format(
                        "The type '%s' is an unknown deployment type.",
                        type
                ));
            }

            this.repos.updateDeployment(id, deployment);
            return JsonConverter.Success();
        }
        catch (IllegalArgumentException | NoSuchElementException ex)
        {
            return JsonConverter.Exception(ex);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }
}
