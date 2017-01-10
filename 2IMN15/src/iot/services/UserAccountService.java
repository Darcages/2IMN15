package iot.services;

import iot.data.Database;
import iot.domain.UserAccount;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Geert on 2017-01-10.
 */
@Path("/UserAccountService")
public class UserAccountService {

    private ArrayList<UserAccount> tmp = new ArrayList<>();

    public UserAccountService() {
        this.tmp.add(UserAccount.Make(1, 3, "Test", Optional.of("prefix"), "Tester", "test@email.com", "password"));
        this.tmp.add(UserAccount.Make(2, 7, "Bert", Optional.empty(), "Janssen", "bert@janssen.nl", "wachtwoord"));
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            JsonArray array = JsonConverter.toJsonArray(this.tmp, ua -> JsonConverter.toJson(ua));
            return JsonConverter.Success(array);
        }
        catch (Exception ex) {
            // TODO: Some kind of logging?
            return JsonConverter.ExceptionInternal();
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject create(JsonObject userAccount) {
        try {
            UserAccount ua = JsonConverter.toUserAccount(userAccount);

            // TODO: Actually check existence.
            for (UserAccount existing : this.tmp)
            {
                if (existing.getGroupNr() == ua.getGroupNr()) {
                    throw new NotAllowedException(
                        String.format(
                            "There already exists an user account with the group number '%1s'.",
                            ua.getGroupNr()));
                }
            }

            // TODO: Actually create the user account.
            this.tmp.add(ua);

            return JsonConverter.Success(JsonConverter.toJson(ua));
        }
        catch (IllegalArgumentException | NoSuchFieldException | NoSuchElementException | NotAllowedException ex)
        {
            return JsonConverter.Exception(ex);
        }
        catch (Exception ex)
        {
            // TODO: Some kind of logging?
            return JsonConverter.ExceptionInternal();
        }
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject delete(@QueryParam("groupNr") int groupNr) {
        try {
            this.tmp.removeIf(ua -> ua.getGroupNr() == groupNr);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            return JsonConverter.ExceptionInternal();
        }
    }
}
