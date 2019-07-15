//报销审批 申请
app.controller('paymentApplicationCtrl', ['$scope', '$http', 'indexServices', '$window', function ($scope, $http, indexServices, $window) {

    $scope.paymentSubmission = function () {

        if ($scope.starter && $scope.billCode) {
            var param = {};
            param.billCode = $scope.billCode;
            param.starter = $scope.starter;
            var startProcess = indexServices.startProcess(param);

            startProcess.then(function (data) {
                // 请求成功执行代码
                swal({
                        title: "单据申请已提交",
                        type: "success",
                        confirmButtonColor: "#DD6B55",
                        closeOnConfirm: false
                    },
                    function () {
                        $window.location.reload();
                    });
            });

        } else {
            swal("请填写必要信息")
        }
    }



}]);


