var addUserToOverview = function(userAccount) {

    // Function called once a row is tried to be removed.
    var remove = function(groupNr, selectedRow) {
        services.userAccount
            .delete(groupNr)
            .done(function() {
                selectedRow.remove();
            })
            .fail(function(error) {
                console.log("Error during removal of group '" + groupNr + "'. Message: " + error);
            });
    }

    var row = toTableRowRemovable(
        userAccount.groupNr,
        [userAccount.userId(), userAccount.roomId(), userAccount.groupNr, userAccount.name(), userAccount.email],
        remove);

    $('#users-overview').append(row);
}

var loadOverview = function() {
    services.userAccount
        .getAll()
        .done(function(userAccounts) {
            for (var i = 0; i < userAccounts.length; i++) {
                addUserToOverview(userAccounts[i]);
            }
        })
        .fail(function(error) {
           console.log("Error during the retrieval of the user accounts: " + error)
        });
}

var create = function() {
    var groupNr = document.getElementById('new-user-input-group').value;
    var roomNr = document.getElementById('new-user-input-room').value;

    if (!isInt(groupNr)) {
        setErrorMessage("The group number is not an integer.");
        return;
    } else {
        groupNr = parseInt(groupNr)
    }

    if (!isInt(roomNr)) {
        setErrorMessage("The room number is not an integer.");
        return;
    } else {
        roomNr = parseInt(roomNr);
    }

    var firstName = document.getElementById('new-user-input-name-first').value;
    var prefix = document.getElementById('new-user-input-name-prefix').value;
    var lastName = document.getElementById('new-user-input-name-last').value;
    var email = document.getElementById('new-user-input-email').value;
    var password = document.getElementById('new-user-input-password').value;

    services.userAccount
        .create(groupNr, roomNr, firstName, prefix, lastName, email, password)
        .done(function(userAccount) {
            disableErrorMessage();

            addUserToOverview(userAccount);
        })
        .fail(function(error) {
            setErrorMessage(error);
        });
}

$(function() {
    loadOverview();
})