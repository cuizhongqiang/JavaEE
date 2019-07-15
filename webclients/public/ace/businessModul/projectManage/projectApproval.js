/**
 *
 * 项目管理
 */

var projectApprovalapp = angular.module('projectApprovalapp', []);

projectApprovalapp.controller('projectApprovalCtrl', ['$scope', '$http', 'indexServices', '$window', function ($scope, $http, indexServices, $window) {

    $scope.data = {
        current: "1"
    };
    $scope.actions =
        {
            setCurrent: function (param) {
                alert('1');
                console.log('1');
                $scope.data.current = param;
            }
        }

}]);