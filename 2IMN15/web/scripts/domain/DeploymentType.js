var DeploymentType = {
    Broker: 1,
    Distributed: 2,
    Unknown: -1,

    getName: function(type) {
        if (type == 1) {
            return 'Broker';
        } else if (type == 2) {
            return 'Distributed';
        } else {
            return 'Unknown';
        }
    }
};