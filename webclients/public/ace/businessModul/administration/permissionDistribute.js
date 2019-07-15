
var permissionDistributeapp = angular.module('permissionDistributeapp', []);



permissionDistributeapp.service('permissionDistributeappServices', function ($http) {
    var service = {
        getProcessDefinitionNodes: function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/workflow/advance/process/getProcessDefinitionNodes',
                params: param
            });
        },
        getWorkflowRoles:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/authuser/getWorkflowRoles?username=admin',
                params: param
            });
        },
        updateWorkflowRoles:function (param) {
            return $http({
                method: 'POST',
                url: rootReqUrl + '/authuser/updateWorkflowRoles',
                params: param
            });
        },
        authuser:function (param) {
            return $http({
                method: 'GET',
                url: rootReqUrl + '/authuser/',
                params: param
            });
        }
    };
    return service;
});

permissionDistributeapp.controller('permissionDistributeCtrl', ['$scope', '$http', '$window','permissionDistributeappServices','$state', function ($scope, $http, $window,permissionDistributeappServices,$state) {

    $scope.adminPermission = "请假流程：Approve or reject request、Holiday approved ；Vacation request：Handle vacation request、Adjust vacation request ";

    var getProcessDefinitionNodes = permissionDistributeappServices.getProcessDefinitionNodes("");
    var getWorkflowRoles = permissionDistributeappServices.getWorkflowRoles();
    var authuser = permissionDistributeappServices.authuser();

    authuser.then(function (data) {
        $scope.userName = data.data.username;
    });


    getWorkflowRoles.then(function (data) {
        var adminData = data.data.mydata;
        getProcessDefinitionNodes.then(function (data) {
            // 请求成功执行代码

            var allData = data.data.content;

            console.log(allData);


                function getTvStateData() {

                var jsonArray = [];

                for (var item in allData) {
                    var nodesArray = [];
                    var subArray = allData[item];
                    for (var subItem in subArray) {
                        var subItemJson = {};
                        subItemJson.text = subArray[subItem];
                        nodesArray.push(subItemJson);
                    }

                    var itemJson = {};
                    itemJson.text = item;
                    itemJson.nodes = nodesArray;
                    jsonArray.push(itemJson);

                }

                    for (adminDataitems in adminData) {
                        var checkedText = adminData[adminDataitems].text;
                        for (jsonArrayItems in jsonArray) {

                            for(nodesItems in jsonArray[jsonArrayItems].nodes){

                                if(checkedText === jsonArray[jsonArrayItems].nodes[nodesItems].text){

                                    jsonArray[jsonArrayItems].nodes[nodesItems].state = {
                                        checked : true
                                    }

                                }

                            }
                        }

                        }

                $scope.jsonArray = jsonArray;

                return jsonArray;
            }

            let $checkableTree = $('#treeview-checkable')
                .treeview({
                    data: getTvStateData(), //数据
                    showIcon: false,
                    showCheckbox: true,
                    levels: 2,
                    onNodeChecked: function(event, node) { //选中节点

                        var checked = $('#treeview-checkable').treeview('getChecked');

                        $scope.checked = checked;
                        // let selectNodes = getChildNodeIdArr(node); //获取所有子节点
                        // if (selectNodes) { //子节点不为空，则选中所有子节点
                        //     $('#treeview-checkable').treeview('checkNode', [selectNodes, { silent: true }]);
                        // }
                        // let parentNode = $("#treeview-checkable").treeview("getNode", node.parentId);
                        // setParentNodeCheck(node);
                    },
                    onNodeUnchecked: function(event, node) { //取消选中节点

                        var checked = $('#treeview-checkable').treeview('getChecked');
                        $scope.checked = checked;

                        // let selectNodes = getChildNodeIdArr(node); //获取所有子节点
                        // if (selectNodes) { //子节点不为空，则取消选中所有子节点
                        //     $('#treeview-checkable').treeview('uncheckNode', [selectNodes, { silent: true }]);
                        // }
                    },
                    onNodeExpanded: function(event, data) {

                    },
                    getSelected:function () {

                    }

                });

            function getChildNodeIdArr(node) {
                let ts = [];
                if (node.nodes) {
                    for (x in node.nodes) {
                        ts.push(node.nodes[x].nodeId);
                        if (node.nodes[x].nodes) {
                            let getNodeDieDai = getChildNodeIdArr(node.nodes[x]);
                            for (j in getNodeDieDai) {
                                ts.push(getNodeDieDai[j]);
                            }
                        }
                    }
                } else {
                    ts.push(node.nodeId);
                }
                return ts;
            }

            function setParentNodeCheck(node) {
                let parentNode = $("#treeview-checkable").treeview("getNode", node.parentId);
                if (parentNode.nodes) {
                    let checkedCount = 0;
                    for (x in parentNode.nodes) {
                        if (parentNode.nodes[x].state.checked) {
                            checkedCount ++;
                        } else {
                            break;
                        }
                    }
                    if (checkedCount === parentNode.nodes.length) {
                        $("#treeview-checkable").treeview("checkNode", parentNode.nodeId);
                        setParentNodeCheck(parentNode);
                    }
                }
            }

        });

    });



    $scope.save  = function () {

        var param = {};
        var myJsonData = {};
        myJsonData.mydata = $scope.checked;
        myJsonData.group = "group";
        param.username = $scope.userName;
        param.workflowroles = JSON.stringify(myJsonData);

        console.log($scope.checked);
        var updateWorkflowRoles = permissionDistributeappServices.updateWorkflowRoles(param);

        updateWorkflowRoles.then(function (data) {
            swal({
                    title: "保存成功",
                    type: "success",
                    confirmButtonColor: "#DD6B55",
                    closeOnConfirm: true
                },
                function () {
                    $state.reload()
                });
        });


    }

}]);




