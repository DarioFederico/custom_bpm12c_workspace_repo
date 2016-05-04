<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<table class="board_01" summary="BBS Article Control">
	<caption>나의 업무 목록</caption>
	
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
			<th scope="col">번호</th>
			<th scope="col">제목</th>
			<th scope="col">공급사</th>
			<th scope="col">상태</th>
			<th scope="col">시작일자</th>
			<th scope="col">종료일자</th>
			<th scope="col">단계</th>
			<th scope="col">프로세스</th>
			 -->
			<th scope="col">Number</th>
			<th scope="col">제목</th>
			<th scope="col">상태</th>
			<th scope="col">도착일시</th>
			<!-- 
			<th scope="col">지연일수</th>
			 -->
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${fn:length(requestScope.result) > 0}">
		<c:set var="tc" value="0" />
		<c:forEach var="task" begin="0" items="${requestScope.result}" varStatus="ts">
		<tr> 
			<!-- 
			<td align="center" width="80">${task.number}</td>
			<td align="center"><a href="javascript:openTaskUI('${task.taskDisplayUrl}', '${task.id}')">${task.title}</a></td>
			<td align="center">${task.textAttribute1}</td>
			<td align="center" width="120">${task.state == 'ASSIGNED' ? '진행중' : (task.state == 'COMPLETED' ? '완료' : task.state)}</td>
			<td align="center" width="120">${task.createDate}</td>
			<td align="center" width="120">${task.endDate}</td>
			<td align="center" width="120">${task.activityName}</td>
			<td align="center" width="150"><a href="javascript:openTracker('', 'BGF/PoCProcess!1.0*/EvaluationProcess', '${task.instanceId}')">${task.processName == 'EvaluationProcess' ? '공급사 적격 심사' : task.processName}</a></td>
			 -->
			<td align="center" width="40">${task.number}</td>
			<td align="center" width="220">
				<c:choose>
					<c:when test="${task.activityName == 'SAP 전표처리'}">
					<a href="javascript:openSapUI('${task.activityName}', '${task.textAttribute2}', '${task.textAttribute15}')"><img src="<c:url value='/resources/skin/images/icon/cmd12x10.jpg' />" alt="sapshcut" /></a>
					&nbsp;&nbsp;
					</c:when>
				</c:choose>
				<a href="javascript:openTaskUI('${task.textAttribute1}', '${task.number}')">${task.title}</a></td>
			<td align="center" width="60">${task.state == 'ASSIGNED' ? '진행중' : (task.state == 'COMPLETED' ? '완료' : task.state)}</td>
			<!-- 
			<td align="center" width="100"><fmt:formatDate value="${task.dateAttribute1.time}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm" /></td>
			 -->
			<td align="center" width="100"><fmt:formatDate value="${task.assignedDate.time}" type="date" dateStyle="long" pattern="yyyy-MM-dd HH:mm" /></td>
			
			<!-- 
			<td align="center" width="40">${task.expiryDate == 'null' ? '0' : task.expiryDate}</td>
			 -->
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