package iot.services;

import iot.data.repository.RoomRepository;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by Geert on 2017-01-14.
 */
@Path("/RoomService")
public class RoomService {

    private final RoomRepository repos = new RoomRepository();

    @GET
    @Path("/getAllNrs")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAllNrs() {
        try {
            ArrayList<Integer> nrs = this.repos.getAllNrs();

            JsonArray array = JsonConverter.toJsonArray(nrs);
            return JsonConverter.Success(array);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }
}
