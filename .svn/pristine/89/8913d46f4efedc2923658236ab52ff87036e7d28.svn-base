<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<c:url value='/resources/skin/js/jquery-1.11.0.min.js' />"></script>

<title>처리 완료</title>
</head>
<body>
<script>

alert("처리되었습니다.");
//opener.document.location.reload();

bpmClose();
/**
* BPM 닫기
*/
function bpmClose(){
	//alert();
	// init TO-DO
	window.opener.myTodoSearch();
	// init 프로세스현황
	window.opener.prcStateSearch();
	// init 프로세스 지연현황
	window.opener.prcDelaySearch();
	// init 담당자현황
	window.opener.prcCharge();
	//admin
	//bpmAdmin();
	self.close();
};
//self.close();

</script>
</body>
</html>