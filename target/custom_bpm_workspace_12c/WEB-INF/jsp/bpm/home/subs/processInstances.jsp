<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--div class="alert alert-info" style="font-size:0.9em">Search Results for Employees <code>(${fn:length(result)} results)</code> and Query Time <code>(${response_time} sec)</code></div-->
<div style="height:180px;overflow:auto;">
	<div style="padding:0px 0px 5px 0px;text-align:right">
		<strong>Flow ID</strong> : <input type="text" id="SearchFlowId2" name="SearchFlowId" value="${param.flowId}" size="5"></input>&nbsp;
		<strong>Instance ID</strong> : <input type="text" id="SearchInstanceId" name="SearchInstanceId" value="${param.componentInstanceId}" size="5"></input>&nbsp;
		<strong>Process Name</strong> : <input type="text" id="SearchProcessName" name="SearchProcessName" value="${param.componentName}" size="10"></input>&nbsp;
		<strong>State</strong> : <select id="SearchProcessState" style="width:120px">
			<option value="0" ${param.state == '0' ? 'selected' : ''}>running</option>
			<option value="2" ${param.state == '2' ? 'selected' : ''}>completed successfully</option>
			<option value="1" ${param.state == '1' ? 'selected' : ''}>Recovery required</option>
			<option value="3" ${param.state == '3' ? 'selected' : ''}>faulted</option>
			<option value="4" ${param.state == '4' ? 'selected' : ''}>terminated by user</option>
			<option value="5" ${param.state == '5' ? 'selected' : ''}>suspended</option>
			<option value="6" ${param.state == '6' ? 'selected' : ''}>stale</option>
			<option value="7" ${param.state == '7' ? 'selected' : ''}>recovered</option>
		</select>&nbsp;
		<strong>Page</strong> : <select id="SearchProcessPageSize" style="width:50px">
			<option value="5" ${param.pageSize == '5' ? 'selected' : ''}>5</option>
			<option value="10" ${param.pageSize == '10' ? 'selected' : ''}>10</option>
			<option value="20" ${param.pageSize == '20' ? 'selected' : ''}>20</option>
			<option value="50" ${param.pageSize == '50' ? 'selected' : ''}>50</option>
			<option value="100" ${param.pageSize == '100' ? 'selected' : ''}>100</option>
			<option value="500" ${param.pageSize == '500' ? 'selected' : ''}>500</option>
			<option value="1000" ${param.pageSize == '1000' ? 'selected' : ''}>1000</option>
		</select>
		<button id="process-search-btn" class="btn btn-inverse btn-default btn-xs">SEARCH</button>&nbsp;
	</div>
	<table class="table table-bordered table-striped table-condensed" style="font-size:11px">
	<thead>
	<tr>
	    <th>번호</th>
		<th>제목</th>
		<th>생성일</th>
		<th>상태</th>
		<th>기한</th>
	</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${fn:length(requestScope.result) > 0}">
				<c:set var="tc" value="0" />
				<c:forEach var="instance" begin="0" items="${requestScope.result}" varStatus="ts">
		<tr>
			<td class="center">${instance.instanceId}</td>
			<td class="center">${instance.title}</td>
			<td class="center">${instance.createdDate}</td>
			<td class="center">${instance.state}</td>
			<td class="center">${instance.processDueDate}</td>
		</tr>
				<c:set var="tc" value="${ts.index}" />
				</c:forEach>
			</c:when>
			<c:otherwise>
		<tr>
			<td colspan="6">NO DATA...</td>
		</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>