package iot.services;

import iot.data.Database;
import iot.data.repository.UserAccountRepository;
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

    private UserAccountRepository repos;

    public UserAccountService() {
        try {
            this.repos = new UserAccountRepository(Database.load());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getAll() {
        try {
            ArrayList<UserAccount> accounts = this.repos.getAll();

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
    public JsonObject create(JsonObject userAccount) {
        try {
            UserAccount ua = JsonConverter.toUserAccount(userAccount);

            if (this.repos.exists(ua.getGroupNr())) {
                throw new IllegalArgumentException(
                    String.format(
                        "There already exists an user account with the group number '%1s'.",
                        ua.getGroupNr()));
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
    public JsonObject delete(@QueryParam("groupNr") int groupNr) {
        try {
            this.repos.delete(groupNr);
            return JsonConverter.Success();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return JsonConverter.ExceptionInternal();
        }
    }
}
