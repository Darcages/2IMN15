function Event(timestamp, deviceID, userID, newState, userType) {
    var self = this;

    self.timestamp = timestamp;
    self.deviceID = deviceID;
    self.userID = deviceID;
    self.newState = newState;
    self.userType = userType;

}

var parseEvent = function(data) {
    return new Event(new Date(data.timestamp), data.deviceID, data.userID, data.newState, data.userType);
}