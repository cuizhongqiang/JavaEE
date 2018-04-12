//报销审批 审批

app.controller('billApprovalCtrl', ['$scope', '$http', 'indexServices', '$window', function ($scope, $http, indexServices, $window) {

    $('#dynamic-table').dataTable({
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
        ajax: function (data, callback) {
            $.ajax({
                type: "GET",
                url: rootReqUrl + "/workflow/advance/process/getTasks?assignee=lisier",
                cache: false, //禁用缓存
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};
                    returnData.data = result.content;
                    callback(returnData);
                }
            });
        },
        "columnDefs": [
            {
                "targets": 3,
                "render": function (data, type, full, meta) {
                    return "<a id=\"agreeButton\" class=\"tooltip-info\" data-rel=\"tooltip\" title=\"View\"><span class=\"red\"><i class=\"fa fa-thumbs-o-up bigger-120\"></i></span></a>" +
                        "<a style='margin-left: 20px' id=\"refuseButton\" class=\"tooltip-info\" data-rel=\"tooltip\" title=\"View\"><span class=\"red\"><i class=\"fa fa-thumbs-o-down bigger-120\"></i></span></a>"
                }
            }
        ],

        //列表表头字段
        columns: [
            {"data": "id"},
            {
                "data": function (obj) {
                    return getMyDate(obj.createTime);
                }
            },
            {"data": "name"}
        ]
    });


    function getMyDate(time) {
        if (typeof(time) == "undefined") {
            return "";
        }
        var oDate = new Date(time),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oHour = oDate.getHours(),
            oMin = oDate.getMinutes(),
            oSen = oDate.getSeconds(),
            oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间

        return oTime;
    }

    //补0操作,当时间数据小于10的时候，给该数据前面加一个0
    function getzf(num) {
        if (parseInt(num) < 10) {
            num = '0' + num;
        }
        return num;
    }

    //同意
    $("#dynamic-table tbody").on("click", "#agreeButton", function () {
        //获取行
        var row = $("table#dynamic-table tr").index($(this).closest("tr"));
        //获取某列（从0列开始计数）的值
        var taskId = $("table#dynamic-table").find("tr").eq(row).find("td").eq(0).text();
        //var job = $("table#dynamic-table").find("tr").eq(row).find("td").eq(1).text();

        swal({
            title: "审批同意",
            text: "您可以输入您的审批意见",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: "请输入..."
        }, function (inputValue) {
            if (inputValue === false) {
                return false;
            }
//                        if (inputValue === "") {
//                            swal.showInputError("内容不能为空！");
//                            return false;
//                        }
            approveTask(taskId, true, inputValue);
        })
    });

    //拒绝
    $("#dynamic-table tbody").on("click", "#refuseButton", function () {
        //获取行
        var row = $("table#dynamic-table tr").index($(this).closest("tr"));
        //获取某列（从0列开始计数）的值
        var taskId = $("table#dynamic-table").find("tr").eq(row).find("td").eq(0).text();
        //var job = $("table#dynamic-table").find("tr").eq(row).find("td").eq(1).text();
        swal({
            title: "审批拒绝",
            text: "您可以输入您的审批意见",
            type: "input",
            showCancelButton: true,
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: "请输入..."
        }, function (inputValue) {
            if (inputValue === false) {
                return false;
            }
//                        if (inputValue === "") {
//                            swal.showInputError("内容不能为空！");
//                            return false;
//                        }
            approveTask(taskId, false, inputValue);
        })
    });


    /**
     * 审批接口方法
     */
    function approveTask(taskId, approved, opinion) {
        var param = {};
        param.taskId = taskId;
        param.approver = 'lisier';
        param.approved = approved;
        param.opinion = opinion;
        var approveTask = indexServices.approveTask(param);

        approveTask.then(function (data) {
            // 请求成功执行代码
            swal({
                    title: "审批已提交",
                    type: "success",
                    confirmButtonColor: "#DD6B55",
                    closeOnConfirm: false
                },
                function () {
                    $window.location.reload();
                });
        });
    }

}]);


