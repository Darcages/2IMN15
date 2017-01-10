function UserAccount(groupNr, roomNr, firstName, prefix, lastName, email) {
    var self = this;

    self.groupNr = groupNr;
    self.roomNr = roomNr;
    self.firstName = firstName;
    self.prefix = prefix;
    self.lastName = lastName;
    self.email = email;

    self.name = function () {
        var fullName = self.lastName;

        if (self.prefix) {
            fullName += " " + self.prefix;
        }

        return fullName + ", " + self.firstName;
    }

    self.roomId = function() {
        return "Room-" + self.roomNr;
    }

    self.userId = function () {
        return "Office-Worker-" + self.groupNr;
    }
}

var parseUserAccount = function(data) {
    return new UserAccount(data.groupNr, data.roomNr, data.firstName, data.prefix, data.lastName, data.email);
}