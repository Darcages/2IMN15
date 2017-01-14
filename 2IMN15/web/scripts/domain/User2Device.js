function User2Device(userID, deviceID, prioLevel, red, green, blue, lowLight) {
    var self = this;

    self.userID = userID;
    self.deviceID = deviceID;
    self.prioLevel = prioLevel;
    self.red = red;
    self.green = green;
    self.blue = blue;
    self.lowLight = lowLight;



}

var parseUser2Device = function(data) {
    return new User2Device(data.userID, data.deviceID, data.prioLevel, data.red, data.green, data.blue, data.lowLight);
}