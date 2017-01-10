/**
 * Contains the API for the cloud services.
 */
var services = {

    /**
     * The UserAccount service.
     */
    userAccount: {

        /**
         * The base URL of the UserAccount service.
         */
        url: "/services/UserAccountService",

        /**
         * Create a new user account.
         * @param groupNr The group number of the user account.
         * @param roomNr The number of the room of the user account.
         * @param firstName The first name of the user account.
         * @param prefix The prefix of the user account.
         * @param lastName The last name of the user account.
         * @param email The email address of the user account.
         * @param password The password of the user account.
         * @returns {Promise} The promise that will give the result of this call.
         */
        create: function(groupNr, roomNr, firstName, prefix, lastName, email, password) {
            return api
                .post(
                    services.userAccount.url + "/create",
                    {
                        groupNr: groupNr,
                        roomNr: roomNr,
                        firstName: firstName,
                        prefix: prefix,
                        lastName: lastName,
                        email: email,
                        password: password
                    })
                .then(function(data) {
                    return parseUserAccount(data);
                });
        },

        /**
         * Deletes the specified user account.
         * @param groupNr The group number of the user account that is to be deleted.
         * @returns A promise notifying whether or not it was successful.
         */
        delete: function(groupNr) {
            return api.delete(
                services.userAccount.url + "/delete",
                {
                    'groupNr': groupNr
                });
        },

        /**
         * Retrieves all existing user accounts.
         * @returns {Promise} The promise that will give the result of this call.
         */
        getAll: function() {
            return api
                .get(services.userAccount.url + "/getAll")
                .then(function(data) {
                    var result = []

                    for (var i = 0; i < data.length; i++) {
                        result.push(parseUserAccount(data[i]));
                    }

                    return result;
                });
        }
    }
}