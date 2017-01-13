function Device2Desk(deskID, deviceID) {
    var self = this;

    self.deskID = deskID;
    self.deviceID = deviceID;


}

var parseDevice2Desk = function(data) {
    return new Device2Desk(data.deskID, data.deviceID);
}