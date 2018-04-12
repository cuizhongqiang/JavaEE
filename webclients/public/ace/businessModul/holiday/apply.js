/**
 *  假期申请
 */
var applyapp = angular.module('applyapp', []);


//假期申请接口方法

applyapp.service('applyServices', function ($http) {
    var service = {
        submitForm: function (param) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/workflow/execute/HolidayRequest/submitForm',
                params: param
            });
        },
    };
    return service;
});


//假期申请控制器
applyapp.controller('applyCtrl', ['$scope', '$http', 'applyServices', '$window','$state', function ($scope, $http, applyServices, $window,$state) {
    //Editor引入
    jQuery(function ($) {
        $('#editor2').css({'height': '200px'}).ace_wysiwyg({
            toolbar_place: function (toolbar) {
                return $(this).closest('.widget-box')
                    .find('.widget-header').prepend(toolbar)
                    .find('.wysiwyg-toolbar').addClass('inline');
            },
            toolbar:
                [
                    'bold',
                    {name: 'italic', title: 'Change Title!', icon: 'ace-icon fa fa-leaf'},
                    'strikethrough',
                    null,
                    'insertunorderedlist',
                    'insertorderedlist',
                    null,
                    'justifyleft',
                    'justifycenter',
                    'justifyright'
                ],
            speech_button: false
        });


        $('[data-toggle="buttons"] .btn').on('click', function (e) {
            var target = $(this).find('input[type=radio]');
            var which = parseInt(target.val());
            var toolbar = $('#editor1').prev().get(0);
            if (which >= 1 && which <= 4) {
                toolbar.className = toolbar.className.replace(/wysiwyg\-style(1|2)/g, '');
                if (which == 1) $(toolbar).addClass('wysiwyg-style1');
                else if (which == 2) $(toolbar).addClass('wysiwyg-style2');
                if (which == 4) {
                    $(toolbar).find('.btn-group > .btn').addClass('btn-white btn-round');
                } else $(toolbar).find('.btn-group > .btn-white').removeClass('btn-white btn-round');
            }
        });


        //RESIZE IMAGE

        //Add Image Resize Functionality to Chrome and Safari
        //webkit browsers don't have image resize functionality when content is editable
        //so let's add something using jQuery UI resizable
        //another option would be opening a dialog for user to enter dimensions.
        if (typeof jQuery.ui !== 'undefined' && ace.vars['webkit']) {

            var lastResizableImg = null;

            function destroyResizable() {
                if (lastResizableImg == null) return;
                lastResizableImg.resizable("destroy");
                lastResizableImg.removeData('resizable');
                lastResizableImg = null;
            }

            var enableImageResize = function () {
                $('.wysiwyg-editor')
                    .on('mousedown', function (e) {
                        var target = $(e.target);
                        if (e.target instanceof HTMLImageElement) {
                            if (!target.data('resizable')) {
                                target.resizable({
                                    aspectRatio: e.target.width / e.target.height,
                                });
                                target.data('resizable', true);

                                if (lastResizableImg != null) {
                                    //disable previous resizable image
                                    lastResizableImg.resizable("destroy");
                                    lastResizableImg.removeData('resizable');
                                }
                                lastResizableImg = target;
                            }
                        }
                    })
                    .on('click', function (e) {
                        if (lastResizableImg != null && !(e.target instanceof HTMLImageElement)) {
                            destroyResizable();
                        }
                    })
                    .on('keydown', function () {
                        destroyResizable();
                    });
            }

            enableImageResize();

        }


    });


    $scope.submission = function () {


        $scope.description = $('#editor2').wysiwyg()[0].innerText;

        if ($scope.nrOfHolidays && $scope.description) {

            var param = {};
            param.nrOfHolidays = $scope.nrOfHolidays;
            param.description = $scope.description;
            var submitForm = applyServices.submitForm(param);

            submitForm.then(function (data) {
                // 请求成功执行代码
                swal({
                        title: "申请已提交",
                        type: "success",
                        confirmButtonColor: "#DD6B55",
                        closeOnConfirm: true
                    },
                    function () {
                         $state.reload()
                    });
            });
        } else {
            sweetAlert("请完善信息", "", "error");
        }
    };


}]);
