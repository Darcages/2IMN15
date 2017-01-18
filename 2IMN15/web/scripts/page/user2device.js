var addU2DToOverview = function(d2d) {
    var table = $('#users2devices-overview');

    var id = d2d.userID + "-" + d2d.deviceID;
    var data = [d2d.userID, d2d.deviceID, d2d.prioLevel, d2d.red, d2d.green, d2d.blue, d2d.lowLight];

    var row = table
        .children('tr')
        .filter(function(i) {
           return $(this).data('id') === id;
        });

    if (row.length > 0) {
        row
            .children('td')
            .each(function (i) {
                if (0 <= i && i < data.length) {
                    $(this).text(data[i]);
                }
            });
    } else {
        // Function called once a row is tried to be removed.
        var remove = function(id, selectedRow) {
            services.user2device
                .delete(id.split("-")[0], id.split("-")[1])
                .done(function() {
                    selectedRow.remove();
                })
                .fail(function(error) {
                    console.log("Error during removal of group '" + id + "'. Message: " + error);
                });
        }

        table.append(toTableRowRemovable(id, data, remove));
    }
}

var loadOverview = function() {
    services.user2device
        .getAll()
        .done(function(u2ds) {
            for (var i = 0; i < u2ds.length; i++) {
                addU2DToOverview(u2ds[i]);
                console.log("User2Devices groups: ", u2ds[i])
            }
        })
        .fail(function(error) {
           console.log("Error during the retrieval of the desks: " + error)
        });
}

var set = function() {
    var userid = document.getElementById('new-u2d-input-userid').value;
    if (!isInt(userid)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else if(parseInt(userid) == -1){
        setErrorMessage("You have to select a user.");
        return;
    } else {
        userid = parseInt(userid);
    }

    var deviceid = document.getElementById('new-u2d-input-deviceid').value;
    if (!isInt(deviceid)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else if(parseInt(deviceid) == -1){
        setErrorMessage("You have to select a device.");
        return;
    } else {
        deviceid = parseInt(deviceid);
    }

    if (document.getElementById('new-u2d-input-prio-1').checked) {
        var prio = 1;
    }
    else if (document.getElementById('new-u2d-input-prio-2').checked) {
        var prio = 2;
    }
    else {
        var prio = 3;
    }

    var red = document.getElementById('new-u2d-input-red').value;
    if (!isInt(red)) {
        setErrorMessage("Red is not an integer.");
        return;
    } else {
        red = parseInt(red);
    }

    var green = document.getElementById('new-u2d-input-green').value;
    if (!isInt(green)) {
        setErrorMessage("Green is not an integer.");
        return;
    } else {
        green = parseInt(green);
    }

    var blue = document.getElementById('new-u2d-input-blue').value;
    if (!isInt(blue)) {
        setErrorMessage("Blue is not an integer.");
        return;
    } else {
        blue = parseInt(blue);
    }

    if (document.getElementById('new-u2d-input-lowlight-true').checked) {
        var lowlight = true;
    }
    else {
        var lowlight = false
    }

    services.user2device
        .set(userid, deviceid, prio, red, green, blue, lowlight)
        .done(function(u2d) {
            disableErrorMessage();

            addU2DToOverview(u2d);
        })
        .fail(function(error) {
            setErrorMessage(error);
        });
}

var fillUsers = function() {
    services.userAccount
        .getAll()
        .done(function(users) {
            for (var i = 0; i < users.length; i++) {

                var row = toSelectionField(
                    users[i].groupNr,
                    users[i].firstName + " " + users[i].prefix + " " + users[i].lastName);

                $('#new-u2d-input-userid').append(row);
            }
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the users: " + error)
        });
}

var fillDevices = function() {
    services.device
        .getAll()
        .done(function(devices) {
            for (var i = 0; i < devices.length; i++) {
                if(devices[i].deviceType == true) {

                    var row = toSelectionField(
                        devices[i].deviceID,
                        devices[i].deviceID + " (Light on location: (" + devices[i].locX + ", " + devices[i].locY + "))");


                    $('#new-u2d-input-deviceid').append(row);
                }
            }
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the devices: " + error)
        });
}

var fillTODO = function() {
    services.device
        .getAll()
        .done(function(devices) {

            services.userAccount
                .getAll()
                .done(function(users) {

                    services.user2device
                        .getAll()
                        .done(function(u2ds) {

                            for(var i = 0; i < devices.length; i++){
                                if(devices[i].deviceType == true) {
                                    var user1 = false;
                                    var user2 = false;
                                    var user3 = false;

                                    for (var j = 0; j < u2ds.length; j++) {
                                        if (devices[i].deviceID == u2ds[j].deviceID) {
                                            console.log("type: " + devices[i].deviceID + " prio " + u2ds[j].prioLevel);
                                            if (u2ds[j].prioLevel == 1) user1 = true;
                                            else if (u2ds[j].prioLevel == 2) user2 = true;
                                            else if (u2ds[j].prioLevel == 3) user3 = true;
                                        }
                                    }

                                    if (!user1) {
                                        var row = $('<li>');
                                        row.text("Device with ID '" + devices[i].deviceID + "' is not yet assigned a User 1 priority.");
                                        $('#todos').append(row);
                                    }
                                    if (!user2) {
                                        var row = $('<li>');
                                        row.text("Device with ID '" + devices[i].deviceID + "' is not yet assigned a User 2 priority.");
                                        $('#todos').append(row);
                                    }
                                    if (!user3) {
                                        var row = $('<li>');
                                        row.text("Device with ID '" + devices[i].deviceID + "' is not yet assigned a User 3 priority.");
                                        $('#todos').append(row);
                                    }
                                }
                            }

                            for(var i = 0; i < users.length; i++){
                                for (var j = 0; j < u2ds.length; j++) {
                                    var found = false;
                                    if (users[i].groupNr == u2ds[j].userID) {
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    var row = $('<li>');
                                    row.text("User with ID '" + users[i].groupNr + "' is not yet assigned a device.");
                                    $('#todos').append(row);
                                }
                            }


                        })
                        .fail(function(error) {
                            console.log("Error during the retrieval of the d2ds: " + error)
                        });
                })
                .fail(function(error) {
                    console.log("Error during the retrieval of the desks: " + error)
                });
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the devices: " + error)
        });
}


$(function() {
    fillTODO();
    fillDevices();
    fillUsers();
    loadOverview();

})