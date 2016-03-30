var updateLegendTimeout = null;
var latestPosition = null;
var plots = [];

function rysujWykres(dane, osX, osY, element, czySlupki, nazwa) {
    var opadyPlotData = [];
    for (i = 0; i < dane.length; i++) {
        opadyPlotData.push([new Date(dane[i][osX]).getTime(), dane[i][osY]]);
    }

    if (czySlupki) {

        var plot = $.plot($(element), [{data: opadyPlotData, label: nazwa + " = 0.00"}],
                {
                    xaxis: {mode: "time"}, yaxis:{min: 0}, bars: {
                        show: czySlupki
                    },
                    grid: {hoverable: true, clickable: true},
                    crosshair: {mode: "x"}
                });
    } else if(osY === 'wilgotnosc') {
        var plot = $.plot($(element), [{data: opadyPlotData, label: nazwa + " = 0.00"}],
                {
                    xaxis: {mode: "time"}, yaxis:{min: 0, max:100}, bars: {
                        show: czySlupki
                    },
                    grid: {hoverable: true, clickable: true},
                    crosshair: {mode: "x"}
                });
    }
    else{
                var plot = $.plot($(element), [{data: opadyPlotData, label: nazwa + " = 0.00"}],
                {
                    xaxis: {mode: "time"}, bars: {
                        show: czySlupki
                    },
                    grid: {hoverable: true, clickable: true},
                    crosshair: {mode: "x"}
                });
    }

    $(element).bind("plothover", function (event, pos, item) {
        latestPosition = pos;
        if (!updateLegendTimeout) {
            updateLegendTimeout = setTimeout(function () {
                updateLegend(plot, element);
            }, 50);
        }
    });
    plots.push([plot, element]);
}


function updateLegend(plot, element) {


    updateLegendTimeout = null;

    var pos = latestPosition;

    var axes = plot.getAxes();
    for (var k = 0; k < plots.length; k++) {
        plots[k][0].setCrosshair({x: pos.x});
        var legends = $(plots[k][1] + " .legendLabel");

        if (pos.x < axes.xaxis.min || pos.x > axes.xaxis.max) {
            return;
        }

        var i, j, dataset = plots[k][0].getData();
        for (i = 0; i < dataset.length; ++i) {

            var series = dataset[i];

            // Find the nearest points, x-wise

            for (j = 0; j < series.data.length; ++j) {
                if (series.data[j][0] > pos.x) {
                    break;
                }
            }

            // Now Interpolate

            var y,
                    p1 = series.data[j - 1],
                    p2 = series.data[j];

            if (p1 == null) {
                y = p2[1];
            } else if (p2 == null) {
                y = p1[1];
            } else {
                y = p1[1] + (p2[1] - p1[1]) * (pos.x - p1[0]) / (p2[0] - p1[0]);
            }

            legends.eq(i).text(series.label.replace(/=.*/, "= " + y.toFixed(2)));
        }
    }
}

