/**
 * 假期审批
 */

var approveapp = angular.module('approveapp', []);

//假期申请接口方法
approveapp.service('approveServices', function ($http) {
    var service = {
        listTasks: function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/workflow/execute/HolidayRequest/listTasks',
                data: param
            });
        },
        approveForm:function (param) { //假期审批
            return $http({
                method: 'POST',
                url: rootReqUrl + "/workflow/execute/HolidayRequest/approveForm",
                params: param
            });
        }
    };
    return service;
});


//审批 controller
approveapp.controller('approveCtrl', ['$scope', '$http', 'approveServices', '$window', function ($scope, $http, approveServices, $window) {
    //Editor引入

    var listTasks = approveServices.listTasks();
    var param = {};


    listTasks.then(function (data) {
        // 请求成功执行代码
        $scope.obj = data.data.content;
        console.log(data.data.content);
    });


    $scope.agree = function (id) {
        //
        param.taskId = id;
        param.approved = true;
        var approveForm = approveServices.approveForm(param);


        approveForm.then(function (data) {
            // 请求成功执行代码
            swal({
                title: "您已同意" + id + '的假期申请'
            }, function () {
                $window.location.reload();
            })
        });

    };
    $scope.refuse = function (id) {
        param.taskId = id;
        param.approved = false;
        var approveForm = approveServices.approveForm(param);

        approveForm.then(function (data) {
            // 请求成功执行代码
            swal({
                title: "您已拒绝" + id + '的假期申请'
            }, function () {
                $window.location.reload();
            })
        });
    };

    $scope.getImgByProcessId = function (processInstanceId) {
        swal({
            title: "",
            text: "",
            imageUrl: rootReqUrl + "/runtime/process-instances/" + processInstanceId + '/diagram',
            imageSize: '433x616'
        });
    }
}]);

