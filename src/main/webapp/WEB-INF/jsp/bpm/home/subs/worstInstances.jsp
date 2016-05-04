<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<table class="board_01" summary="BBS Article Control">
	<caption>프로세스현황</caption>
	
	<thead>
		<tr>
			<th scope="col">Number</th>
			<th scope="col">제목</th>
			<th scope="col">기한일</th>
			<th scope="col">지연시간</th>
		
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${fn:length(requestScope.result) > 0}">
		<c:set var="tc" value="0" />
		<c:forEach var="instance" begin="0" items="${requestScope.result}" varStatus="ts">
		<tr>
			<td class="center">
		 	<%-- <c:choose>
				<c:when test="${instance.state eq 'OPEN' or instance.state eq 'CLOSE'}">
					<a href="javascript:openAlterFlow('${instance.instanceId}')">${instance.instanceId}</a>
				</c:when>
				<c:otherwise>
					${instance.instanceId}
				</c:otherwise>
			</c:choose> --%>
			<a href="javascript:searchTaskApproval('${instance.instanceId}');">${instance.instanceId}</a>
			</td>
			<td class="center"><a href="javascript:openFlowTrace('${instance.instanceId}', '${instance.componentName}', '${instance.compositeVersion}')">${instance.title}</a></td>
			<!-- 
			<td class="center">${instance.state eq 'OPEN' ? '진행중' : (instance.state eq 'CLOSE' ? '완료' : instance.state)}</td>
			 -->
			<td class="center"><fmt:formatDate value="${instance.processDueDate.time}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm" /></td>
			<td class="center"><font color="red">${instance.countDueDate == '' ? '1분미만' : instance.countDueDate}</font></td>
		</tr>
		<c:set var="tc" value="${ts.index}" />
		</c:forEach>
		<c:forEach var="lc" begin="${tc + 1}" end="4" step="1">
		<tr class="table_padding"> 
			<td align="center" colspan="4">&nbsp;</td>
		</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
		<tr class="table_padding"> 
			<td align="center" colspan="4">&nbsp; DATA 가 없습니다. &nbsp;</td>
		</tr>
		<c:forEach var="lc" begin="1" end="4" step="1">
		<tr class="table_padding"> 
			<td align="center" colspan="4">&nbsp;</td>
		</tr>
		</c:forEach>
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
	//var version     = '1.0';
	//var instanceid  = 70234;
	
	openFlowTrace = function(instanceid,processid, version) {
		window.open('<spring:url value="/tracker/custom/main" />?mode='+mode+'&partition='+partition+'&compositeid='+compositeid+'&processid='+processid+'&version='+version+'&instanceid='+instanceid+'&only_process_view=false&refresh_time=10&scale=1','프로세스 트래커', 'width=1200,height=768');
	}
	
	openAlterFlow = function(instanceId)  {
		window.open('<spring:url value="/bpm/process/grab/info" />?instanceId='+instanceId,'프로세스변경','width=992,height=610')
	}

	
	
</script>