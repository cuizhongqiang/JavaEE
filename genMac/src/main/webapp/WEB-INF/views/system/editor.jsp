<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"
xmlns:b3mn="http://b3mn.org/2007/b3mn"
xmlns:ext="http://b3mn.org/2007/ext"
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
xmlns:atom="http://b3mn.org/2007/atom+xhtml">
<head profile="http://purl.org/NET/erdf/profile">
<title>Activiti BPM suite</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- libraries -->
<script src="${ctx}/static/modeler/libs/test.js" type="text/javascript" />
<script src="${ctx}/static/modeler/libs/prototype-1.5.1.js" type="text/javascript" />
<script src="${ctx}/static/modeler/libs/path_parser.js" type="text/javascript" />
<script src="${ctx}/static/modeler/libs/ext-2.0.2/adapter/ext/ext-base.js" type="text/javascript" />
<script src="${ctx}/static/modeler/libs/ext-2.0.2/ext-all-debug.js" type="text/javascript" />
<script src="${ctx}/static/modeler/libs/ext-2.0.2/color-field.js" type="text/javascript" />
<style media="screen" type="text/css">
@import url("${ctx}/static/modeler/libs/ext-2.0.2/resources/css/ext-all.css");
@import url("${ctx}/static/modeler/libs/ext-2.0.2/resources/css/xtheme-darkgray.css");
</style>
<link rel="Stylesheet" media="screen" href="${ctx}/static/modeler/editor/css/theme_norm.css" type="text/css" />
<link rel="Stylesheet" media="screen" href="${ctx}/static/modeler/editor/css/theme_norm_signavio.css" type="text/css" />
<link rel="Stylesheet" media="screen" href="${ctx}/static/modeler/explorer/src/css/xtheme-smoky.css" type="text/css" />
<link rel="Stylesheet" media="screen" href="${ctx}/static/modeler/explorer/src/css/custom-style.css" type="text/css" />
<!-- oryx editor -->
<!-- language files -->
<script src="${ctx}/static/modeler/editor/i18n/translation_en_us.js" type="text/javascript" />
<script src="${ctx}/static/modeler/editor/i18n/translation_signavio_en_us.js" type="text/javascript" />
<script src="${ctx}/static/modeler/libs/utils.js" type="text/javascript" />
<script src="${ctx}/static/modeler/editor/oryx.debug.js" type="text/javascript" />
<!-- erdf schemas -->
<link rel="schema.dc" href="http://purl.org/dc/elements/1.1/" />
<link rel="schema.dcTerms" href="http://purl.org/dc/terms/" />
<link rel="schema.b3mn" href="http://b3mn.org" />
<link rel="schema.oryx" href="http://oryx-editor.org/" />
<link rel="schema.raziel" href="http://raziel.org/" />
</head>
<body style="overflow:hidden;">
</body>
</html>