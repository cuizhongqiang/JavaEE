/**
 * 用户管理请求接口
 */

var sysUserapp = angular.module('sysUserapp', []);

sysUserapp.service('sysUserServices', function ($http) {
    var service = {
        insertForm: function (data) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/authUsers/',
                data: data
            });
        },
        getOne: function (url) {
            return $http({
                method: 'GET',
                url: url
            });
        },
        updateForm: function (data) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/authuser/update',
                data: data,
                headers : {
                    'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                }
            });
        },
        delete: function (url) {
            return $http({
                method: 'DELETE',
                url: url
            });
        },
        validatePassword : function (data) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/authuser/validatePassword',
                data: data,
                headers : {
                    'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8'
                }
            });
        }
    };
    return service;
});

/**
 * 用户管理
 */
sysUserapp.controller('sysUserCtrl', ['$scope', '$http', '$compile', 'sysUserServices', '$window', 'SpringDataRestAdapter', function ($scope, $http, $compile, sysUserServices, $window, SpringDataRestAdapter) {

    var dataTable = $('#sysUser-table').dataTable({
        autoWidth: false, //禁用自动调整列宽
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        stripeClasses: ["odd", "even"], //为奇偶行加上样式，兼容不支持CSS伪类的场合
        orderMulti: false, //启用多列排序
        order: [], //取消默认排序查询,否则复选框一列会出现小箭头
        renderer: "bootstrap", //渲染样式：Bootstrap和jquery-ui
        pagingType: "simple_numbers", //分页样式：simple,simple_numbers,full,full_numbers
        bLengthChange: true,
        ajax: function (data, callback) {
            $.ajax({
                type: "GET",
                url: rootReqUrl + "/authUsers",
                cache: false, //禁用缓存
                dataType: "json",
                success: function (result) {
                    var returnData = {};
                    returnData.data = result._embedded.authUsers;
                    callback(returnData);
                }
            });
        },
        columns: [
            {
                "sClass": "text-center",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox" name="rowCheckbox"/>';
                },
                "bSortable": false
            },
            {"data": "username"},
            {"data": "realName"},
            {"data": "mobile"},
            {"data": "email"},
            {"data": "lastLoginTime"},
            {
                "data": "status",
                "render": function (data, type, row, meta) {
                    var returnStr = "<div class='btn-group btn-group-sm'>";
                    if (data == 1) {
                        returnStr += "<button type='button' class='btn btn-info'>启用</button>";
                        returnStr += "<button type='button' class='btn btn-default' ng-click=\"updateStatus('" + row._links.authUser.href + "', '" + data + "')\">" + cumSpace(7) + "</button>";
                    } else {
                        returnStr += "<button type='button' class='btn btn-default' ng-click=\"updateStatus('" + row._links.authUser.href + "', '" + data + "')\">" + cumSpace(7) + "</button>";
                        returnStr += "<button type='button' class='btn btn-warning'>禁用</button>";
                    }
                    returnStr += "</div>";
                    return returnStr;
                },
                "bSortable": false,
                "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                    $compile(nTd)($scope);
                }
            },
            {
                "data": "_links.authUser.href",
                "sClass": "text-center",
                "bSortable": false,
                "render": function (data, type, full, meta) {
                    var result = "<div class='btn-group btn-group-sm'>";
                    /*result += "<a href='javascript:;' class='btn-xs btn-success' onclick=\"toUpdate('" + id + "', 'detail')\" title='查看'><span class='glyphicon glyphicon-search'></span></a> ";*/
                    result += "<a href='javascript:;' class='btn-xs btn-primary' ng-click=\"toUpdate('" + data + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a> ";
                    result += "<a href='javascript:;' class='btn-xs btn-danger' ng-click=\"toDelete('" + data + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
                    result += "</div>";
                    return result;
                },
                "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                    $compile(nTd)($scope);
                }
            }
        ]
    });

    $('#sysUser-table tbody').on( 'click', 'tr', function (event) {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
            this.cells[0].childNodes[0].checked = false;
            $scope.selected = null;
        } else {
            dataTable.$('tr.selected').removeClass('selected');
            $("input[name='rowCheckbox']").attr("checked", false);
            $(this).addClass('selected');
            this.cells[0].childNodes[0].checked = true;
            $scope.selected = dataTable.fnGetData($(this));
        }
    });

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
        var sysUserSubmit;
        if ($.isEmptyObject($scope.model)) {
            sysUserSubmit = sysUserServices.insertForm($("#mainForm").serializeObject());//新增
        } else {
            sysUserSubmit = sysUserServices.updateForm($("#mainForm").serialize());//修改
        }
        sysUserSubmit.then(function (data) {
            $("#submitButton").popover('show');
            setTimeout(function() {
                $("#submitButton").popover('hide');
                $('#myModal').modal('hide');
                $("#submitButton").button('reset');
                dataTable.api().ajax.reload();
            }, 1000);
        }).catch(function (result) {
            alert("error");
        });
    });
    
    $scope.toInsert = function () {
        $scope.myModalLabel = "新增";
        $('#mainForm')[0].reset();
        $scope.model = {};
        $('#myModal').modal('show');
    }

    $scope.toUpdate = function (url) {
        $('#mainForm')[0].reset();
        var theOne = sysUserServices.getOne(url);
        theOne.then(function (response) {
            $scope.myModalLabel = "修改";
            var sysUser = response.data;
            $scope.model = {
                url : url,
                username : sysUser.username,
                password : sysUser.password,
                confirmPassword : sysUser.password,
                realName : sysUser.realName,
                department : sysUser.department,
                birthday : sysUser.birthday,
                gender : sysUser.gender,
                email : sysUser.email,
                mobile : sysUser.mobile,
                tel : sysUser.tel,
                id : url.substring(url .lastIndexOf("\/") + 1, url.length)
            };

            $('#myModal').modal('show');
            setTimeout(function() {//remote请求--"延迟"+"主动"--验证
                bootstrapValidator.validate();
            }, 1000);
        });
    }

    $scope.toDelete = function (url) {
        swal({
            title: "您确定要删除吗？",
            text: "您确定要删除这条数据？",
            type: "warning",
            showCancelButton: true,
            closeOnConfirm: false
        }, function() {
            var sysUserDelete = sysUserServices.delete(url);
            sysUserDelete.then(function successCallback(response) {
                swal("操作成功!", "已成功删除数据！", "success");
                dataTable.api().ajax.reload();
            }, function errorCallback(response) {
                swal("OMG", "删除操作失败了!", "error");
            });
        });
    }

    $scope.updateStatus = function (url, data) {
        var id = url.substring(url .lastIndexOf("\/") + 1, url.length);
        var status = data == 1 ? 0 : 1;
        var updateStatus = sysUserServices.updateForm($.param({id:id,status:status}));
        updateStatus.then(function (response) {
            dataTable.api().ajax.reload();
        });
    }

    $scope.toUpdatePwd = function () {
        if ($scope.selected == null) {
            swal("提示", "请选择一行!", "warning");
        } else {
            $('#updatePwdModal').modal('show');
        }
    }

    var bootstrapValidator2 = $("#updatePwdForm").data('bootstrapValidator');
    //Modal验证销毁重构
    $('#updatePwdModal').on('hidden.bs.modal', function() {
        $('#updatePwdForm')[0].reset();
        bootstrapValidator2.destroy();
        form2Validator();
    });
    $("#updatePwdSubmitButton").click(function() {
        /*手动验证表单，当是普通按钮时*/
        bootstrapValidator2.validate();
        if (!bootstrapValidator2.isValid()) {
            return false;
        }
        var url = $scope.selected._links.authUser.href;
        var id = url.substring(url .lastIndexOf("\/") + 1, url.length);
        var validPwd = sysUserServices.validatePassword($.param({id:id,oldPassword:$("#oldPassword").val()}));
        validPwd.then(function (response) {
            if (response.data.valid) {
                $("#updatePwdSubmitButton").button('loading');
                var updatePwdSubmit = sysUserServices.updateForm($.param({id:id,password:$("#newPassword").val()}));
                updatePwdSubmit.then(function (data) {
                    $("#updatePwdSubmitButton").popover('show');
                    setTimeout(function() {
                        $("#updatePwdSubmitButton").popover('hide');
                        $('#updatePwdModal').modal('hide');
                        $("#updatePwdSubmitButton").button('reset');
                    }, 1000);
                });
            } else {
                $("#pwdAlert").show();
            }
        });
    });

}]);
