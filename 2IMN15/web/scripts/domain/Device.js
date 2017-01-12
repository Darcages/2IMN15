function Device(deviceID, deviceType, state, roomNr, locX, locY) {
    var self = this;

    self.deviceID = deviceID;
    self.deviceType = deviceType;
    self.state = state;
    self.roomNr = roomNr;
    self.locX = locX;
    self.locY = locY;

    self.deviceTypeString = function () {
        if(self.deviceType) return "Light";
        else return "Sensor";
    }

    self.stateString = function () {
        if(self.deviceType){
            if(self.state) return "On";
            else return "Off";
        }
        else {
            if(self.state) return "Occupied";
            else return "Free";
        }
    }

}

var parseDevice = function(data) {
    return new Device(data.deviceID, data.deviceType, data.state, data.roomNr, data.locX, data.locY);
}