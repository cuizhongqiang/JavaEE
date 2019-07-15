//系统数据监控 统计页面

var sysMonitoringapp = angular.module('sysMonitoringapp', []);

sysMonitoringapp.service('sysMonitoringappServices', function ($http) {
    var service = {
        health: function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/health',
                params: param
            });
        },
        metrics: function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/metrics',
                params: param
            });
        },
        env:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/env',
                params: param
            });
        }
    };
    return service;
});


sysMonitoringapp.controller('sysMonitoringCtrl', ['$scope', '$http', '$window', 'sysMonitoringappServices', function ($scope, $http, $window, sysMonitoringappServices) {


    var placeholder = $('#piechart-placeholder').css({'width': '90%', 'min-height': '150px'});
    var getHealthData = sysMonitoringappServices.health();
    var getMetricsData = sysMonitoringappServices.metrics();
    var getEnvInfo = sysMonitoringappServices.env();

    //获取Health相关data 并绘制图形
    getHealthData.then(function (responseData) {


        $scope.redisVersion = responseData.data.redis.version;
        $scope.db = responseData.data.db.database;

        var data = [
            {label: "占用", data: responseData.data.diskSpace.free, color: "#DA5430"},
            {label: "空闲", data: responseData.data.diskSpace.total - responseData.data.diskSpace.free, color: "#68BC31"},
        ];

        function drawPieChart(placeholder, data, position) {
            $.plot(placeholder, data, {
                series: {
                    pie: {
                        show: true,
                        tilt: 0.8,
                        highlight: {
                            opacity: 0.25
                        },
                        stroke: {
                            color: '#fff',
                            width: 2
                        },
                        startAngle: 2
                    }
                },
                legend: {
                    show: true,
                    position: position || "ne",
                    labelBoxBorderColor: null,
                    margin: [-30, 15]
                }
                ,
                grid: {
                    hoverable: true,
                    clickable: true
                }
            })
        }

        drawPieChart(placeholder, data);

        placeholder.data('chart', data);
        placeholder.data('draw', drawPieChart);

        var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
        var previousPoint = null;

        placeholder.on('plothover', function (event, pos, item) {
            if (item) {
                if (previousPoint != item.seriesIndex) {
                    previousPoint = item.seriesIndex;
                    var tip = item.series['label'] + " : " + item.series['percent'] + '%';
                    $tooltip.show().children(0).text(tip);
                }
                $tooltip.css({top: pos.pageY + 10, left: pos.pageX + 10});
            } else {
                $tooltip.hide();
                previousPoint = null;
            }

        });
    });

    //获取metrics（应用基本指标）data

    getMetricsData.then(function (responseData) {
        $scope.metricsData = responseData.data;
    });

    //获取env 环境配置数据
    getEnvInfo.then(function (responseData) {
        $scope.envInfo = responseData.data;
    });




}]);




