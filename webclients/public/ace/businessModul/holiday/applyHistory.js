//审批历史

var applyHistoryapp = angular.module('applyHistoryapp', []);

applyHistoryapp.controller('applyHistoryCtrl', ['$scope', '$http', 'indexServices', '$window','i18nService', function ($scope, $http, indexServices, $window,i18nService) {

    var getPage = function (curPage, pageSize, data,isFirstLoad) {
        var firstRow = (curPage - 1) * pageSize;

        var listHistory = indexServices.listHistory();

        listHistory.then(function (data) {
            // 请求成功执行代码

            $scope.myData =  data.data.content.slice(firstRow, firstRow + pageSize);
            console.log($scope.myData);
        });

    };

    i18nService.setCurrentLang("zh-cn");
    $scope.gridOptions = {
        data: 'myData',
        columnDefs: [

            {
                field: 'variables.employee',
                displayName: '申请上级',
            },
            {
                field: "variables.nrOfHolidays",
                displayName: '请假天数/天',
            },
            {
                field: "createTime",
                displayName: '审批时间',
                name:'createTime',
                cellTemplate:'<div class="ui-grid-cell-contents" >{{COL_FIELD |date:"yyyy-MM-dd HH:mm:ss"}}</div>'
            },
            {
                field: "variables.description",
                displayName: '请假原因',
            },
            {
                field: "variables.approved",
                displayName: '审批结果',
                enableColumnMenu: false
            },
        ],

        enableSorting: true, //是否排序
        useExternalSorting: false, //是否使用自定义排序规则
        enableGridMenu: true, //是否显示grid 菜单
        showGridFooter: true, //是否显示grid footer
        enableHorizontalScrollbar :  0, //grid水平滚动条是否显示, 0-不显示  1-显示
        enableVerticalScrollbar : 1, //grid垂直滚动条是否显示, 0-不显示  1-显示

        //-------- 分页属性 ----------------
        enablePagination: true, //是否分页，默认为true
        enablePaginationControls: true, //使用默认的底部分页
        paginationPageSizes: [10, 15, 20], //每页显示个数可选项
        paginationCurrentPage:1, //当前页码
        paginationPageSize: 20, //每页显示个数
        //paginationTemplate:"<div></div>", //自定义底部分页代码
        totalItems : 100, // 总数量
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


}]);
