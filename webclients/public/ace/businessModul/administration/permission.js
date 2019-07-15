
var permissionapp = angular.module('permissionapp', []);

/**
 * 功能菜单请求接口
 */
permissionapp.service('permissionServices', function ($http) {
    var service = {
        insertForm: function (data) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/permissions',
                data: data
            });
        },
        getOne: function (id) {
            return $http({
                method: 'GET',
                url: rootReqUrl + "/permissions/" + id,
                headers : {
                    "Accept": "*/*"
                }
            });
        },
        updateForm: function (data) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/permission/update',
                data: data,
                headers : {
                    'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                }
            });
        },
        delete: function (id) {
            return $http({
                method: 'DELETE',
                url: rootReqUrl + "/permissions/" + id
            });
        }
    };
    return service;
});

/**
 * 功能菜单
 */
permissionapp.controller('permissionCtrl', ['$scope', '$http', '$compile', 'permissionServices', '$window', 'SpringDataRestAdapter', function ($scope, $http, $compile, permissionServices, $window, SpringDataRestAdapter) {

    var dataTable = $('#permission-table').jqGrid({
        "autowidth":true,
        "hoverrows":false,
        "viewrecords":false,
        "gridview":true,
        "mtype":"GET",
        "url": rootReqUrl + "/permission/jqGridList",
        "prmNames":"none",
        "treeGrid":true,
        "ExpandColumn":"perName",
        "height":"auto",
        "sortname":"sort",
        "scrollrows":true,
        "treedatatype":"json",
        "treeGridModel":"adjacency",
        "loadonce":false,//如果为ture则数据只从服务器端抓取一次，之后所有操作都是在客户端执行，翻页功能会被禁用
        "rowNum":1000,
        "treeReader":{
            "parent_id_field":"pid",
            "level_field":"level",
            "leaf_field":"isLeaf",
            "expanded_field":"expanded"
        },
        "datatype":"json",
        "colModel":[
            {
                "name":"perName",
                "label":"名称"
            },{
                "name":"perCode",
                "label":"编码"
            },{
                "name":"perType",
                "label":"类别",
                "search":false
            },{
                "name":"perUrl",
                "label":"资源路径"
            },{
                "name":"status",
                "label":"状态",
                "search":false,
                "formatter":function (cellvalue, options, rowObject) {
                    var returnStr = "<div class='btn-group btn-group-sm'>";
                    if (cellvalue == 1) {
                        returnStr += "<button type='button' class='btn btn-info'>启用</button>";
                        returnStr += "<button type='button' class='btn btn-default' name='updateStatusBtn'>" + cumSpace(7) + "</button>";
                    } else {
                        returnStr += "<button type='button' class='btn btn-default' name='updateStatusBtn'>" + cumSpace(7) + "</button>";
                        returnStr += "<button type='button' class='btn btn-warning'>禁用</button>";
                    }
                    returnStr += "</div>";
                    return returnStr;
                }
            },{
                "name":"id",
                "label":"操作",
                "search":false,
                "align":"center",
                "formatter":function (cellvalue, options, rowObject) {
                    var result = "<div class='btn-group btn-group-sm'>";
                    result += "<a href='javascript:;' class='btn-xs btn-primary' title='编辑' value='" + cellvalue + "'><span class='glyphicon glyphicon-pencil'></span></a> ";
                    result += "<a href='javascript:;' class='btn-xs btn-danger' title='删除' value='" + cellvalue + "'><span class='glyphicon glyphicon-remove'></span></a>";
                    result += "</div>";
                    return result;
                }
            }
        ],
        "pager":"false",
        "loadComplete":function (data) {
            if (typeof data == "undefined" || data == null) {
                return false;
            }
            var pidArray = [];
            for (i = 0; i < data.rows.length; i++) {
                if (data.rows[i].pid != null) {
                    pidArray.push(data.rows[i].pid);
                }
            }
            for (i = 0; i < data.rows.length; i++) {
                if (pidArray.includes(data.rows[i].id)) {
                    data.rows[i].isLeaf = false;
                } else {
                    data.rows[i].isLeaf = true;
                }
                data.rows[i].expanded = true;
            }
            this.addJSONData(data);
        },
        gridComplete: function(){
            var ids = $(this).jqGrid('getDataIDs');
            for (var i = 0; i < ids.length; i++) {
                var rowData = $(this).getRowData(ids[i]);
            }
        }
    });

    /*//搜索
    dataTable.jqGrid("filterToolbar", {
        autoSearch: 'select',
        beforeSearch: function () {
        },
        afterSearch: function () {
        },
        searchOnEnter: true  //回车触发搜索
    });*/

    $scope.toInsert = function () {
        $scope.myModalLabel = "新增";
        $('#mainForm')[0].reset();
        $scope.model = {};
        var id = dataTable.getGridParam('selrow');
        var level = dataTable.getRowData(id).level;
        if (id == null) {
            $scope.model.level = 0;
        } else {
            $scope.model.level = level + 1;
            $scope.model.pid = id;
        }
        $('#myModal').modal('show');
    }

    var bootstrapValidator = $("#mainForm").data('bootstrapValidator');
    //Modal验证销毁重构
    $('#myModal').on('hidden.bs.modal', function() {
        bootstrapValidator.destroy();
        formValidator();
    });
    $("#submitButton").click(function() {
        /*手动验证表单，当是普通按钮时*/
        bootstrapValidator.validate();
        if (!bootstrapValidator.isValid()) {
            return false;
        }
        $("#submitButton").button('loading');
        var permissionSubmit;
        if ($scope.model.id == null) {
            permissionSubmit = permissionServices.insertForm($("#mainForm").serializeObject());//新增
        } else {
            permissionSubmit = permissionServices.updateForm($("#mainForm").serialize());//修改
        }
        permissionSubmit.then(function (data) {
            $("#submitButton").popover('show');
            setTimeout(function() {
                $("#submitButton").popover('hide');
                $('#myModal').modal('hide');
                $("#submitButton").button('reset');
                dataTable.trigger("reloadGrid");
            }, 1000);
        }).catch(function (result) {
            alert("error");
        });
    });

    $("#permission-table tbody").on("click", "button[name='updateStatusBtn']", function (event) {
        setTimeout(function() {
            var id = dataTable.getGridParam('selrow');
            var rowData = dataTable.getRowData(id);
            var status = rowData.status.indexOf("启用") > 0 ? 0 : 1;
            var updateStatus = permissionServices.updateForm($.param({id:id,status:status}));
            updateStatus.then(function (response) {
                dataTable.trigger("reloadGrid");
            });
        }, 200);
    });

    $("#permission-table tbody").on("click", "a[title='编辑']", function (event) {
        $('#mainForm')[0].reset();
        var id = $(this).attr('value');
        var theOne = permissionServices.getOne(id);
        theOne.then(function (response) {
            $scope.myModalLabel = "修改";
            $scope.model = response.data;
            $scope.model.id = id;
            $('#myModal').modal('show');
            setTimeout(function() {//remote请求--"延迟"+"主动"--验证
                bootstrapValidator.validate();
            }, 1000);
        });
    });

    $("#permission-table tbody").on("click", "a[title='删除']", function (event) {
        var id = $(this).attr('value');
        swal({
            title: "您确定要删除吗？",
            text: "您确定要删除这条数据？",
            type: "warning",
            showCancelButton: true,
            closeOnConfirm: false
        }, function() {
            var permissionDelete = permissionServices.delete(id);
            permissionDelete.then(function successCallback(response) {
                swal("操作成功!", "已成功删除数据！", "success");
                dataTable.trigger("reloadGrid");
            }, function errorCallback(response) {
                swal("OMG", "删除操作失败了!", "error");
            });
        });
    });
}]);
