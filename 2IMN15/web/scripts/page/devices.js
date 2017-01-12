var addDeviceToOverview = function(device) {

    // Function called once a row is tried to be removed.
    var remove = function(deviceID, selectedRow) {
        services.device
            .delete(deviceID)
            .done(function() {
                selectedRow.remove();
            })
            .fail(function(error) {
                console.log("Error during removal of group '" + groupNr + "'. Message: " + error);
            });
    }

    var row = toTableRowRemovable(
        device.deviceID,
        [device.deviceID, device.deviceTypeString(), device.stateString(), device.roomNr],
        remove);

    $('#devices-overview').append(row);
}

var loadOverview = function() {
    services.device
        .getAll()
        .done(function(devices) {
            for (var i = 0; i < devices.length; i++) {
                addDeviceToOverview(devices[i]);
                console.log("device: ", $(devices[i]));
            }
        })
        .fail(function(error) {
           console.log("Error during the retrieval of the devices: " + error)
        });
}

var create = function() {
    var deviceID = document.getElementById('new-device-input-id').value;

    if (document.getElementById('new-device-input-type-true').checked) {
        var deviceType = true;
    }
    else {
        var deviceType = false
    }



    var deviceState = false;

    if (!isInt(deviceID)) {
        setErrorMessage("The device id is not an integer.");
        return;
    } else {
        deviceID = parseInt(deviceID)
    }


    var roomNr = document.getElementById('new-device-input-room').value;

    if (!isInt(roomNr)) {
        setErrorMessage("The room number is not an integer.");
        return;
    } else {
        roomNr = parseInt(roomNr);
    }

    services.device
        .create(deviceID, deviceType, deviceState, roomNr)
        .done(function(device) {
            disableErrorMessage();

            addDeviceToOverview(device);
        })
        .fail(function(error) {
            setErrorMessage(error);
        });
}

$(function() {
    loadOverview();
})