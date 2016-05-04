<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<table class="board_01" summary="BBS Article Control">
	<caption>프로세스현황</caption>
	
	<thead>
	<!-- 
		<tr> 
			<td colspan="7"> <table width="100%" cellspacing="0" cellpadding="3">
				<tr> 
				  <td class="table_search_line" colspan="11"></td>
				</tr>
				<tr class="bg_gray_F7F7F7"> 
				  <td width="120" align="right">제목 : </td>
				  <td width="188"> 
					<input name="title" id="task_title" type="text" value="${param.title}">
				  </td>
				  <td width="70" align="right">&nbsp;</td>
				  <td width="80" align="right">상태 : </td>
				  <td><select name="task_state" class="input_combobox">
					  <option value="" ${param.task_state == '' ? 'selected' : ''}>전체</option>
					  <option value="ASSIGNED" ${param.task_state == 'ASSIGNED' ? 'selected' : ''}>진행중</option>
					  <option value="COMPLETED" ${param.task_state == 'COMPLETED' ? 'selected' : ''}>완료</option>
					</select> </td>
				  
				  <td width="80" align="right" nowrap>게시물 수 : <br></td>
				  <td colspan="2">
					<select name="tasmaxRows" class="input_combobox">
					  <option value="5" ${param.maxRows == '5' ? 'selected' : ''}>5</option>
					  <option value="10" ${param.maxRows == '10' ? 'selected' : ''}>10</option>
					  <option value="15" ${param.maxRows == '15' ? 'selected' : ''}>15</option>
					  <option value="20" ${param.maxRows == '20' ? 'selected' : ''}>20</option>
					  <option value="300" ${param.maxRows == '300' ? 'selected' : ''}>300</option>
					</select>
				  </td>
				  
				  <td width="">&nbsp;</td>
				  <td width="62" align="right">&nbsp; </td>
				  <td>&nbsp;</td>
				  <td width="200" align="right"><button id="searchbtn1" onclick="javascript:searchTasks('#task_list')">search</button>
				</tr>
			  </table></td>
		  </tr>
		  -->
		<tr>
			<!-- 
			<th scope="col">도착일시</th>
			<th scope="col">번호</th>
			<th scope="col">제목</th>
			<th scope="col">상태</th>
			<th scope="col">지연일수</th>
			 -->
			<!-- 
			<th scope="col">Flow ID</th>
			<th scope="col">Title</th>
			<th scope="col">Number</th>
			<th scope="col">Initiator</th>
			<th scope="col">Process Name</th>
			<th scope="col">Status</th>
			 -->
			<th scope="col">Number</th>
			<th scope="col">Title</th>
			<th scope="col">생성일</th>
			<th scope="col">지연일</th>
		
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${fn:length(requestScope.result) > 0}">
		<c:set var="tc" value="0" />
		<c:forEach var="componentInstance" begin="0" items="${requestScope.result}" varStatus="ts">
		
		<tr>
			<td class="center">
		 	<c:choose>
				<c:when test="${componentInstance.normalizedStateAsString eq 'running' or componentInstance.normalizedStateAsString eq 'suspend'}">
					<a href="javascript:openAlterFlow('${fn:substringAfter(componentInstance.id, ':')}')">${fn:substringAfter(componentInstance.id, ':')}</a>
				</c:when>
				<c:otherwise>
					${fn:substringAfter(componentInstance.id, ':')}
				</c:otherwise>
			</c:choose>
			</td>
			<!-- 
			<td class="center"><a href="javascript:openFlowTrace('${fn:substringAfter(componentInstance.id, ':')}', '${componentInstance.componentName}')">Instance #${fn:substringAfter(componentInstance.id, ':')} of ${componentInstance.componentName}</a></td>
			 -->
			<td class="center"><a href="javascript:openFlowTrace('${fn:substringAfter(componentInstance.id, ':')}', '${componentInstance.componentName}')">${componentInstance.componentName}</a></td>
			<!-- 
			<td class="center">${componentInstance.creator}</td>
			<td class="center">${componentInstance.componentName}</td>
			 -->
			<td class="center"><fmt:formatDate value="${componentInstance.creationDate}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm" /></td>
			<td class="center">${componentInstance.normalizedStateAsString eq 'running' ? '진행중' : (componentInstance.normalizedStateAsString eq 'completed successfully' ? '완료' : componentInstance.normalizedStateAsString)}</td>
		</tr>
		<c:set var="tc" value="${ts.index}" />
		</c:forEach>
		<c:forEach var="lc" begin="${tc + 1}" end="4" step="1">
		<tr class="table_padding"> 
			<td align="center" colspan="5">&nbsp;</td>
		</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
		<tr class="table_padding"> 
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
		</tr>
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<input type="hidden" id="pageSize" name="pageSize" value=""/>
<input type="hidden" name="partition" value="default">
<input type="hidden" name="composite" value="ExpenseReportApplication">
<input type="hidden" name="service" value="ExpenseReportProcessDirect.service">
<script type="text/javascript">

	//$(function() {
	//});
	
	jsonToHtml = function(depth, jsonObject, html) {
	
		for(var i=0; i<jsonObject.length; i++) {
			var flowEntry = jsonObject[i].flowEntry;
			var childFlowEntries = jsonObject[i].childFlowEntries;
			
			//html = indent(depth) + "-" + flowEntry.instanceId + "<br>";
			html = "<tr class=\"treegrid-"+flowEntry.instanceId+(flowEntry.parentInstanceId == undefined ? '' : ' treegrid-parent-'+flowEntry.parentInstanceId)+"\">\n" 	
			+ "<td style=\"font-color:blue;font-size:7px;\">"+flowEntry.entityName+" ["+flowEntry.instanceId+"]</td>\n"
			+ "<td width=\"80\" style=\"font-size:8px;\">"+flowEntry.type+"</td>\n"
			+ "<td width=\"80\" style=\"font-size:8px;\">"+flowEntry.subType+"</td>\n"
			+ "<td width=\"80\" style=\"font-size:8px;\">"+(flowEntry.state == 'Running' ? '<span class="label label-success">Running</span>' : (flowEntry.state == 'Completed' ? '<span class="label label-info">Completed</span>' : (flowEntry.state == 'Failed' ? '<span class="label label-danger">Failed</span>' : (flowEntry.state == 'Running Recovered' ? '<span class="label label-success">Recovered</span>' : (flowEntry.state == 'Completed Recovered' ? '<span class="label label-success">Recovered</span>' : (flowEntry.state == 'Suspended' ? '<span class="label label-warning">Suspended</span>' : (flowEntry.state == 'Resumed' ? '<span class="label label-success">Resumed</span>' : (flowEntry.state == 'Aborted' ? '<span class="label-default label">Aborted</span>' : (flowEntry.state == 'Stale' ? '<span class="label-default label">Stale</span>' : (flowEntry.state == 'Recovery Required' ? '<span class="label label-warning">Recovery Required</span>' : 'Unknown'))))))))))+"</td>\n"	// usage는 먼지 모름...?
			+ "<td width=\"180\" style=\"font-size:8px;\">"+flowEntry.date+"</td>\n"
			+ "<td width=\"180\" style=\"font-size:8px;\">"+flowEntry.compositeName+"</td>\n"
		    + "</tr>\n";
			
			$("#flowTraceInfo").append(html);
			
			if(childFlowEntries.length > 0) {
				jsonToHtml(depth + 1, childFlowEntries, html);
			}
			
			//alert(childFlowEntries.length);
		}
	}
	
	indent = function(depth) {
		var indent = "";
		
		for(var i = 0; i < depth * 4; i++) {
			indent += "&nbsp;"
		}
		
		return indent;
	}
	
	/*
	http://oraclebpm:7003/bpms/tracker/custom/main?mode=runtime&partition=default&compositeid=ExpenseReportApplication&processid=ExpenseReportProcess&version=1.0&instanceid=70234&only_process_view=false&refresh_time=10&scale=1 
	*/
	var mode        = 'runtime';
	var partition   = 'default';
	var compositeid = 'ExpenseReportApplication';
	var processid   = 'ExpenseReportProcess';
	var version     = '1.0';
	//var instanceid  = 70234;
	
	openFlowTrace = function(instanceid,processid) {
		window.open('<spring:url value="/tracker/custom/main" />?mode='+mode+'&partition='+partition+'&compositeid='+compositeid+'&processid='+processid+'&version='+version+'&instanceid='+instanceid+'&only_process_view=false&refresh_time=10&scale=1','프로세스 트래커', null, null);
	}
	
	openAlterFlow = function(componentInstanceId)  {
		window.open('<spring:url value="/bpm/process/grab/info" />?instanceId='+componentInstanceId,'프로세스변경','width=992,height=610')
	}
	

	
	
</script>