var addDeskToOverview = function(desk) {

    // Function called once a row is tried to be removed.
    var remove = function(deskID, selectedRow) {
        services.desk
            .delete(deskID)
            .done(function() {
                selectedRow.remove();
            })
            .fail(function(error) {
                console.log("Error during removal of group '" + groupNr + "'. Message: " + error);
            });
    }




    var row = toTableRowRemovable(
        desk.deskID,
        [desk.deskID, desk.userIdToString(), desk.locX, desk.locY],
        remove);

    $('#desks-overview').append(row);
}

var loadOverview = function() {
    services.desk
        .getAll()
        .done(function(desks) {
            for (var i = 0; i < desks.length; i++) {
                addDeskToOverview(desks[i]);
                //console.log("Device groups: ", desks[i])
            }
        })
        .fail(function(error) {
           console.log("Error during the retrieval of the desks: " + error)
        });
}



var create = function() {
    var deskID = document.getElementById('new-desk-input-id').value;
    if (!isInt(deskID)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else {
        deskID = parseInt(deskID);
    }

    var userid = document.getElementById('new-desk-input-user').value;
    if (!isInt(userid)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else if(parseInt(userid) == -1){
        setErrorMessage("You have to select a user.");
        return;
    } else {
        userid = parseInt(userid);
    }

    var locX = document.getElementById('new-desk-input-locx').value;
    if (!isInt(locX)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else {
        locX = parseInt(locX);
    }

    var locY = document.getElementById('new-desk-input-locy').value;
    if (!isInt(locY)) {
        setErrorMessage("The device group ID is not an integer.");
        return;
    } else {
        locY = parseInt(locY);
    }



    services.desk
        .create(deskID, userid, locX, locY)
        .done(function(desk) {
            disableErrorMessage();

            addDeskToOverview(desk);
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

                $('#new-desk-input-user').append(row);
            }
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the users: " + error)
        });
}


$(function() {
    fillUsers();
    loadOverview();

})