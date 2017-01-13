function Desk(deskID, userID, locX, locY) {
    var self = this;

    self.deskID = deskID;
    self.userID = userID;
    self.locX = locX;
    self.locY = locY;


    self.userIdToString = function () {
        return "Office-Worker-" + self.userID;
    }

}

var parseDesk = function(data) {
    return new Desk(data.deskID, data.userID, data.locX, data.locY);
}