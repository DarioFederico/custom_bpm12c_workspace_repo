<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>결재이력</title>
<link rel="stylesheet" href="<c:url value='/resources/skin/css/default.css' />" />
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" marginheight="0" marginwidth="0" bgcolor="#FFFFFF">
<table width="100%" border="0" cellpadding="0" cellspacing="0">

  <tr>
    <td width="14" height="14"><img src="<c:url value='/resources/img/main_box1.gif' />"></td>
    <td height="5" background="<c:url value='/resources/img/main_box1bg.gif' />"></td>
    <td width="14" height="14"><img src="<c:url value='/resources/img/main_box2.gif' />"></td>
  </tr>
  <tr>
    <td width="10" background="<c:url value='/resources/img/main_box4bg.gif' />"></td>
    <td align="left" height="270" valign="top" bgcolor="#FFFFFF" style="padding:0 0 0 0">
    	
    
	    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	    		<tr>
	    			<td colspan="3">
	    			<table width="100%" height="18" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="28" align="left" class="titlefont3" style="padding:7 0 0 0">
							<img src="<c:url value='/resources/img/bullet2.gif'/>" width="9" height="9"> 작업내역
				    	</td>
					     <td align="right" style="padding:0 0 5 0">
					     	<input name="search" type="button" onclick="self.close();"  class="btn_01" value="닫기">
					     </td>
					</tr>
	    			</table>
	    			</td>
	    		</tr>
	            <tr> 
	              <td width="4"><img src="<c:url value='/resources/img/board_bar01.gif'/>"></td>
	              <td background="<c:url value='/resources/img/board_barbg.gif'/>"><table width="100%" height="18" border="0" cellpadding="0" cellspacing="0">
	                  <tr> 
	                	<td width="25%" align="center" class="menufont"><font color="#FFFFFF"><b>수행자</b></font></td>
						<td width="25%" align="center" class="menufont"><font color="#FFFFFF"><b>결과</b></font></td>
						<td width="50%" align="center" class="menufont"><font color="#FFFFFF"><b>완료일시</b></font></td>
	                  </tr>
	                </table></td>
	              <td width="4"><img src="<c:url value='/resources/img/board_bar02.gif'/>" width="4" height="28"></td>
	            </tr>
	          </table>
	          <c:choose>
	          <c:when test="${fn:length(requestScope.result) > 0}">
	          <c:set var="ti" value="0" />
			  <c:forEach var="taskHistory" items="${requestScope.result}" varStatus="ts">
			  
	          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	             <tr>
	               <td height="28"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainfont120">
	                   <tr>
	         	  	      <td align="center" width="25%" style="text-overflow:ellipsis;overflow:hidden;">${taskHistory.updateByDisplayName}</td>  
	         	  	      <td align="center" width="25%" style="text-overflow:ellipsis;overflow:hidden;">${taskHistory.outcome == 'APPROVE' ? '승인' : (taskHistory.outcome == 'REJECT' ? '반려' : (taskHistory.outcome == 'SUBMIT' ? '제출' : (taskHistory.outcome == 'OK' ? '확인' : (taskHistory.outcome == 'YES' ? '검토' : taskHistory.outcome))))}</td>    
	         	  	      <td align="center"width="50%" style="text-overflow:ellipsis;overflow:hidden;">${taskHistory.updateDate}</td> 
	                   </tr>
	                 </table></td>
	             </tr>
	             <tr> 
	               <td height="1" bgcolor="#E5E5E5"></td>
	             </tr>
	           </table>
	           <c:set var="ti" value="${ti + 1}" />
	           </c:forEach>
	           </c:when>
	           <c:otherwise>
	           <table width="100%" border="0" cellspacing="0" cellpadding="0">
	             <tr>
	               <td height="28"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainfont120">
	                   <tr>
	         	  	      <td align="center" width="5%" style="text-overflow:ellipsis;overflow:hidden;">작업 내역이 존재하지 않습니다.</td>  
	                   </tr>
	                 </table></td>
	             </tr>
	             <tr> 
	               <td height="1" bgcolor="#E5E5E5"></td>
	             </tr>
	           </table>
	           </c:otherwise>
	    	   </c:choose>
    	   
     </td> 
	<td width="10" background="<c:url value='/resources/img/main_box2bg.gif'/>"></td>
  </tr>
  <tr>
    <td width="14" height="14"><img src="<c:url value='/resources/img/main_box4.gif'/>"></td>
    <td height="5" background="<c:url value='/resources/img/main_box3bg.gif'/>"></td>
    <td width="14" height="14"><img src="<c:url value='/resources/img/main_box3.gif'/>"></td>
  </tr>
</table>
</body>
</html>
