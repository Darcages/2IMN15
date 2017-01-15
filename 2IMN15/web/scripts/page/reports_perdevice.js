var chart;
var legend;

var chartData = [];

var allDevices = [];

var createPieGraph = function(){



    AmCharts.ready(function () {
        // PIE CHART
        chart = new AmCharts.AmPieChart();
        chart.dataProvider = chartData;
        chart.titleField = "country";
        chart.valueField = "litres";
        chart.gradientRatio = [0, 0, 0 ,-0.2, -0.4];
        chart.gradientType = "radial";

        // LEGEND
        legend = new AmCharts.AmLegend();
        legend.align = "center";
        legend.markerType = "circle";
        chart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
        chart.addLegend(legend);

        // WRITE
        chart.write("chartdiv");
    });
}


var fillPieGraph = function(deviceID) {


        services.event
            .getAll()
            .done(function(events) {

                chartData.length = 0;

                if(deviceID != -1) {


                    var lastTime = 0;
                    var sumTimeOn = 0;
                    var sumTimeOff = 0;

                    for (var i = 0; i < events.length; i++) {
                        if (events[i].deviceID == deviceID) {
                            if (events[i].newState == true) {
                                //sumTimeOff += elapsed
                                if (lastTime != 0) sumTimeOff += (events[i].timestamp - lastTime);
                                lastTime = events[i].timestamp;
                            }
                            else {
                                //sumTimeOff += elapsed
                                if (lastTime != 0) sumTimeOn += (events[i].timestamp - lastTime);
                                lastTime = events[i].timestamp;
                            }
                        }
                    }
                    //clear array

                    var hours = Math.round(sumTimeOn / 1000 / 3600 * 100) / 100;

                    var column
                    if (allDevices[deviceID].deviceType == true) column = {"country": "on", "litres": hours};
                    else column = {"country": "occupied", "litres": hours};

                    chartData.push(column);
                    hours = Math.round(sumTimeOff / 1000 / 3600 * 100) / 100;

                    if (allDevices[deviceID].deviceType == true) column = {"country": "off", "litres": hours};
                    else column = {"country": "free", "litres": hours};

                    chartData.push(column);

                }
                chart.validateData();

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



    fillPieGraph(deviceID);
}


$(function() {


    createPieGraph();
    fillPage();
    fillDevices();
    //fillBarGraph();


})