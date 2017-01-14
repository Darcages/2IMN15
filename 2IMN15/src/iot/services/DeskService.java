package iot.services;

import iot.data.Database;
import iot.data.repository.DeskRepository;
import iot.domain.Desk;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.NoSuchElementException;


@Path("/DeskService")
public class DeskService {

    private DeskRepository repos;

    public DeskService() {
        try {
            this.repos = new DeskRepository(Database.load());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            ArrayList<Desk> accounts = this.repos.getAll();

            JsonArray array = JsonConverter.toJsonArray(accounts, dg -> JsonConverter.toJson(dg));
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
            Desk dg = JsonConverter.toDesk(desk);

            if (this.repos.exists(dg.getDeskID())) {
                throw new IllegalArgumentException(
                    String.format(
                        "There already exists an desk with ID '%1s'.",
                        dg.getDeskID()));
            }

            this.repos.create(dg);

            return JsonConverter.Success(JsonConverter.toJson(dg));
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
    public JsonObject delete(@QueryParam("deskID") int deskID) {
        try {
            this.repos.delete(deskID);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }
}
