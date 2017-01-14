var addD2DToOverview = function(d2d) {

    // Function called once a row is tried to be removed.
    var remove = function(id, selectedRow) {
        services.device2desk
            .delete(id.split("-")[0], id.split("-")[1])
            .done(function() {
                selectedRow.remove();
            })
            .fail(function(error) {
                console.log("Error during removal of group '" + id + "'. Message: " + error);
            });
    }




    var row = toTableRowRemovable(
        d2d.deskID + "-" + d2d.deviceID,
        [d2d.deskID, d2d.deviceID],
        remove);

    $('#d2ds-overview').append(row);
}

var loadOverview = function() {
    services.device2desk
        .getAll()
        .done(function(d2ds) {
            for (var i = 0; i < d2ds.length; i++) {
                addD2DToOverview(d2ds[i]);
                console.log("Device groups: ", d2ds[i])
            }
        })
        .fail(function(error) {
           console.log("Error during the retrieval of the desks: " + error)
        });
}



var create = function() {
    var deskID = document.getElementById('new-d2d-input-deskid').value;
    if (!isInt(deskID)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else if(parseInt(deskID) == -1){
        setErrorMessage("You have to select a device.");
        return;
    } else {
        deskID = parseInt(deskID);
    }

    var deviceid = document.getElementById('new-d2d-input-deviceid').value;
    if (!isInt(deviceid)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else if(parseInt(deviceid) == -1){
        setErrorMessage("You have to select a desk.");
        return;
    } else {
        deviceid = parseInt(deviceid);
    }


    services.device2desk
        .create(deskID, deviceid)
        .done(function(d2d) {
            disableErrorMessage();

            addD2DToOverview(d2d);
        })
        .fail(function(error) {
            setErrorMessage(error);
        });
}



var fillDesks = function() {
    services.desk
        .getAll()
        .done(function(desks) {
            for (var i = 0; i < desks.length; i++) {

                var row = toSelectionField(
                    desks[i].deskID,
                    desks[i].deskID + " (On location: (" + desks[i].locX + ", " + desks[i].locY + "))");


                $('#new-d2d-input-deskid').append(row);
            }
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the desks: " + error)
        });
}

var fillDevices = function() {
    services.device
        .getAll()
        .done(function(devices) {
            for (var i = 0; i < devices.length; i++) {

                var lightorsensor = devices[i].deviceType == true ? "Light" : "Sensor";

                var row = toSelectionField(
                    devices[i].deviceID,
                    devices[i].deviceID + " (" + lightorsensor + " on location: (" + devices[i].locX + ", " + devices[i].locY + "))");


                $('#new-d2d-input-deviceid').append(row);
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

            services.desk
                .getAll()
                .done(function(desks) {

                    services.device2desk
                        .getAll()
                        .done(function(d2ds) {

                            for(var i = 0; i < devices.length; i++){
                                var found = false;
                                for(var j = 0; j < d2ds.length; j++){
                                    if(devices[i].deviceID == d2ds[j].deviceID){
                                        found = true;
                                        break;
                                    }
                                }
                                if(!found){
                                    var row = $('<li>');
                                    row.text("Device with ID '" + devices[i].deviceID + "' is not yet assigned to a desk.");
                                    $('#todos').append(row);
                                }
                            }

                            var array = [];

                            for(var i = 0; i < desks.length; i++){
                                array[desks[i].deskID] = 0;
                                for(var j = 0; j < d2ds.length; j++){
                                    if(desks[i].deskID == d2ds[j].deskID){
                                        array[desks[i].deskID] += 1;
                                        console.log(desks[i].deskID + " becomes " + array[desks[i].deskID]);

                                    }
                                }
                            }
                            for(var i = 0; i < desks.length; i++){
                                if(array[desks[i].deskID] < 3){
                                    var row = $('<li>');
                                    row.text("There are only " + array[desks[i].deskID] + " devices assigned to the desk with ID '" + desks[i].deskID + "'.");
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
    fillDesks();
    loadOverview();

})