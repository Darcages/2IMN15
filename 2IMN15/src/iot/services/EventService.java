package iot.services;

import iot.data.Database;
import iot.data.repository.DeviceRepository;
import iot.data.repository.EventRepository;
import iot.domain.Device;
import iot.domain.Event;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;


@Path("/EventService")
public class EventService {

    private EventRepository repos;

    public EventService() {
        try {
            this.repos = new EventRepository(Database.load());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            ArrayList<Event> events = this.repos.getAll();

            JsonArray array = JsonConverter.toJsonArray(events, e -> JsonConverter.toJson(e));
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
    public JsonObject create(JsonObject event) {
        try {
            Event e = JsonConverter.toEvent(event);

            if (this.repos.exists(e.getTimestamp(), e.getDeviceID())) {
                throw new IllegalArgumentException(
                    String.format(
                        "There already exists an event with ID '%1s'.",
                        e.getDeviceID()));
            }

            this.repos.create(e);

            return JsonConverter.Success(JsonConverter.toJson(e));
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

}
