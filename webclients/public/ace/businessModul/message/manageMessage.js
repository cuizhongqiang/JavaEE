/*标记已读*/


var manageMessageapp = angular.module('manageMessageapp', []);
manageMessageapp.service('manageMessageServices', function ($http) {

    var service = {
        getNoticeFrom: function () {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/notices'
            });
        }
    };
    return service;
});
manageMessageapp.controller('manageMessageCtrl',['$scope','$http', 'manageMessageServices',function ($scope,$http,manageMessageServices) {
    /*暂时先用本地数据*/
/*    $scope.messArray=[{"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"}];*/
    var messData=[{"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},
        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知","mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",'personFrom':"田维杰","messDate":"2017-3-26","messTime":"20:18:18"},

        {"title":"【中建材信息数字化工作平台】V2.1.1.012版本更新通知",
         "mess":"中建材信息数字化工作平台】V2.1.1.012版本已更新完成，本次紧急更新内容涉及【财务功能】、【人力模块】两个功能，具体如下",                       'personFrom':"田维杰",
         "messDate":"2017-3-26",
         "messTime":"20:18:18"}];

    $('#messageList-table').dataTable({
        data:messData,
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
        ordering:false , //取消默认排序查询,否则复选框一列会出现小箭头
        searching:false,
        renderer: "bootstrap", //渲染样式：Bootstrap和jquery-ui
        pagingType: "simple_numbers", //分页样式：simple,simple_numbers,full,full_numbers
        bLengthChange: true,
       /* ajax: function (data, callback) {
            $.ajax({
                type: "GET",
                url: rootReqUrl + "/notices",
                cache: false, //禁用缓存
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var returnData = {};

                    returnData.data = result._embedded.notices;

                    callback(returnData);
                }
            });
        },*/

        //列表表头字段
        columns: [
            {"render": function (data, type, full, meta) {
                return '<input type="checkbox"  class="checkchild" name="dataCheckBox" />'+'<i class="fa fa-envelope"></i>';
            }},
            {
                "data":"title",
                "render": function (data, type, full, meta) {
                    return "<h5>"+data+"</h5>"+full.mess;
                }
            },
            {
                "data":"personFrom",
                "render": function (data, type, full, meta) {
                    var htm='';
                    return htm+='<span class="label label-success">'+"发送人"+'</span>&nbsp;&nbsp;'+data+'<br/>'+full.messDate+"&nbsp;&nbsp;"+full.messTime;
                }
            }
        ],
        /*消息内容列未读时字体显示加粗*/
        "createdRow": function ( row, data, index ) {
                $('td', row).eq(1).css("font-weight","bold");

        }
    });
    /*全选与反选*/
    $(".titleCheck").on("click",function(){
        if($(this).prop("checked")){
            $(".checkchild").each(function () {
                $(this).prop("checked", true);
            })
        }else{
            $(".checkchild").each(function () {
                $(this).prop("checked", false);
            });
        }
    });

    /*全部标记已读，消息图标打开，字体不加粗*/
    $(".allReadMark").on("click",function(){
        $(".checkchild").next().removeClass("fa-envelope").addClass("fa-envelope-open-o");
        $('tr td').css("font-weight","normal"); $('tr td h5').css("font-weight","normal");
    });
    /*选中项标记已读*/
    $(".readMark").on("click",function(){
        $("input[name='dataCheckBox']").each(function(){
            if(this.checked == true){
                $(this).next().removeClass("fa-envelope").addClass("fa-envelope-open-o");
                $(this).parent().next().css("font-weight","normal");$('tr td h5').css("font-weight","normal");
            }
        });
    });

    /*删除*/
    $(".deleteMess").on("click",function(){
        $("input[name='dataCheckBox']").each(function(){
            if(this.checked == true){
                $(this).parents("tr").remove();
            }
        });
    });

}]);


