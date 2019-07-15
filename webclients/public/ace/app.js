//angluar/Js

let app = angular.module('indexApp', ['ui.router', 'ngResource', 'spring-data-rest','ui.grid','ui.grid.selection','ui.grid.edit',
    'ui.grid.exporter','ui.grid.pagination','ui.grid.resizeColumns','ui.grid.autoResize','ui.router.state.events','oc.lazyLoad']);

app.run(['$rootScope', '$state', '$stateParams', '$window',
    function($rootScope, $state, $stateParams, $window) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;

        /**
         * 增加页面标题，通过配置读取路由（$state）中 title, 之后需要加title直接在路由中配置即可
         * @type {[type]}
         */
        $rootScope.$on('$stateChangeSuccess', function(evt, state, params, fromState, fromParams){
            let defautlTitle = '大宗物联', title;
            title = title !== undefined ? title
                : state.title !== undefined ? state.title : defautlTitle;
            $window.document.title = title;
        });
    }
]);


/**
 * http 拦截器  统一处理http请求
 */
app.factory("httpInterceptor", ["$q", "$rootScope", function ($q, $rootScope) {
    return {
        request: function (config) {
            $rootScope.loading = true;
            return config || $q.when(config);
        },
        requestError: function (rejection) {
            $rootScope.loading = false;
            return $q.reject(rejection)
        },
        response: function (response) {
            $rootScope.loading = false;
            return response || $q.when(response);
        },
        responseError: function (rejection) {
            $rootScope.loading = false;
            if(rejection.status === 400 || rejection.status === 401 || rejection.status === 404){
                sweetAlert("哎呦……", "出错了！","error");
                return $q.reject(rejection);
            }else {
                sweetAlert("哎呦……", "服务器未启动！","error");
                return $q.reject(rejection);
            }
        }
    };
}]);

app.config(function ($stateProvider, $urlRouterProvider,$httpProvider) {
    $httpProvider.defaults.withCredentials = true;
    $httpProvider.interceptors.push('httpInterceptor');

    function isIE11(){ if((/Trident\/7\./).test(navigator.userAgent))return true; else return false;}

    //initialize get if not there --add by cuizhongqiang 为解决getOne的缓存问题
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    //disable IE ajax request caching

    if(isIE11()){
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    }
    // extra
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

    $urlRouterProvider.otherwise('/sysMonitoring');

    $stateProvider
        .state('index', {
            title: '首页',
            url: '/index',
            templateUrl: 'home.html'
        })
        .state('apply', {
            title: '假期申请',
            url: '/apply',
            controller: 'applyCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/holiday/apply.js");
                }]
            },
            templateUrl: 'businessModul/holiday/apply.html'
        })
        .state('approve', {
            url: '/approve',
            controller: 'approveCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/holiday/approve.js");
                }]
            },
            title: '假期审批',
            templateUrl: 'businessModul/holiday/approve.html'
        })
        .state('applyHistory', {
            title: '假期审批',
            controller: 'applyHistoryCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/holiday/applyHistory.js");
                }]
            },
            url: '/applyHistory',
            templateUrl: 'businessModul/holiday/applyHistory.html'
        })
        .state('dataDictionary', {
            url: '/dataDictionary',
            controller: 'dataDictionaryCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/dataDictionary/dataDictionary.js");
                }]
            },
            title: '数据字典管理',
            templateUrl: 'businessModul/dataDictionary/dataDictionary.html'
        })
        .state('paymentApplication', {
            url: '/paymentApplication',
            templateUrl: 'businessModul/documentReview/paymentApplication.html'
        })
        .state('billApproval', {
            url: '/billApproval',
            templateUrl: 'businessModul/documentReview/billApproval.html'
        })
        .state('flowable', {
            url: '/flowable',
            templateUrl: 'businessModul/administration/flowable.html'
        })
        .state('projectApproval', {
            url: '/projectApproval',
            controller: 'projectApprovalCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/projectManage/projectApproval.js");
                }]
            },
            templateUrl: 'businessModul/projectManage/projectApproval.html'
        })
        .state('test', {
            url: '/test',
            controller: 'testCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/test.js");
                }]
            },
            templateUrl: 'businessModul/administration/test.html'
        })
        .state('orgStructure', {
            url: '/orgStructure',
            controller: 'orgStructureCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/orgStructure.js");
                }]
            },
            templateUrl: 'businessModul/administration/orgStructure.html'
        })
        .state('shiroFilterConfig', {
            url: '/shiroFilterConfig',
            controller: 'shiroFilterConfigCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/shiroFilterConfig.js");
                }]
            },
            templateUrl: 'businessModul/administration/shiroFilterConfig.html'
        })
        .state('sysUser', {
            url: '/sysUser',
            controller: 'sysUserCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/sysUser.js");
                }]
            },
            templateUrl: 'businessModul/administration/sysUser.html'
        })
        .state('permission', {
            url: '/permission',
            controller: 'permissionCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/permission.js");
                }]
            },
            templateUrl: 'businessModul/administration/permission.html'
        })
        .state('permissionDistribute', {
            url: '/permissionDistribute',
            controller: 'permissionDistributeCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/permissionDistribute.js");
                }]
            },
            title:'权限管理',
            templateUrl: 'businessModul/administration/permissionDistribute.html'
        })
        .state('sysMonitoring', {
            url: '/sysMonitoring',
            controller: 'sysMonitoringCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/administration/sysMonitoring.js");
                }]
            },
            title:'系统监控',
            templateUrl: 'businessModul/administration/sysMonitoring.html'
        })
        .state('manageMessage', {
            url: '/manageMessage',
            controller: 'manageMessageCtrl',
            resolve:{
                deps:["$ocLazyLoad",function($ocLazyLoad){
                    return $ocLazyLoad.load("businessModul/message/manageMessage.js");
                }]
            },
            title:'消息管理',
            templateUrl: 'businessModul/message/manageMessage.html'
        })


});



app.factory('dataService', ['$window', function ($window) {
    return {        //存储单个属性
        set: function (key, value) {
            $window.localStorage[key] = value;
        },        //读取单个属性
        get: function (key, defaultValue) {
            return $window.localStorage[key] || defaultValue;
        },        //存储对象，以JSON格式存储
        setObject: function (key, value) {
            $window.localStorage[key] = JSON.stringify(value);//将对象以字符串保存
        },        //读取对象
        getObject: function (key) {
            return JSON.parse($window.localStorage[key] || '{}');//获取字符串并解析成对象
        }

    };
}]);


app.service('indexServices', function ($http) {
    let service = {
        getNgTableData: function (param) {
            return $http.get(rootReqUrl + '/repository/process-definitions', param, {cache: false});

        },

        dataDictionarySubmission: function (data, id) {
            return $http({
                method: 'PUT',
                url: rootReqUrl + '/dataDictionaries/' + id,
                data: data
            });
        },
        startProcess: function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/workflow/execute/BillCheck/startProcess',
                params: param
            });
        },
        approveTask: function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/workflow/advance/process/approveTask',
                params: param
            });
        },
        listCatalogs:function (param) { //数据字典-查询数据字典类别
            return $http({
                method: 'GET',
                url: rootReqUrl + '/dataDictionaries/search/listCatalogs',
                params: param
            });
        },
        dataDictionaries:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/dataDictionaries',
                params: param
            });
        },
        findByCatalog:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + "/dataDictionaries/search/findByCatalog",
                params: param
            });

        },

        authuser:function (param) { //检测当前用户登录状态
            return $http({
                method: 'GET',
                url: rootReqUrl + "/authuser/",
                params: param
            });
        },
        logout:function (param) { //退出登录接口
            return $http({
                method: 'GET',
                url: rootReqUrl + "/authuser/logout",
                params: param
            });
        },
        listHistory:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + "/workflow/execute/HolidayRequest/listHistory",
                params: param
            });
        },
        getTodoTasksNoticeByRoles:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + "/workflow/advance/process/getTodoTasksNoticeByRoles",
                params: param
            });
        }

    };
    return service;
});


app.controller('indexAppCtrl', ['$scope', '$http', 'indexServices', '$window', function ($scope, $http, indexServices, $window) {
    //

    // noinspection JSAnnotator
    let getNgTableData = indexServices.getNgTableData();
    let authuser = indexServices.authuser();
    let getTodoTasksNoticeByRoles = indexServices.getTodoTasksNoticeByRoles();

    //未登录踢出逻辑
    authuser.then(function (data) {
        if (data.data.username === 'null') {

            swal({
                title: "请返回 并登录",
                type: "warning",
                confirmButtonColor: "#DD6B55",
            }, function () {
                window.location = 'login.html';
            });

        } else {
            $scope.userName = data.data.username;
        }
    });


    getNgTableData.then(function (data) {
        $scope.data = data.data.data;
    });

    getTodoTasksNoticeByRoles.then(function (data) {
        $scope.todoData = data.data.content;
    });

    $scope.delete = function (id) {
        swal({
            title: "你确定？",
            text: "您将删除这个工作流！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "是的，删除！",
            closeOnConfirm: false
        }, function () {
            $window.location.reload();
        })
    };
    $scope.check = function (id) {
        swal({
            title: "",
            text: "",
            imageUrl: rootReqUrl + "/manage/flowable/processes/" + id,
            imageSize: '380x380'
        });
    };

    $scope.logOut = function () {
        let logout = indexServices.logout();
        logout.then(function () {
            window.location = 'login.html';
        });
    }
}]);

app.controller('homeCtrl', ['$scope', '$http', 'indexServices', '$window', function ($scope, $http, indexServices, $window) {
    //

    $scope.uploadFile = function(){
        let form = new FormData();
        let file = document.getElementById("fileUpload").files[0];
        form.append('file', file);
        if(file){
            $http({
                method: 'POST',
                url: rootReqUrl+'/workflow/advance/process/deployProcessDefinitionByFile',
                data: form,
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity
            }).then(function (data) {
                alert('上传成功');
            })
        }else {
            alert('请选择文件');
        }

    }

}]);









