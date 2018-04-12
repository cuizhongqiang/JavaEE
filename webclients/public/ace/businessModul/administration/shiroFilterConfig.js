
var shiroapp = angular.module('shiroapp', []);

/**
 * shiro配置请求接口
 */
shiroapp.service('shiroServices', function ($http) {
    var service = {
        submitForm: function (id, content) {
            return $http({
                method: 'PUT',
                url: rootReqUrl + '/shiroFilterConfigs/' + id,
                data: {configContent : content}
            });
        },
        getAll: function () {
            return $http({
                method: 'GET',
                url: rootReqUrl+'/shiroFilterConfigs '
            });
        }
    };
    return service;
});

/**
 * shiro配置管理
 */
shiroapp.controller('shiroFilterConfigCtrl', ['$scope', '$http', 'shiroServices', '$window', 'SpringDataRestAdapter', function ($scope, $http, shiroServices, $window, SpringDataRestAdapter) {

    var getAll = shiroServices.getAll();
    getAll.then(function successCallback(response) {
        // 请求成功执行代码
        var shiroFilterConfigs = response.data._embedded.shiroFilterConfigs;
        for (i in shiroFilterConfigs) {
            if (shiroFilterConfigs[i].configName == 'loginUrl') {
                $scope.loginUrl = shiroFilterConfigs[i].configContent;
            } else if (shiroFilterConfigs[i].configName == 'successUrl') {
                $scope.successUrl = shiroFilterConfigs[i].configContent;
            } else if (shiroFilterConfigs[i].configName == 'unauthorizedUrl') {
                $scope.unauthorizedUrl = shiroFilterConfigs[i].configContent;
            } else if (shiroFilterConfigs[i].configName == 'filterChain') {
                $scope.filterChain = eval("(" + shiroFilterConfigs[i].configContent + ")");
            }
        }
    }, function errorCallback(response) {
        // 请求失败执行代码
        alert('请求失败');
    });

    $scope.shiroConfigSubmit = function () {
        shiroServices.submitForm('loginUrl', $scope.loginUrl);
        shiroServices.submitForm('successUrl', $scope.successUrl);
        shiroServices.submitForm('unauthorizedUrl', $scope.unauthorizedUrl);
        var keyArray = [];
        var valueArray = [];
        $("input[name='filterConfigKey']").each(function(){
            keyArray.push($(this).val());
        });
        $("input[name='filterConfigValue']").each(function(){
            valueArray.push($(this).val());
        });
        var jsonDate = {};
        for (i in keyArray) {
            jsonDate[keyArray[i]] = valueArray[i];
        }
        shiroServices.submitForm('filterChain', JSON.stringify(jsonDate));

        swal({
            title: "保存成功",
            type: "success",
            confirmButtonColor: "#DD6B55",
            closeOnConfirm: false
        });
    }

}]);
