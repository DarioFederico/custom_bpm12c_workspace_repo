<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

	<!-- (container) -->
	<div id="container">

		<!-- (left_section) -->
		<div class="left_section">
		
		<!-- (bpmadmin 어드민페이지) -->
		<div id="adminPage"  style="display:none;width:1068px;">
			<div>
				<div style="float:left;">
					<b>프로세스BACK</b>
				</div>
				<div style="float:right;height:24px;"><!-- 
					<input type="radio" name="adminCon" value="OPEN" checked> 진행중
					<input type="radio" name="adminCon" value="COMPLETED"> 완료 -->
					진행상태 : 
					<select id="adminState" name="adminState">
						<option value="">전체</option>
						<!-- <option value="STALE">STALE</option> -->
						<option value="OPEN">진행중</option>
						<option value="COMPLETED">완료</option>
					</select>
					&nbsp;지연상태 :
					<select id="adminOverdue" name="adminOverdue">
						<option value="">전체</option>
						<option value="YES">지연</option>
						<option value="NO">정상</option>
					</select>
					&nbsp;<input type="text" id="adminTitle" name="adminTitle"  placeholder="제목" />
					<a href="javascript:bpmAdmin()"><img src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' />" alt="search" /></a>
				</div>
			</div>
			<div style="clear:both;"></div>
			
			
			<div class="bpmadmin" id="bpmadmin">

			</div>
			
		</div>
		<!-- ( 어드민페이지) -->
		
		
		
			<!-- (left_1_section) -->
			<div class="left_1_section ">
				
				<!-- (article_num1) 개인현황 -->
				<div class="article_num1">
					<div class="w524">
						<!-- <span class="map_01"><img src="<c:url value='/resources/skin/images/todo/0bam006.jpg' />" alt="normal" /></span> -->
						
						
						
						<div class="boxmodel_4">
							<div>
								<div>
									<div style="float:left;">
										<b>나의 할일</b> <div id="mytasks_refresh_spin"></div>
									</div>
									<div style="float:right;height:24px;">
										<!-- <input type="radio" name="myTodo" value="" checked> 전체
										<input type="radio" name="myTodo" value="OVERDUE"> 지연 -->
										<input type="radio" name="myTodo" value="OPEN" checked> 진행중
										<input type="radio" name="myTodo" value="OVERDUE" > 지연
										<input type="radio" name="myTodo" value="COMPLETED"> 완료
										<input type="text" id="myTodoKeyword" name="myTodoKeyword"  placeholder="제목" />
										<a href="javascript:myTodoSearch()"><img src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' />" alt="search" /></a>
									</div>
								</div>
								<div style="clear:both;"></div>
								<div id="mytasks">
									<table class="board_01" summary="BBS Article Control">
									<thead>
										<tr>
											<th scope="col">Number</th>
											<th scope="col">제목</th>
											<th scope="col">상태</th>
											<th scope="col">도착일시</th>
										</tr>
									</thead>
									<tbody>
										<tr class="table_padding"> 
											<td align="center" colspan="4" height="108">&nbsp;</td>
										</tr>
									</tbody>
									</table>
								</div>
							</div>
							
						</div>
					</div>
				</div>
				<!-- // (article_num1) 개인현황 -->
				
				<!-- (article_num2) 프로세스현황 -->
				<div class="article_num2">
					<div class="w524">
						<div class="boxmodel_4">
							<div>
								<div>
									<div style="float:left;">
										<b>프로세스현황</b>
									</div>
									<div style="float:right;height:24px;">
										<input type="radio" name="prcState" value="OPEN" checked> 진행중
										<input type="radio" name="prcState" value="COMPLETED"> 완료
										<input type="text" id="prcStateKeyword" name="prcStateKeyword"  placeholder="제목" />
										<a href="javascript:prcStateSearch()"><img src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' />" alt="search" /></a>
										<!-- 
										<input type="image" src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' /> alt="search"/>
										 -->
									</div>
								</div>
								<div style="clear:both;"></div>
							
								
								<div id="taskProcess">
									<!-- <table class="board_01" summary="BBS Article Control">
										<colgroup>
											<col style="width: 25%;">
											<col style="width: 5%;">
											<col style="width: 40%;">
											<col style="width: 15%;">
											<col style="width: 15%;">
										</colgroup>
										<thead>
											<tr>
												<th scope="col">도착일시</th>
												<th scope="col">ProcessNo</th>
												<th scope="col">제목</th>
												<th scope="col">상태</th>
												<th scope="col">지연일수</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>2016년03월01일</td>
												<td>127</td>
												<td>BPM 프로젝트1</td>
												<td>지연</td>
												<td>2일</td>
											</tr>
											<tr>
												<td>2016년02월28일</td>
												<td>116</td>
												<td>BPM 프로젝트2</td>
												<td>지연</td>
												<td>2일</td>
											</tr>
											<tr>
												<td>2016년02월28일</td>
												<td>114</td>
												<td>BPM 프로젝트3</td>
												<td>진행중</td>
												<td></td>
											</tr>
											<tr>
												<td>2016년02월29일</td>
												<td>94</td>
												<td>BPM 프로젝트4</td>
												<td>완료</td>
												<td></td>
											</tr>
											<tr>
												<td>2016년02월26일</td>
												<td>88</td>
												<td>BPM 프로젝트5</td>
												<td>완료</td>
												<td></td>
											</tr>
										</tbody>
									</table> -->
								</div>
							
							</div>
							
						</div>
					</div>
				</div>
				<!-- (article_num2) 프로세스현황 -->
				
				<!-- (article_num3) 차트 -->
				<%-- <div class="article_num3">
					<div class="w524">
						<span class="map_01"><img src="<c:url value='/resources/skin/images/todo/0bam006.jpg' />" alt="normal" /></span>		
					</div>
				</div> --%>
				<!-- //(article_num3) 차트 -->
				
				
			</div>
			<!-- //(left_1_section) -->
			
			
			
			<!-- (left_2_section) -->
			<div class="left_2_section">
			
			
				<!-- (article_num4) 프로세스 지연현황 -->
				<div class="article_num4">
					<div class="w524 mgl_20">
						<div class="boxmodel_4">
							<div>
								<div>
									<div style="float:left;">
										<b>프로세스 지연현황</b>
									</div>
									<div style="float:right;height:24px;">
										<input type="radio" name="prcDelay" value="OPEN" checked> 진행중
										<input type="radio" name="prcDelay" value="COMPLETED"> 완료
										<input type="text" id="prcDelayKeyword" name="prcDelayKeyword" placeholder="제목"/>
										<a href="javascript:prcDelaySearch()"><img src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' />" alt="search" /></a>
									</div>
								</div>
								<div style="clear:both;"></div>
							
								
								<div id="worstInstances">
									<!-- <table class="board_01" summary="BBS Article Control">
										<colgroup>
											<col style="width: 25%;">
											<col style="width: 5%;">
											<col style="width: 40%;">
											<col style="width: 15%;">
											<col style="width: 15%;">
										</colgroup>
										<thead>
											<tr>
												<th scope="col">도착일시</th>
												<th scope="col">ProcessNo</th>
												<th scope="col">제목</th>
												<th scope="col">상태</th>
												<th scope="col">지연일수</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>2016년03월01일</td>
												<td>127</td>
												<td>BPM 프로젝트1</td>
												<td>지연</td>
												<td>2일</td>
											</tr>
											<tr>
												<td>2016년02월28일</td>
												<td>116</td>
												<td>BPM 프로젝트2</td>
												<td>지연</td>
												<td>2일</td>
											</tr>
											<tr>
												<td>2016년02월28일</td>
												<td>114</td>
												<td>BPM 프로젝트3</td>
												<td>진행중</td>
												<td></td>
											</tr>
											<tr>
												<td>2016년02월29일</td>
												<td>94</td>
												<td>BPM 프로젝트4</td>
												<td>완료</td>
												<td></td>
											</tr>
											<tr>
												<td>2016년02월26일</td>
												<td>88</td>
												<td>BPM 프로젝트5</td>
												<td>완료</td>
												<td></td>
											</tr>
										</tbody>
									</table> -->
								</div>
							
							</div>
							
						</div>
					</div>
				</div>
				<!-- (article_num4) 프로세스현황 -->
				
				<!-- (article_num5) 담당자 지연현황 -->
				<div class="article_num5">
					<div class="w524 mgl_20">
						<div class="boxmodel_4">
							<div>
								<div>
									<div style="float:left;">
										<b>담당자 지연현황</b>
									</div>
									<div style="float:right;height:24px;">
										<input type="radio" name="aprState" value="ASSIGNED" checked> 진행중
										<input type="radio" name="aprState" value="COMPLETED"> 완료
										<!-- 
										<input type="text" id="prcStateKeyword" name="prcStateKeyword"  placeholder="담당자명" />
										<a href="javascript:null"><img src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' />" alt="search" /></a>
										<input type="image" src="<c:url value='/resources/skin/images/btn/btn_allsearch2.gif' /> alt="search"/>
										 -->
									</div>
								</div>
								<div style="clear:both;"></div>
								
								<div id="taskApproval">
								
									<!-- <table class="board_01" summary="BBS Article Control">
										<colgroup>
											<col style="width: 25%;">
											<col style="width: 25%;">
											<col style="width: 25%;">
											<col style="width: 25%;">
										</colgroup>
										<thead>
											<tr>
												<th scope="col">담당자명</th>
												<th scope="col">소요기간</th>
												<th scope="col">상태</th>
												<th scope="col">종료일시</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>홍길동</td>
												<td>7일</td>
												<td>진행중</td>
												<td>2016년02월29일</td>
											</tr>
											<tr>
												<td>홍길순</td>
												<td>4일</td>
												<td>진행중</td>
												<td>2016년02월29일</td>
											</tr>
											<tr>
												<td>김철수</td>
												<td>5일</td>
												<td>완료</td>
												<td>2016년02월27일</td>
											</tr>
										</tbody>
									</table> -->
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- //(article_num4) 담당자현황 -->
				
				
				<!-- (article_num5) 차트 -->
				<%-- <div class="article_num5">	
					<div class="w524 mgl_20">
						<div>
							<span class="map_02"><img src="<c:url value='/resources/skin/images/todo/0bam007.jpg' />" alt="normal" style="align:center;" /></span>
						</div>
					</div>
				</div> --%>
				<!-- //(article_num5) 차트 -->
				
				
			</div>
			<!-- //(left_2_section) -->
		
		</div>
		<!-- //(left_section) -->
		
		
		

		<!-- (right_section) -->
		<div class="right_section">
		
			<!-- (article_num6) 공지사항 -->
			<div class="article_num6">
				<div class="w220">
					<div class="boxmodel_11">
						<div>
							<p>보도자료</p>
							<div style="height: 264px;"> <!-- ( 20140701_height 추가 ) -->

								<ul>
									<li>
										<p><a href="#">서울반도체, 세계 최고 광효율 LED 조...</a></p>
										<span>2016-03-07</span>
									</li>
									<li>
										<p><a href="#">서울반도체, 독자기술의 대한민국 고...</a></p>
										<span>2016-02-02</span>
									</li>
									<li>
										<p><a href="#">서울반도체,루미테크사 기술 활용하여...</a></p>
										<span>2016-01-26</span>
									</li>
									<li>
										<p><a href="#">서울반도체, 엔플라스 핵심 특허 3건...</a></p>
										<span>2015-12-08</span>
									</li>
									<li class="last">
										<p><a href="#">서울반도체, 3분기 사상 최대 분기매출...</a></p>
										<span>2015-10-27</span>
									</li>
								</ul>
								<p class="center"><a href="#"><img src="<c:url value='/resources/skin/images/btn/btn_addview.gif' />" alt="more" /></a></p>

							</div>
							<a href="#" class="list_on">list</a>
						</div>
					</div>
				</div>
			</div>
			<!-- //(article_num6) 공지사항 -->
			
			
			<!-- (article_num7) 달력 -->
			<div class="article_num7">
				<div class="w220">
				
					<!-- (Calendar UI type01) -->
					<div class="boxmodel_13" style="height: 188px;">
						<div class="num1">
							<p>2016.03</p>

							<!-- <span>
								<a href="#" class="left_arrow">prev</a>
								<a href="#" class="right_arrow">next</a>
							</span> -->
						</div>

						<div class="num2">
							<table summary="callendar">
								<caption>callendar</caption>
								<colgroup>
									<col style="width: 30px;" />
									<col style="width: 30px;" />
									<col style="width: 30px;" />
									<col style="width: 30px;" />
									<col style="width: 30px;" />
									<col style="width: 30px;" />
									<col style="width: 30px;" />
								</colgroup>
								<thead>
									<tr>
										<th class="sun" scope="col">SUN</th>
										<th scope="col">MON</th>
										<th scope="col">TUE</th>
										<th scope="col">WED</th>
										<th scope="col">THU</th>
										<th scope="col">FRI</th>
										<th class="sat" scope="col">SAT</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="bgp">28</td>
										<td class="bgp">29</td>
										<td>1</td>
										<td>2</td>
										<td>3</td>
										<td>4</td>
										<td>5</td>
									<tr>
									<tr>
										<td>6</td>
										<td>7</td>
										<td>8</td>
										<td>9</td>
										<td>10</td>
										<td>11</td>
										<td>12</td>
									<tr>
									<tr>
										<td>13</td>
										<td>14</td>
										<td>15</td>
										<td>16</td>
										<td>17</td>
										<td>18</td>
										<td>19</td>
									<tr>
									<tr>
										<td>20</td>
										<td>21</td>
										<td>22</td>
										<td>23</td>
										<td>24</td>
										<td>25</td>
										<td>26</td>
									<tr>
									<tr>
										<td>27</td>
										<td>28</td>
										<td class="on">29</td>
										<td>30</td>
										<td>31</td>
										<td class="bgp">1</td>
										<td class="bgp">2</td>
									<tr>
									<tr>
										<td class="bgp">3</td>
										<td class="bgp">4</td>
										<td class="bgp">5</td>
										<td class="bgp">6</td>
										<td class="bgp">7</td>
										<td class="bgp">8</td>
										<td class="bgp">9</td>
									<tr>
								</tbody>
							</table>
						</div>
						
					</div>

				</div>
			</div>
			<!-- (article_num7) 달력 -->
			
			<!-- 배너 -->
			<div class="article_num7" style="align:center;">
				<div class="w220" style="align:center; margin-left: 5px; height:53px; ">
					<a href="#"><img width="210" height="46" src="<c:url value='/resources/skin/images/todo/banner_1.gif' />" onmouseover="this.src='<c:url value='/resources/skin/images/todo/banner_1_on.gif' />' " onmouseout="this.src='<c:url value='/resources/skin/images/todo/banner_1.gif' />' " alt="Solution Providers" /></a>
				</div>
				<div class="w220" style="align:center; margin-left: 5px; height:53px; ">
					<a href="#"><img width="210" height="46" src="<c:url value='/resources/skin/images/todo/banner_2.gif' />" onmouseover="this.src='<c:url value='/resources/skin/images/todo/banner_2_on.gif' />' " onmouseout="this.src='<c:url value='/resources/skin/images/todo/banner_2.gif' />' " alt="Sample Request" /></a>
				</div>
				<div class="w220" style="align:center; margin-left: 5px; height:53px; ">
					<a href="#"><img width="210" height="46" src="<c:url value='/resources/skin/images/todo/banner_3.gif' />" onmouseover="this.src='<c:url value='/resources/skin/images/todo/banner_3_on.gif' />' "  onmouseout="this.src='<c:url value='/resources/skin/images/todo/banner_3.gif' />' " alt="where to buy" /></a>
				</div>
			</div>
			<!-- //배너 -->
			
		</div>
		<!-- //(right_section) -->
		
	</div>
	<!-- //(container) -->

</div>
<!-- //(wrapper) -->




<script type="text/javascript">
	$(function() {
		
		//ajax_jquery_loading("<spring:url value="/soa/composite/instance/listbyfilter?partition=default&composite=BPELDBAdapter_SelectEmployees&revision=1.0&flowId=" />", "GET", "#soa-composites","#refresh-spin1", "");
		//init
		// init TO-DO
		myTodoSearch();
		// init 프로세스현황
		prcStateSearch();
		// init 프로세스 지연현황
		prcDelaySearch();
		// init 담당자현황
		prcCharge();
		//ajax_jquery("<spring:url value="/bpm/workflow/taskApproval" />", "GET", "#taskApproval","#refresh-spin1", "");
		//admin
		bpmAdmin();
		
		$('#composite-refresh-btn').click(function () {
			var flowId = $("#SearchFlowId").val();
			if(flowId == '') {
				alert('please input flowId!');
				return;
			} else {
				if(flowId == '*') {
					flowId = "";
				}
				ajax_jquery_loading("<spring:url value="/soa/composite/flow/listbyfilter" />?partition=${param.partition}&composite=${param.composite}&revision=${param.revision}&flowId="+flowId, "GET", "#soa-composites","#refresh-spin1", "");
			}
		});
		
		$('#fault-refresh-btn').click(function () {
			var flowId = $("#SearchFlowId").val();
			ajax_jquery_loading("<spring:url value="/soa/composite/faults" />?flowId="+flowId, "GET", "#soa-faults","#refresh-spin2", "");		});
		
		$('#composite-search-btn').click(function () {
			var flowId = $("#SearchFlowId").val();
			if(flowId == '') {
				alert('please input flowId!');
				return;
			} else {
				if(flowId == '*') {
					flowId = "";
				}
				ajax_jquery_loading("<spring:url value="/soa/composite/flow/listbyfilter" />?partition=&composite=&revision=&flowId="+flowId, "GET", "#soa-composites","#refresh-spin1", "");
			}
		});
		
		$('#process-search-btn').click(function () {
			var flowId = $("#SearchFlowId2").val();
			var instanceId = $("#SearchInstanceId").val();
			var componentname = $("#SearchProcessName").val();
			var state = $("#SearchProcessState option:selected").val();
			var pageSize = $("#SearchProcessPageSize option:selected").val();
			
			//if(flowId == '' && instanceId == '' && componentname == '') {
			//	alert('please input search value!');
			//	return;
			//} else {
			//	if(flowId == '*') {
			//		flowId = "";
			//	}
				//flowId=10010&componentInstanceId=bpmn:10020&componentName=SampleProcess&pageSize=0
				ajax_jquery_loading("<spring:url value="soa/component/instance/listbyfilter" />?flowId="+flowId+"&componentInstanceId="+instanceId+"&componentName="+componentname+"&state="+state+"&pageSize="+pageSize, "GET", "#bpm-instances","#refresh-spin3", "");
			//}
		});
		
		/*
		* 나의 할일 
		*/
		$("input[name=myTodo]").click(function(){
			myTodoSearch();
		});
		/*
		* 프로세스 현황 radio 버튼
		$("input[name=prcState]:checked").val();
		*/
		$("input[name=prcState]").click(function(){
			prcStateSearch();
		});
		
		/*
		* 프로세스 지연현황 radio 버튼
		*/
		$("input[name=prcDelay]").click(function(){
			prcDelaySearch();
		});
		/*
		* 담당자현황 radio버튼
		*/
		$("input[name=aprState]").click(function(){
			prcCharge();
		});
		
		/*
		* admin state select
		*/
		$("#adminState").change(function(){
			bpmAdmin();
		});
		$("#adminOverdue").change(function(){
			bpmAdmin();
		});
	});
	
	getFaults = function(flowId) {
		ajax_jquery_loading("<spring:url value="/soa/composite/faults" />?flowId="+flowId, "GET", "#soa-faults","#refresh-spin2", "");
	}
	
	searchFlow = function(partition, composite, revision) {
		ajax_jquery_loading("<spring:url value="/soa/composite/flow/listbyfilter" />?partition="+partition+"&composite="+composite+"&revision="+revision+"&flowId=", "GET", "#soa-composites","#refresh-spin1", "");
	}
	
	openFaultDetail = function(flowId, faultId)  {
		$('#faultDetailInfo').empty();
		$('#faultDetailAction').modal('show');
		//$('#ModalTracker').css("height", "550px");
		ajax_jquery_loading("<spring:url value="/soa/composite/faultdetail" />?flowId="+flowId+"&faultId="+faultId, "GET", "#faultDetailInfo","", "");
		//ajax_jquery_loading("<spring:url value="/bpm/instance/tracker" />?p_title="+encodeURIComponent(title)+"&p_processdn="+encodeURIComponent(processDn)+"&p_instanceid="+instanceId, "GET", "#faultDetailInfo", "");	
	}
	
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
			indent += "&nbsp;";
		}
		
		return indent;
	}
	
	openFlowTrace = function(flowId) {
		var html = "";
		$('#flowTraceInfo').empty();
		$('#flowTrace').modal('show');
		
		$.ajax({
			url:"<spring:url value="/soa/flow/flowTrace" />?flowId="+flowId,
			success:function(data){
				var jsonObject = JSON.parse(data /* your original json data */);
				jsonToHtml(0, jsonObject, html);
				
				$("#flowTraceInfo").html(
				"<table style=\"table-layout:fixed;\" width=\"100%\" class=\"table table-condensed tree\" style=\"font-size:8px;\">\n"
				+ "<thead>\n"
				+ "<tr>\n"
				+ "<th>Instance</th>\n"
				+ "<th width=\"80\">Type</th>\n"
				+ "<th width=\"80\">Usage</th>\n"
				+ "<th width=\"80\">State</th>\n"
				+ "<th width=\"180\">Time</th>\n"
				+ "<th width=\"180\">Composite</th>\n"
				+ "</tr>\n"
				+ "</thead>\n"
				+ "<tbody>\n"
				+$("#flowTraceInfo").html()
				+ "</tbody>\n"
				+ "</table>\n");
				
				$('.tree').treegrid();
			},
			beforeSend: function() {
				$('#spin-auditflow').css("display", "");

			}, 
			error : function(request, status, error) {
				alert("code : " + request.status + "\r\nmessage : " + request.reponseText);
			},
			complete : function() {
				$('#spin-auditflow').css("display", "none");
			}
		});
	}
	
	openAlterFlow = function(componentInstanceId)  {
		$('#alterFlowInfo').empty();
		$('#alterFlow').modal('show');
		
		$.ajax({
			url:"<spring:url value="/bpm/process/grab/info" />?instanceId="+componentInstanceId,
			success:function(data){
				$('#alterFlowInfo').append(data);
			},
			beforeSend: function() {
				$('#spin-alterflow').css("display", "");
			}, 
			error : function(request, status, error) {
				$('#alterFlow').modal('show');
				$('#alterFlowInfo').append(error);
			},
			complete : function() {
				$('#spin-alterflow').css("display", "none");
			}
		});
		
		//$('#ModalTracker').css("height", "550px");
		//ajax_jquery_loading("<spring:url value="/bpm/process/grab/info" />?instanceId="+componentInstanceId, "GET", "#alterFlowInfo","", "");
		//ajax_jquery_loading("<spring:url value="/bpm/instance/tracker" />?p_title="+encodeURIComponent(title)+"&p_processdn="+encodeURIComponent(processDn)+"&p_instanceid="+instanceId, "GET", "#faultDetailInfo", "");	
	}
	
	/**
	* 나의 할일 SAP GUI
	*/
	openSapUI = function (activityName, tcode, docnum){
		var paramKey = '';
		
		if(activityName  != 'SAP 전표처리'){
			console.log('법인카드 기안/승인'+activityName);
			// 기안/승인
			tcode = '*ZCDR0040';
			paramKey = 'PA_DOC=';
		}else{
			console.log(activityName);
			// 전표처리
			tcode = 'ZCDR0010_POC';
			paramKey = 'p_docnum=';
		}
		
	    var appParams = "-u=BPMUSER -pw=123456 -gui=192.168.3.25 -language=KO -sid=SEQ -type=Transaction -clt=200 -cmd="+tcode+" p_bukrs=1000;p_empno=304694;"+paramKey+docnum;      // Client 실행 프로그램
	    var appName = "sapshcut.exe";                  // Client 실행 프로그램에 추가될 파라메타
		
		$('#sapguiurl').html(appName+" "+appParams);
		
		var au = new ActiveXObject("WScript.Shell");
		console.log("\"C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\"+appName+"\" "+appParams);
		console.log(appName+" "+appParams);
		au.Run(appName+" "+appParams);
		
		return;
	}
	
	/*
	*
	*/
	openTaskUI = function (taskDisplayUrl,number){
		var width = 1200; //팝업창 넓이
		var height = 600; //팝업창 높이
 		var left = (screen.width - width) / 2 ; //왼쪽 좌표
 		var top = 0; /* (screen.height - height) / 2 ; */ //왼쪽 좌표

		if(taskDisplayUrl == 'SAP'){
			window.open('<spring:url value='/bpm/workflow/sapDetail' />?number='+number,'SOA','width='+width+', height='+(screen.height-10)+', left='+left+', top='+top);
		}else{
			window.open('<spring:url value='/bpm/workflow/taskDetail' />?number='+number,'기안','width='+width+', height='+height+', left='+left+', top='+top);
		}
	}
	
	/*
	*  나의 할일 검색 myTodoSearch
	*/
	myTodoSearch = function (){
		var myTodo = $("input[name=myTodo]:checked").val();
		var myTodoKeyword = $("#myTodoKeyword").val();
		
		var state = "";
		var overdue = "";
		
		/* if(myTodo == ""){

		}
		else if(myTodo == "OVERDUE"){
			state = "ASSIGNED";
			overdue = "YES";
		} */
		
		if(myTodo == "" ||myTodo == "OPEN"){
			state = "ASSIGNED";
			/* overdue = "NO"; */
		}
		else if( myTodo == "OVERDUE"){
			overdue = "YES";
		}
		else if(myTodo == "COMPLETED"){
			state = "COMPLETED";
		}
		
		//?processname="+prcDelayKeyword+"&state="+prcDelay+"&overdue=yes"
		//?title="+prcStateKeyword+"&state="+prcState+"&assignmentFilter=MY_AND_GROUP"
		ajax_jquery_loading("<spring:url value="/bpm/workflow/tasks" />?title="+myTodoKeyword+"&state="+state+"&OVERDUE="+overdue, "GET", "#mytasks","#mytasks_refresh_spin", "");
	}
	
	/*
	*  프로세스 현황 검색 prcStateSearch
	*/
	prcStateSearch = function (){
		//var prcState = $('#prcState').is(':checked');
		
		var prcState = $("input[name=prcState]:checked").val();
		var prcStateKeyword = $("#prcStateKeyword").val();
		/* if (checkCnt == 0) {
			// default radio 체크 (첫 번째)
			$("input[name=prcState]").eq(0).attr("checked", true);
		} */
		
		//alert(prcState);
		//processname=&state=OPEN&overdue=yes&assignmentFilter=MY_AND_GROUP
		
		ajax_jquery_loading("<spring:url value="/bpm/process/wfInstances" />?assignmentFilter=PREVIOUS&state="+prcState+"&title="+prcStateKeyword, "GET", "#taskProcess","#refresh-spin1", "");
	}
	
	/*
	*  프로세스 지연 현황 검색 prcDelaySearch
	*/
	prcDelaySearch = function (){
		var prcDelay = $("input[name=prcDelay]:checked").val();
		var prcDelayKeyword = $("#prcDelayKeyword").val();
		//processname=&state=OPEN&overdue=yes
		
		ajax_jquery("<spring:url value="/bpm/process/worstInstances" />?overdue=YES&orderBy=dueDate&assignmentFilter=ALL&state="+prcDelay+"&title="+prcDelayKeyword, "GET", "#worstInstances","#refresh-spin1", "");
	}
	
	/*
	* prcCharge
	*/
	prcCharge = function (){
		var state = $("input[name=aprState]:checked").val();
		var overdue = "YES";
		
		ajax_jquery("<spring:url value="/bpm/workflow/taskApproval" />?state="+state+"&OVERDUE="+overdue, "GET", "#taskApproval","#refresh-spin1", "");
		
	}
	/*
	* 어드민
	*/
	bpmAdmin = function (){
		var state = $("#adminState option:selected").val();
		var title = $("#adminTitle").val();
		var overdue = $('#adminOverdue').val();
		//processname=&state=OPEN&overdue=yes
		//alert(title);
		ajax_jquery("<spring:url value="/bpm/process/bpmadmin" />?overdue="+overdue+"&assignmentFilter=ALL&state="+state+"&title="+title+"&maxRows=10", "GET", "#bpmadmin","#refresh-spin1", "");
	}

	var openFlag = true;
	 bpmadminView = function (){
		 if(openFlag){
			 $('#adminPage').css("display","block");
			 $('.left_1_section').css("display","none");
			 $('.left_2_section').css("display","none");
			 openFlag = false;
		 }
		 else{
			 $('#adminPage').css("display","none");
			 $('.left_1_section').css("display","block");
			 $('.left_2_section').css("display","block");
			 openFlag = true;
		 }
		 
	 }
	 
	 searchTaskApproval = function(instanceid) {
		var state = $("input[name=aprState]:checked").val();
		var overdue = "YES";
		
		ajax_jquery("<spring:url value="/bpm/workflow/taskApproval" />?state="+state+"&OVERDUE="+overdue+"&instanceId="+instanceid, "GET", "#taskApproval","#refresh-spin1", "");
	 }
		
</script>

