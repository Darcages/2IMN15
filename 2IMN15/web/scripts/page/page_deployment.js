var setDeploymentInOverview = function(id, deployment) {
    var table = $('#deployment-overview');
    var data = [id, DeploymentType.getName(deployment)];

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
        table.append(toTableRow(id, data));
    }
}

var loadLightDevices = function() {
    services.device
        .getAll()
        .then(function(devices) {
            var selection = $('#set-deployment-input-id')

            for (var i = 0; i < devices.length; i++) {
                var device = devices[i];

                if (device.isLightDevice()) {
                    setDeploymentInOverview(device.deviceID, device.deployment);

                    var option = $('<option>').text(device.deviceID);
                    selection.append(option);
                }
            }
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the available light devices: " + error);
        });
}

var update = function() {
    var deviceId = document.getElementById('set-deployment-input-id').value;
    var deploymentType = document.getElementById('set-deployment-input-type').value;

    if (!isInt(deviceId)) {
        setErrorMessage("The device identifier is not an integer.");
        return;
    } else {
        deviceId = parseInt(deviceId)
    }

    if (!isInt(deploymentType)) {
        setErrorMessage("The deployment type is not an integer.");
        return;
    } else {
        deploymentType = parseInt(deploymentType);
    }

    services.device
        .updateDeployment(deviceId, deploymentType)
        .then(function() {
            setDeploymentInOverview(deviceId, deploymentType)
        })
        .fail(function(error) {
            setErrorMessage(error);
        });
}

$(function() {
    loadLightDevices();
})