function Event(timestamp, deviceID, userID, newState) {
    var self = this;

    self.timestamp = timestamp;
    self.deviceID = deviceID;
    self.userID = deviceID;
    self.newState = newState;

}

var parseEvent = function(data) {
    return new Event(new Date(data.timestamp), data.deviceID, data.userID, data.newState);
}