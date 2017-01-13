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
    },

    /**
     * The Device service.
     */
    device: {

        /**
         * The base URL of the Device service.
         */
        url: "/services/DeviceService",

        /**
         * Create a new device.
         * @param deviceID The ID of the device
         * @param deviceType The type of the device.
         * @param state The state of the device.
         * @param roomNr The number of the room of the device.
         * @returns {Promise} The promise that will give the result of this call.
         */
        create: function(deviceID, deviceType, state, roomNr, locX, locY) {
            return api
                .post(
                    services.device.url + "/create",
                    {
                        deviceID: deviceID,
                        deviceType: deviceType,
                        state: state,
                        roomNr: roomNr,
                        locX: locX,
                        locY : locY
                    })
                .then(function(data) {
                    return parseDevice(data);
                });
        },

        /**
         * Deletes the specified device.
         * @param deviceID The deviceID of the device that is to be deleted.
         * @returns A promise notifying whether or not it was successful.
         */
        delete: function(deviceID) {
            return api.delete(
                services.device.url + "/delete",
                {
                    'deviceID': deviceID
                });
        },

        /**
         * Retrieves all existing devices.
         * @returns {Promise} The promise that will give the result of this call.
         */
        getAll: function() {
            return api
                .get(services.device.url + "/getAll")
                .then(function(data) {
                    var result = []

                    for (var i = 0; i < data.length; i++) {
                        result.push(parseDevice(data[i]));
                    }

                    return result;
                });
        }
    },

    /**
     * The Desk service.
     */
    desk: {

        /**
         * The base URL of the Desk service.
         */
        url: "/services/DeskService",

        /**
         * Create a new desk.
         * @param deskID The ID of the desk
         * @returns {Promise} The promise that will give the result of this call.
         */
        create: function(deskID, userID, locX, locY) {
            return api
                .post(
                    services.desk.url + "/create",
                    {
                        deskID: deskID,
                        userID: userID,
                        locX: locX,
                        locY: locY
                    })
                .then(function(data) {
                    return parseDesk(data);
                });
        },

        /**
         * Deletes the specified desk.
         * @param deskID The deviceID of the device that is to be deleted.
         * @returns A promise notifying whether or not it was successful.
         */
        delete: function(deskID) {
            return api.delete(
                services.desk.url + "/delete",
                {
                    'deskID': deskID
                });
        },

        /**
         * Retrieves all existing desks.
         * @returns {Promise} The promise that will give the result of this call.
         */
        getAll: function() {
            return api
                .get(services.desk.url + "/getAll")
                .then(function(data) {
                    var result = []

                    for (var i = 0; i < data.length; i++) {
                        result.push(parseDesk(data[i]));
                    }

                    return result;
                });
        }
    },


    /**
     * The device2desk service.
     */
    device2desk: {

        /**
         * The base URL of the Desk service.
         */
        url: "/services/Device2DeskService",

        /**
         * Create a new desk.
         * @param deskID The ID of the desk
         * @returns {Promise} The promise that will give the result of this call.
         */
        create: function(deskID, deviceID) {
            return api
                .post(
                    services.device2desk.url + "/create",
                    {
                        deskID: deskID,
                        deviceID: deviceID
                    })
                .then(function(data) {
                    return parseDevice2Desk(data);
                });
        },

        /**
         * Deletes the specified desk.
         * @param deskID The deviceID of the device that is to be deleted.
         * @returns A promise notifying whether or not it was successful.
         */
        delete: function(deskID, deviceID) {
            return api.delete(
                services.device2desk.url + "/delete",
                {
                    'deskID': deskID,
                    'deviceID': deviceID
                });
        },

        /**
         * Retrieves all existing desks.
         * @returns {Promise} The promise that will give the result of this call.
         */
        getAll: function() {
            return api
                .get(services.device2desk.url + "/getAll")
                .then(function(data) {
                    var result = []

                    for (var i = 0; i < data.length; i++) {
                        result.push(parseDevice2Desk(data[i]));
                    }

                    return result;
                });
        }
    }


}