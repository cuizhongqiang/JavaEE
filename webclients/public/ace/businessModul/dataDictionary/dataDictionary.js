/**
 * dataDictionary 数据字典管理
 */
//数据字典

var dataDictionaryapp = angular.module('dataDictionary', []);

dataDictionaryapp.controller('dataDictionaryCtrl', ['$scope', '$http', 'indexServices', '$window', 'SpringDataRestAdapter', function ($scope, $http, indexServices, $window, SpringDataRestAdapter) {



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
        bLengthChange: true,
        ajax: function (data, callback) {
            $.ajax({
                type: "GET",
                url: rootReqUrl + "/dataDictionaries",
                cache: false, //禁用缓存
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};

                    returnData.data = result._embedded.dataDictionaries;

                    callback(returnData);
                }
            });
        },
        "columnDefs": [
            {
                "targets": 4,
                "render": function (data, type, full, meta) {
                    return "<a id=\"deleteButton\" class=\"tooltip-info\" data-rel=\"tooltip\" title=\"View\"><span class=\"red\"><i class=\"ace-icon fa fa-trash-o bigger-120\"></i></span></a>"
                }
            }
        ],

//            initComplete:function(){
//                $("#dynamic-table_length").append("<a href='https://github.com/ssy341/datatables-cn/issues/new' " +
//                    "class='btn btn-primary btn-sm'>批量删除</a>");
//            },
        //列表表头字段
        columns: [
            {
                "sClass": "text-center",
                "data": "ID",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox"  class="checkchild" name="dataCheckBox" value="' + data + '" />';
                },
                "bSortable": false
            },
            {"data": "catalog"},
            {"data": "name"},
            {"data": "content"}
        ]
    });

    var listCatalogs = indexServices.listCatalogs();

    listCatalogs.then(function (data) {
        // 请求成功执行代码
        $scope.listCatalogsData = data.data;
    });

    var dataTable =  $('#dynamic-table').dataTable();

    $scope.putDictionariesData = function (catalog) {
        if(dataTable){
            dataTable.fnDestroy();
        }
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
            bLengthChange: true,
            ajax: function (data, callback) {
                $.ajax({
                    type: "GET",
//                        url: rootReqUrl + "/dataDictionaries",
                    url: rootReqUrl + "/dataDictionaries/search/findByCatalog?catalog=" + catalog,
                    cache: false, //禁用缓存
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        var returnData = {};

                        returnData.data = result;

                        callback(returnData);
                    }
                });
            },
            "columnDefs": [
                {
                    "targets": 4,
                    "render": function (data, type, full, meta) {
                        return "<a id=\"deleteButton\" class=\"tooltip-info\" data-rel=\"tooltip\" title=\"View\"><span class=\"red\"><i class=\"ace-icon fa fa-trash-o bigger-120\"></i></span></a>"
                    }
                }
            ],

//            initComplete:function(){
//                $("#dynamic-table_length").append("<a href='https://github.com/ssy341/datatables-cn/issues/new' " +
//                    "class='btn btn-primary btn-sm'>批量删除</a>");
//            },
            //列表表头字段
            columns: [
                {
                    "sClass": "text-center",
                    "data": "ID",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild" name="dataCheckBox" value="' + data + '" />';
                    },
                    "bSortable": false
                },
                {"data": "catalog"},
                {"data": "name"},
                {"data": "content"}
            ]
        });
    };


    $("#myCheckbox").bind("click", function () {
        if ($('#myCheckbox').is(':checked') == true) {
            $("INPUT[name='dataCheckBox']").each(function () {

                if (false == $(this).is(':checked')) {
                    $(this).prop("checked", true);
                }

            });
        }
        if ($('#myCheckbox').is(':checked') == false) {
            $("INPUT[name='dataCheckBox']").each(function () {

                if (true == $(this).is(':checked')) {
                    $(this).prop("checked", false);
                }
            });
        }
    });

    $("#dynamic-table tbody").on("click", "#deleteButton", function () {
        //获取行
        var row = $("table#dynamic-table tr").index($(this).closest("tr"));
        //获取某列（从0列开始计数）的值
        var name = $("table#dynamic-table").find("tr").eq(row).find("td").eq(0).text();
        var job = $("table#dynamic-table").find("tr").eq(row).find("td").eq(1).text();
        alert(name + "是" + job);
    });

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
