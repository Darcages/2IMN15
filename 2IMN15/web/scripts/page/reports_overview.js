
var fillBarGraph = function() {

    var lightchart;
    var lightchartData = [];

    var sensorchart;
    var sensorchartData = [];

    services.event
        .getAll()
        .done(function(events) {

            services.device
                .getAll()
                .done(function(devices) {

                    var lastOnTime = [];
                    var sumTime = [];

                    for (var i = 0; i < events.length; i++) {
                        for(var j = 0; j < devices.length; j++){
                            if(events[i].deviceID == devices[j].deviceID){
                                if(events[i].newState == 1 || events[i].newState == 2){
                                    if(sumTime[events[i].deviceID] == undefined) sumTime[events[i].deviceID] = 0;
                                    lastOnTime[events[i].deviceID] = events[i].timestamp;
                                    console.log("Set on: " + events[i].deviceID + " at " + events[i].timestamp);

                                }
                                else {
                                    if(sumTime[events[i].deviceID] != undefined){
                                        //sum += elapsed
                                        sumTime[events[i].deviceID] += (events[i].timestamp - lastOnTime[events[i].deviceID]);
                                        console.log("Set off: " + events[i].deviceID + " at " + events[i].timestamp);
                                        console.log("elapsed: " + (events[i].timestamp - lastOnTime[events[i].deviceID]));
                                        console.log("sum: " + sumTime[events[i].deviceID]);
                                    }
                                }
                                break;
                            }
                        }
                    }

                    for(var j = 0; j < devices.length; j++){
                        var hours = Math.round(sumTime[devices[j].deviceID]/1000/3600 * 100) / 100
                        var column = {"Device":devices[j].deviceID, "Hours":hours};
                        if(devices[j].deviceType == true) lightchartData.push(column)
                        else sensorchartData.push(column)
                    }
                    lightchart.validateData();
                    sensorchart.validateData();
                })
                .fail(function(error) {
                    console.log("Error during the retrieval of the devices: " + error)
                });
        })
        .fail(function(error) {
            console.log("Error during the retrieval of the events: " + error)
        });






    AmCharts.ready(function () {
        //lightsChart
        {
            // SERIAL CHART
            lightchart = new AmCharts.AmSerialChart();
            lightchart.dataProvider = lightchartData;
            lightchart.categoryField = "Device";
            lightchart.startDuration = 1;

            // AXES
            // category
            var categoryAxis = lightchart.categoryAxis;
            categoryAxis.labelRotation = 90;
            categoryAxis.gridPosition = "start";

            // value
            // in case you don't want to change default settings of value axis,
            // you don't need to create it, as one value axis is created automatically.

            // GRAPH
            var graph = new AmCharts.AmGraph();
            graph.valueField = "Hours";
            graph.balloonText = "[[category]]: <b>[[value]]</b>";
            graph.type = "column";
            graph.lineAlpha = 0;
            graph.fillAlphas = 0.8;
            lightchart.addGraph(graph);

            // CURSOR
            var chartCursor = new AmCharts.ChartCursor();
            chartCursor.cursorAlpha = 0;
            chartCursor.zoomable = false;
            chartCursor.categoryBalloonEnabled = false;
            lightchart.addChartCursor(chartCursor);

            lightchart.creditsPosition = "top-right";

            lightchart.write("lightchartdiv");
        }

        //sensorChart
        {
            // SERIAL CHART
            sensorchart = new AmCharts.AmSerialChart();
            sensorchart.dataProvider = sensorchartData;
            sensorchart.categoryField = "Device";
            sensorchart.startDuration = 1;

            // AXES
            // category
            var categoryAxis = sensorchart.categoryAxis;
            categoryAxis.labelRotation = 90;
            categoryAxis.gridPosition = "start";

            // value
            // in case you don't want to change default settings of value axis,
            // you don't need to create it, as one value axis is created automatically.

            // GRAPH
            var graph = new AmCharts.AmGraph();
            graph.valueField = "Hours";
            graph.balloonText = "[[category]]: <b>[[value]]</b>";
            graph.type = "column";
            graph.lineAlpha = 0;
            graph.fillAlphas = 0.8;
            sensorchart.addGraph(graph);

            // CURSOR
            var chartCursor = new AmCharts.ChartCursor();
            chartCursor.cursorAlpha = 0;
            chartCursor.zoomable = false;
            chartCursor.categoryBalloonEnabled = false;
            sensorchart.addChartCursor(chartCursor);

            sensorchart.creditsPosition = "top-right";

            sensorchart.write("sensorchartdiv");
        }

    });
}

$(function() {
    fillBarGraph();


})