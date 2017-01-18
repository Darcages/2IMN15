var onOffChart;
var onOffChartData = [];

var userTypeChart;
var userTypeChartData = [];


var allDevices = [];

var createPieGraph = function(){



    AmCharts.ready(function () {

        {

            var legend;
            // PIE CHART
            onOffChart = new AmCharts.AmPieChart();
            onOffChart.dataProvider = onOffChartData;
            onOffChart.titleField = "country";
            onOffChart.valueField = "litres";
            onOffChart.gradientRatio = [0, 0, 0, -0.2, -0.4];
            onOffChart.gradientType = "radial";

            // LEGEND
            legend = new AmCharts.AmLegend();
            legend.align = "center";
            legend.markerType = "circle";
            onOffChart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]] hours</b> ([[percents]]%)</span>";
            onOffChart.addLegend(legend);

            // WRITE
            onOffChart.write("onOffChartdiv");
        }


        {

            var legend;
            // PIE CHART
            userTypeChart = new AmCharts.AmPieChart();
            userTypeChart.dataProvider = userTypeChartData;
            userTypeChart.titleField = "country";
            userTypeChart.valueField = "litres";
            userTypeChart.gradientRatio = [0, 0, 0, -0.2, -0.4];
            userTypeChart.gradientType = "radial";

            // LEGEND
            legend = new AmCharts.AmLegend();
            legend.align = "center";
            legend.markerType = "circle";
            userTypeChart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]] hours</b> ([[percents]]%)</span>";
            userTypeChart.addLegend(legend);

            // WRITE
            userTypeChart.write("userTypeChart");
        }
    });
}



var filluserTypeChart = function(deviceID) {


    services.event
        .getAll()
        .done(function(events) {

            userTypeChartData.length = 0;

            if(deviceID != -1) {


                var lastTime = 0;
                var lastUser = 0;
                var sumTime1 = 0;
                var sumTime2 = 0;
                var sumTime3 = 0;

                for (var i = 0; i < events.length; i++) {
                    if (events[i].deviceID == deviceID) {
                        if (events[i].newState == true) {
                            lastUser = events[i].userType;
                            lastTime = events[i].timestamp;
                        }
                        else {
                            //sumTimeOff += elapsed

                            if(lastUser == 1 && lastTime != 0) sumTime1 += (events[i].timestamp - lastTime);
                            else if(lastUser == 2 && lastTime != 0) sumTime2 += (events[i].timestamp - lastTime);
                            else if(lastUser == 3 && lastTime != 0) sumTime3 += (events[i].timestamp - lastTime);
                        }
                    }
                }
                //clear array

                var hours = Math.round(sumTime1 / 1000 / 3600 * 100) / 100;
                var column = {"country": "User1", "litres": hours}
                userTypeChartData.push(column);

                hours = Math.round(sumTime2 / 1000 / 3600 * 100) / 100;
                column = {"country": "User2", "litres": hours}
                userTypeChartData.push(column);

                hours = Math.round(sumTime3 / 1000 / 3600 * 100) / 100;
                column = {"country": "User3", "litres": hours}
                userTypeChartData.push(column);


                console.log(userTypeChartData);
            }
            userTypeChart.validateData();

        })
        .fail(function(error) {
            console.log("Error during the retrieval of the events: " + error)
        });


    //userTypeChart.validateData();





}


var fillonOffChart = function(deviceID) {


    services.event
        .getAll()
        .done(function(events) {

            onOffChartData.length = 0;

            if(deviceID != -1) {


                var lastTime = 0;
                var lastState = -1;
                var sumTimeOn = 0;
                var sumTimeOff = 0;
                var sumTimeDimmed = 0;

                for (var i = 0; i < events.length; i++) {
                    if (events[i].deviceID == deviceID) {
                        if (events[i].newState == 1) {
                            if(lastState == 0 && lastTime != 0) sumTimeOff += (events[i].timestamp - lastTime);
                            else if(lastState == 2 && lastTime != 0) sumTimeDimmed += (events[i].timestamp - lastTime);
                            lastTime = events[i].timestamp;
                            lastState = 1;
                        }
                        else if(events[i].newState == 2){
                            if(lastState == 0 && lastTime != 0) sumTimeOff += (events[i].timestamp - lastTime);
                            else if(lastState == 1 && lastTime != 0) sumTimeOn += (events[i].timestamp - lastTime);

                            lastTime = events[i].timestamp;
                            lastState = 2;
                        }
                        else {

                            if(lastState == 2 && lastTime != 0) sumTimeDimmed += (events[i].timestamp - lastTime);
                            else if(lastState == 1 && lastTime != 0) sumTimeOn += (events[i].timestamp - lastTime);

                            lastTime = events[i].timestamp;
                            lastState = 0;
                        }
                    }
                }
                //clear array

                if (allDevices[deviceID].deviceType == true){
                    var hours = Math.round(sumTimeOn / 1000 / 3600 * 100) / 100;
                    var column = {"country": "on", "litres": hours};
                    hours = Math.round(sumTimeOff / 1000 / 3600 * 100) / 100;
                    var column2 = {"country": "off", "litres": hours};
                    hours = Math.round(sumTimeDimmed / 1000 / 3600 * 100) / 100;
                    var column3 = {"country": "dimmed", "litres": hours};
                    onOffChartData.push(column);
                    onOffChartData.push(column2);
                    onOffChartData.push(column3);
                }
                else {
                    var hours = Math.round(sumTimeOn / 1000 / 3600 * 100) / 100;
                    var column = {"country": "occupied", "litres": hours};
                    hours = Math.round(sumTimeOff / 1000 / 3600 * 100) / 100;
                    var column2 = {"country": "free", "litres": hours};
                    onOffChartData.push(column);
                    onOffChartData.push(column2);
                }

            }
            onOffChart.validateData();

        })
        .fail(function(error) {
            console.log("Error during the retrieval of the events: " + error)
        });




}

var fillDevices = function() {
    services.device
        .getAll()
        .done(function(devices) {
            for (var i = 0; i < devices.length; i++) {


                allDevices[devices[i].deviceID] = devices[i];
                var lightorsensor = devices[i].deviceType == true ? "Light" : "Sensor";

                var row = toSelectionField(
                    devices[i].deviceID,
                    devices[i].deviceID + " (" + lightorsensor + " on location: (" + devices[i].locX + ", " + devices[i].locY + "))");


                $('#input-deviceid').append(row);
            }
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the devices: " + error)
        });
}

var fillPage = function(){


    var deviceID = document.getElementById('input-deviceid').value;
    if (!isInt(deviceID)) {
        setErrorMessage("The device ID is not an integer.");
        return;
    } else {
        deviceID = parseInt(deviceID);
    }



    filluserTypeChart(deviceID);
    fillonOffChart(deviceID);
}


$(function() {


    createPieGraph();
    fillPage();

    fillDevices();
    //fillBarGraph();


})