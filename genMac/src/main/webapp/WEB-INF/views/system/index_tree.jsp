<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>中建材通用管理系统</title>
<link rel="icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/images/favicon.ico" type="image/x-icon" />

<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx }/static/plugins/artTemplate/dist/template.js"></script>

<!--导入首页启动时需要的相应资源文件(首页相应功能的 js 库、css样式以及渲染首页界面的 js 文件)-->
<script src="${ctx}/static/plugins/easyui/common/index.js" type="text/javascript"></script>
<script src="${ctx}/static/plugins/easyui/common/index-startup.js" type="text/javascript"></script>
<link href="${ctx}/static/plugins/easyui/common/index.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
.tree-node {
    height: 32px;
    white-space: nowrap;
    cursor: pointer;
}
.tree-node span {
    height: 32px;
    line-height:32px;
}
.tree-node span a {
    height: 32px;
}
.tree-node span.tree-hit.tree-expanded {
    height: 16px;
}
.tree-node span.tree-hit.tree-collapsed {
    height: 16px;
}
.toprightcolor {
	color: #fff;
}
.toprightcolor:hover{ color:#000; font-weight:bold} 
</style>
</head>
<body>
	<!-- 容器遮罩 -->
    <div id="maskContainer">
        <div class="datagrid-mask" style="display: block;"></div>
        <div class="datagrid-mask-msg" style="display: block; left: 50%; margin-left: -52.5px;">
            正在加载...
        </div>
    </div>
    <div id="mainLayout" class="easyui-layout hidden" data-options="fit: true">
        <div id="northPanel" data-options="region: 'north', border: false" style="height: 80px; overflow: hidden;">
            <div id="topbar">
                <div class="top-bar-left">
                	<%-- <div style="padding:0px 0px 0px 5px"><img src="${ctx}/static/images/logo.png" alt="LOGO" /></div> --%>
                	<img src="${ctx}/static/images/logo_sign.png" alt="LOGO" />
                	<div style="margin-top: 12px; font-size: 21px; float: right;">中建材通用机械有限公司管理系统</div>
                </div>
                <div class="top-bar-right">
                    <div id="timerSpan">
                    </div>
                    <div id="themeSpan">
                    	<a id="aboutus" href="#" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-hamburg-customers'">关于我们</a>
                    	<a id="help" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-standard-help'">帮助</a>
                        <span>更换皮肤风格：</span>
                        <select id="themeSelector"></select>
                        <a id="btnHideNorth" class="easyui-linkbutton" data-options="plain: true, iconCls: 'layout-button-up'"></a>
                    </div>
                </div>
            </div>
            <div id="toolbar" class="panel-header panel-header-noborder top-toolbar">
                <div id="infobar">
                    <span class="icon-hamburg-user" style="padding-left: 25px; background-position: left center;">
                       <shiro:principal property="name"/>，您好
                    </span>
                </div>
                <div id="buttonbar">
                    <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_set'" iconCls="icon-standard-cog">系统操作</a>  
                    <div id="layout_north_set">
						<div id="chgPwd" onclick="add();" data-options="iconCls:'icon-standard-user-edit'">修改密码</div>
						<div id="btnFullScreen" data-options="iconCls:'icon-standard-arrow-out'">全屏切换</div>
						<div id="btnExit" data-options="iconCls:'icon-munich-sign-out'">退出系统</div>
					</div>
					<a id="btnShowNorth" class="easyui-linkbutton" data-options="plain: true, iconCls: 'layout-button-down'" style="display: none;"></a>
                </div>
            </div>
        </div>

        <div data-options="region: 'west', title: '菜单导航栏', iconCls: 'icon-standard-map', split: true, minWidth: 200, maxWidth: 400" style="width: 220px; padding: 1px;">
            <div id="myMenu" class="easyui-accordion" data-options="fit:true,border:false">
            <script id="menu" type="text/html">
			{{each data as p_permission}}
				{{if (p_permission.pid==null)}}
   				<div title="{{p_permission.name }}" style="padding: 5px;" data-options="border:false,iconCls:'{{p_permission.icon }}'">
					<ul class="easyui-tree" data-options="onClick: openLeftNodeUrl">
						{{each data as s_permission}}
						{{if (s_permission.pid==p_permission.id)}}
						<li data-options="plain:true,iconCls:'{{s_permission.icon }}'">
							<span style="font-weight:bold">
								{{if (s_permission.url!=null && s_permission.url.length>0)}}
								<a id="btn" p_name="{{s_permission.name}}" p_url="{{s_permission.url }}" p_icon="{{s_permission.icon }}">{{s_permission.name}}</a>
								{{else}}
								{{s_permission.name}}
								{{/if}}
							</span>
							<ul>
								{{each data as c_permission}}
								{{if (c_permission.pid==s_permission.id)}}
								<li data-options="plain:true,iconCls:'{{c_permission.icon }}'">
                            		<span>
										{{if (c_permission.url!=null && c_permission.url.length>0)}}
										<a id="btn" p_name="{{c_permission.name}}" p_url="{{c_permission.url }}" p_icon="{{c_permission.icon }}">{{c_permission.name}}</a>
										{{else}}
										{{c_permission.name}}
										{{/if}}
									</span>
								</li>
								{{/if}}
								{{/each}}
							</ul>
						</li>
						{{/if}}	
						{{/each}}
					</ul>
				</div>
				{{/if}}	
			{{/each}}
			</script>
			</div>
        </div>

        <div data-options="region: 'center'">
            <div id="mainTabs_tools" class="tabs-tool">
                <table>
                    <tr>
                        <td><a id="mainTabs_jumpHome" class="easyui-linkbutton easyui-tooltip" title="跳转至主页选项卡" data-options="plain: true, iconCls: 'icon-hamburg-home'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
						<td><a id="mainTabs_toggleAll" class="easyui-linkbutton easyui-tooltip" title="展开/折叠面板使选项卡最大化" data-options="plain: true, iconCls: 'icon-standard-arrow-out'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_refTab" class="easyui-linkbutton easyui-tooltip" title="刷新当前选中的选项卡" data-options="plain: true, iconCls: 'icon-standard-arrow-refresh'"></a></td>
                        <td><div class="datagrid-btn-separator"></div></td>
                        <td><a id="mainTabs_closeTab" class="easyui-linkbutton easyui-tooltip" title="关闭当前选中的选项卡" data-options="plain: true, iconCls: 'icon-standard-application-form-delete'"></a></td>
                    </tr>
                </table>
            </div>
            <div id="mainTabs" class="easyui-tabs" data-options="fit: true, border: false, showOption: true, enableNewTabMenu: true, tools: '#mainTabs_tools', enableJumpTabMenu: true">
            </div>
        </div>

        <!-- <div data-options="region: 'east', title: '日历', iconCls: 'icon-standard-date', split: true,collapsed: false, minWidth: 160, maxWidth: 500" style="width: 220px;">
            <div id="eastLayout" class="easyui-layout" data-options="fit: true">
                <div data-options="region: 'north', split: false, border: false" style="height: 220px;">
                    <div class="easyui-calendar" data-options="fit: true, border: false"></div>
                </div>
                <div id="linkPanel" data-options="region: 'center', border: false, title: '通知', iconCls: 'icon-hamburg-link', tools: [{ iconCls: 'icon-hamburg-refresh', handler: function () { window.link.reload(); } }]">
                    
                </div>
            </div>
        </div> -->

        <div data-options="region: 'south', title: '关于...', iconCls: 'icon-standard-information', collapsed: true, border: false" style="height: 70px;">
            <div style="color: #4e5766; padding: 6px 0px 0px 0px; margin: 0px auto; text-align: center; font-size:12px;">
                
            </div>
        </div>

    </div>
	<div id="dlg"></div>
<script>
$.ajax({
	async:false,
	type:'get',
	url:"${ctx}/system/permission/i/json",
	success: function(data){
		var menuData={data:data};
		var html = template('menu', menuData);
		$('#myMenu').html(html); 
	},
	headers: {
		"Accept": "application/json" //配置返回数据类型application/xml
	}
});

	/**流程工作台加载页面**/
	$(function() {
		$('#mainTabs').tabs('add', {
			title: '我的工作台',
			iniframe: true,
			refreshable: false,
			selected: true,
			href : '${ctx}/workflow'
		});
	});

	$('.easyui-linkbutton').on('click', function() {
		$('.easyui-linkbutton').linkbutton({
			selected : false
		});
		$(this).linkbutton({
			selected : true
		});
	});

	//弹窗增加
	function add() {
		d = $("#dlg").dialog({
			title : '修改密码',
			width : 380,
			height : 180,
			href : '${ctx}/system/user/updatePwd',
			maximizable : true,
			modal : true,
			buttons : [ {
				text : '保存',
				handler : function() {
					$("#mainform").submit();
				}
			}, {
				text : '关闭',
				handler : function() {
					d.panel('close');
				}
			} ]
		});
	}
	
	/**
	 *  左侧菜单点击事件
	 **/
	function openLeftNodeUrl(node) {
		if($(node.text).attr("p_url")){
			window.mainpage.mainTabs.addModule($(node.text).attr("p_name"),$(node.text).attr("p_url"),$(node.text).attr("p_icon"))
		}
	}
</script>
    
</body>
</html>
