
var orgStructureapp = angular.module('orgStructureapp', []);

/**
 * 组织结构配置
 */

//组织结构配置接口
orgStructureapp.service('orgStructureServices', function ($http) {
    var service = {
        setConfigData: function (data) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/configCenters',
                data: data
            });
        },
        getConfigData:function (id) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/configCenters/'+ id
            });
        }
    };
    return service;
});

//组织结构配置控制器
orgStructureapp.controller('orgStructureCtrl', ['$scope', '$http', 'orgStructureServices', '$window', function ($scope, $http, orgStructureServices, $window) {

    let getConfigData = orgStructureServices.getConfigData("orgStructureData");



    getConfigData.then(function (data) {
        // 请求成功执行代码
        var base = new Base64();
        var result = base.decode(data.data.content);
        result = JSON.parse(result);
        $('#treeDemo').jstree({
            // 引入插件、
            'plugins': [ 'types', 'themes', 'contextmenu',"dnd","checkbox","state"],
            "types": {
                "default" : {
                    "icon" : false  // 删除默认图标
                },
            },
            'core': {
                "themes": {
                    "stripes" : true
                },
                'multiple': true,  // 可否多选
                'data': result,  // 生成节点的数据，nodeData是后台返回的JSON数据
                'check_callback': true
            }
        });
    });

    $scope.keepOrgStructureData = function () {
        let v = $('#treeDemo').jstree(true).get_json('#', {flat: true});
        let mytext = JSON.stringify(v);


        var base = new Base64();
        var result = base.encode(mytext);


        let data = {};
        data.catalog = "TEXT";
        data.content = result;
        data.name = "orgStructureData";
        let setConfigData = orgStructureServices.setConfigData(data);

        setConfigData.then(function (data) {
            // 请求成功执行代码
            swal("数据保存成功！", "","success")
        });
    }

}]);

