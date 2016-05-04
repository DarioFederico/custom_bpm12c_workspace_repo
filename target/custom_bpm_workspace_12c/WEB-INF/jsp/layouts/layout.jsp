<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" href="<c:url value='/resources/skin/css/common.css' />" />
<!-- 
<link rel="stylesheet" href="<c:url value='/resources/skin/css/ui.jqgrid.css' />" />
 -->

<script type="text/javascript" src="<c:url value='/resources/skin/js/jquery-1.11.0.min.js' />"></script>
<!-- <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js' />"></script> -->
<script type="text/javascript" src="<c:url value='/resources/skin/js/ui.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/skin/js/mobilerange.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/skin/js/bxslider.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/skin/js/jquery.easing.min.1.3.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/skin/js/jquery.jcontent.0.8.min.js' />"></script>
<!-- jqgrid 메인화면 ajax grid -->
<script type="text/javascript" src="<c:url value='/resources/skin/js/i18n/grid.locale-kr.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/skin/js/jquery.jqGrid.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/default.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/request.js'/>"></script>

<!--[if lt IE 9]>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/skin/css/ie8.min.css' />" />
	<script type="text/javascript" src="<c:url value='/resources/skin/js/ui8.js' />"></script>
<![endif]-->
</head>
<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />
</body>
</html>
