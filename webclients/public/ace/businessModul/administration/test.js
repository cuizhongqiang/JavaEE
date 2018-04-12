/**
 *
 * 项目管理
 */

var testapp = angular.module('testapp', []);


testapp.controller('testCtrl', ['$scope', '$http', 'indexServices', '$window','i18nService','$filter', function ($scope, $http, indexServices, $window,i18nService,$filter) {


    var listCatalogs = indexServices.listCatalogs();
    var getPage = function (curPage, pageSize, data,isFirstLoad) {
        var firstRow = (curPage - 1) * pageSize;

        if(!isFirstLoad){
            $scope.myData = data.data.slice(firstRow, firstRow + pageSize);

        }else {

            var dataDictionaries = indexServices.dataDictionaries();

            dataDictionaries.then(function (data) {
                // 请求成功执行代码
                console.log(data.data._embedded.dataDictionaries);
                $scope.myData =  data.data._embedded.dataDictionaries.slice(firstRow, firstRow + pageSize);
            });
        }




    };

    listCatalogs.then(function (data) {
        // 请求成功执行代码
        $scope.listCatalogsData = data.data;
    });


    i18nService.setCurrentLang("zh-cn");
    $scope.gridOptions = {
        data: 'myData',
//            enableFiltering: true,

        columnDefs: [

            {field: 'catalog'},
            {field: "name"},
            {field: "content"},
            {
                field: 'accountId',
                displayName: '操作',
                cellTemplate: '<div class="ui-grid-cell-contents" style="margin-top: -3px"> <button ng-click="grid.appScope.check()" class="btn btn-xs btn-success"><i class="ace-icon fa fa-trash-o bigger-100"></i></button></div>',
                enableCellEdit: false,// 是否可编辑
                enableColumnMenu: false,// 是否显示列头部菜单按钮

            },
        ],

        enableSorting: true, //是否排序
        useExternalSorting: false, //是否使用自定义排序规则
        enableGridMenu: true, //是否显示grid 菜单
        showGridFooter: true, //是否显示grid footer
        enableHorizontalScrollbar :  1, //grid水平滚动条是否显示, 0-不显示  1-显示
        enableVerticalScrollbar : 0, //grid垂直滚动条是否显示, 0-不显示  1-显示

        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage:1, //当前页码
        paginationPageSize: 10, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems : 0, // 总数量
        useExternalPagination: true,//是否使用分页按钮

        //---------------api---------------------
        onRegisterApi: function(gridApi) {
            $scope.gridApi = gridApi;
            //分页按钮事件
            gridApi.pagination.on.paginationChanged($scope,function(newPage, pageSize) {
                if(getPage) {
                    getPage(newPage, pageSize);
                }
            });
            //行选中事件
            $scope.gridApi.selection.on.rowSelectionChanged($scope,function(row,event){
                if(row){
                    $scope.testRow = row.entity;
                }
            });

            gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
                alert('旧值' + oldValue + '已被修改为' + newValue);
//                        $scope.msg.lastCellEdited = 'edited row id:' + rowEntity.id + ' Column:' + colDef.name + ' newValue:' + newValue + ' oldValue:' + oldValue ;
//                        $scope.$apply();
            });
        }
    };



    getPage(1, $scope.gridOptions.paginationPageSize,'',true);




    $scope.putDictionariesData = function (catalog) {

        $scope.isFirstLoad = false;
        var param = {};
        param.catalog = catalog;
        var findByCatalog = indexServices.findByCatalog(param);
        findByCatalog.then(function (data) {
            // 请求成功执行代码
            getPage(1, $scope.gridOptions.paginationPageSize,data,false);
        });


    };

    $scope.check = function () {
        alert('1');
    }


    $scope.dataDictionarySubmission = function () {
        swal({
            title: "<small>增加</small>",
            text:
            "id <input type='text' name='myinput' id='id'>"
            +"catalog <input type='text' name='myinput' id='catalog'>"
            +"content <input type='text' name='myinput' id='content'>",
            html: true,
            type: "prompt"
        }, function(){
            $scope.id = document.getElementById('id').value;
            $scope.catalog = document.getElementById('catalog').value;
            $scope.content = document.getElementById('content').value;

            if ($scope.id && $scope.catalog && $scope.content) {

                var data = {};
                data.catalog = $scope.catalog;
                data.content = $scope.content;
                data.name = $scope.id;

                var id = $scope.id;

                var dataDictionarySubmission = indexServices.dataDictionarySubmission(data, id);

                dataDictionarySubmission.then(function (data) {
                    // 请求成功执行代码
                    swal({
                            title: "字典已添加",
                            type: "success",
                            confirmButtonColor: "#DD6B55",
                            closeOnConfirm: false
                        },
                        function () {
                            $window.location.reload();

                        });
                });

            } else {
                setTimeout("swal(\"请填写必要信息\")",500);
            }


        })

    }
}]);

