
//通用格式化指令定义

var app = angular.module('formDirectives', []);


/**
 * 禁止输入空格 检测
 *
 * 用法示例（在module中需引入）
 *
 * <input  space-Validation  type="text" value="" placeholder="xxxxx" ng-maxlength="16"/>
 * <span class="ng-hide" ng-show="yourData.$dirty && yourData.$error.spaceWord">不能有空格</span>
 */
app.directive('spaceValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (viewValue.indexOf(" ")==-1) {
                    ctrl.$setValidity('spaceWord', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('spaceWord', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 检测  输入是否为非负数
 *
 * 用法示例（在module中需引入、参考上面指令）
 *
 * @type {RegExp}
 */
var FFS_CODE = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
app.directive('feifsValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (FFS_CODE.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('feifs', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('feifs', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 大于零的 最多两位小数
 * @type {RegExp}
 */


var BIGZERO_CODE = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
app.directive('bigzeroValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (BIGZERO_CODE.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('bigzero', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('bigzero', false);
                    return undefined;
                }
            });
        }
    };
});

/**
 * 大于零的 最多三位小数
 * @type {RegExp}
 */


var BIGZERO_CODE = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,3})?$/;
app.directive('lessthreeValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (BIGZERO_CODE.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('lessthree', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('lessthree', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 正整数integer
 * @type {RegExp}
 */

var INTERGER_CODE = /^[1-9]*[1-9][0-9]*$/;
app.directive('integerValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (INTERGER_CODE.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('integer', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('integer', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 非负整数integer
 * @type {RegExp}
 */

var FFSInt_CODE = /^[+]{0,1}(\d+)$/;
app.directive('feifInter', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {

                if (FFSInt_CODE.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('feifsint', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('feifsint', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 邮政编码
 * @type {RegExp}
 */
var POST_CODE = /^[0-9][0-9]{5}$/;
app.directive('postcodeValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (POST_CODE.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('postcode', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('postcode', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 英文名称
 * @type {RegExp}
 */
var ENG_NAME = /^[A-Za-z0-9.,&()\s]{1,40}$/;
app.directive('engnameValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (ENG_NAME.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('engname', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('engname', false);
                    return undefined;
                }
            });
        }
    };
});

/**
 * 固定电话
 * @type {RegExp}
 */
var tel = /^\d{3,4}-?\d{5,8}$/;
app.directive('telValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (tel.test(viewValue )|| viewValue=='') {
                    ctrl.$setValidity('telnum', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('telnum', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 手机号
 * @type {RegExp}
 */
var phone = /^\d{11}$/;
app.directive('phoneValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (phone.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('phonenum', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('phonenum', false);
                    return undefined;
                }
            });
        }
    };
});


/**
 * 银行账号
 * @type {RegExp}
 */
var bank = /^\d{0,30}$/;
app.directive('bankValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (bank.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('banknum', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('banknum', false);
                    return undefined;
                }
            });
        }
    };
});

/**
 * 身份证号
 * @type {RegExp}
 */

var card =/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
app.directive('cardValidation', function() {
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function(viewValue) {
                if (card.test(viewValue) || viewValue=='') {
                    ctrl.$setValidity('cardnum', true);
                    return viewValue;
                } else {
                    ctrl.$setValidity('cardnum', false);
                    return undefined;
                }
            });
        }
    };
});




