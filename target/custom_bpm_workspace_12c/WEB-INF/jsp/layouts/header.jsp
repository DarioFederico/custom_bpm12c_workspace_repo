<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- (wrapper) -->
<div id="wrapper">

	<!-- (header) -->
	<div id="header">

		<!-- (top_section) -->
		<div class="top_section">
			<h1><a href="#"><img src="<c:url value='/resources/skin/images/todo/greenlogo_2.jpg' />" width="275" height="56" alt="서울반도체 BPM" /></a></h1>

			<!-- (searchbox) -->
			<div class="searchbox">
				<div>
					<input type="text" value="Search" />
					<a href="#"><img src="<c:url value='/resources/skin/images/btn/btn_search.gif' />" alt="Search" /></a>
				</div>
			</div>
			<!-- //(searchbox) -->

			<script type="text/javascript">
			$(function(){
                $("div#weather").jContent({
					orientation: 'vertical', 
					easing: "easeOutCirc", 
					duration: 500,
					speed : 1000,
					width : 240,
					height : 62,
					circle: true
				});
			});
			</script>

			<!-- (weather_section) -->
			<div id="weather" class="weather_section">
				<a href="#" class="up prev">up</a>
				<div class="slides">
					<div>
						<p class="txt"><span>ASIA</span><em>SEOUL</em></p>
						<p class="con">
							<span>5.5℃</span>
							<em><img src="<c:url value='/resources/skin/images/icon/icon_wind.png' />" alt="" /></em>
						</p>
					</div>
					<div>
						<p class="txt"><span>ASIA</span><em>JAPAN</em></p>
						<p class="con">
							<span>6.5℃</span>
							<em><img src="<c:url value='/resources/skin/images/icon/icon_serenity.png' />" alt="" /></em>
						</p>
					</div>
					<div>
						<p class="txt"><span>ASIA</span><em>KAZAKHSTAN</em></p>
						<p class="con">
							<span>27.5℃</span>
							<em><img src="<c:url value='/resources/skin/images/icon/icon_wind.png' />" alt="" /></em>
						</p>
					</div>
				</div>
				<a href="#" class="down next">down</a>
			</div>
			<!-- //(weather_section) -->

		</div>
		<!-- //(top_section) -->

		<!-- (gnb_section) -->
		<div class="gnb_section">

			<!-- (gnb) -->
			<ul class="gnb">
				<li>
					<a href="<%= request.getContextPath() %>">Main</a>
					<!-- <div>
						<ul>
							<li><a href="#">BPM</a></li>
							<li><a href="#">KPI</a></li>
							<li><a href="#">Exec. Communications</a></li>
							<li><a href="#">Blogs. Etc</a></li>
							<li><a href="#">Industry News</a></li>
							<li><a href="#">Study case</a></li>
						</ul>
					</div> -->
				</li>
				<li>
					 <a href="#" onclick="invoke()">비용 발생</a>					
				</li>
				
				<c:if test="${sessionScope.accountData.bpm_user_info.attributes.name eq 'bpmadmin'}">
				<li>
					<%-- <a href="#" onclick="window.open('<spring:url value='/bpm/workflow/taskAdmin' />','프로세스관리','width=600,height=400')">프로세스관리</a> --%>					
					<a href="#" onclick="bpmadminView()">프로세스BACK</a>
				</li>
				</c:if>
			</ul>
			<!-- //(gnb) -->
			

			<!-- 20140707_v03 추가 -->
			<div class="it_Support">
				<em>&nbsp;</em>
				<span>&nbsp;</span>
			</div>
			<!-- end 20140707_v03 추가 -->

			<script type="text/javascript">
			$(function(){
				$(".myinfo > a").click(function(){
					$(".myinfo_list").toggle();
				});
			});
			</script>

			<!-- (mymenu) -->
			<div class="mymenu">
				<div class="myinfo">
					<span class="mycon">
						<span class="my_thumbnail">
							<img src="<c:url value='/resources/skin/images/data/${sessionScope.accountData.bpm_user_info.attributes.name}.png' />" width="30" height="30" alt="" />
							<!--  
							<c:choose>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='bpmuser1'}">
									<img src="<c:url value='/resources/skin/images/data/aa.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='draftera'}">
									<img src="<c:url value='/resources/skin/images/data/people_2.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='sapusera'}">
									<img src="<c:url value='/resources/skin/images/data/people_3.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='sapuserb'}">
									<img src="<c:url value='/resources/skin/images/data/people_4.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='approvaluser1'}">
									<img src="<c:url value='/resources/skin/images/data/people_5.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='approvaluser2'}">
									<img src="<c:url value='/resources/skin/images/data/people_6.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='approvaluser3'}">
									<img src="<c:url value='/resources/skin/images/data/people_7.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='approvaluser4'}">
									<img src="<c:url value='/resources/skin/images/data/people_8.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='approvaluser5'}">
									<img src="<c:url value='/resources/skin/images/data/people_9.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='accounta'}">
									<img src="<c:url value='/resources/skin/images/data/people_10.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='executivea'}">
									<img src="<c:url value='/resources/skin/images/data/people_11.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:when test="${sessionScope.accountData.bpm_user_info.attributes.name=='bpmadmin'}">
									<img src="<c:url value='/resources/skin/images/data/people_12.png' />" width="25" height="30" alt="" />
								</c:when>
								<c:otherwise>
									<img src="<c:url value='/resources/skin/images/data/people_13.png' />" width="25" height="30" alt="" />
								</c:otherwise>
							</c:choose>
								-->
						</span>
						<span class="my_contents">
							<em class="welcome">Welcome</em>
							<em class="my_name">${sessionScope.accountData.bpm_user_info.attributes.displayName}</em>
						</span>
					</span>

					<a href="#">my menu list</a>
				</div>
				<div class="myinfo_list" style="display: none;">
					<ul>
						<li><a href="#">Helpdesk</a></li>
						<li><a href="#">Mypage</a></li>
						<li><a href="#">Widgets Option</a></li>
						<li><a href="<%= request.getContextPath() %>/j_spring_security_logout">Logout</a></li>
					</ul>
				</div>
			</div>
			<!-- //(mymenu) -->

		</div>
		<!-- //(gnb_section) -->

	</div>
	<!-- //(header) -->
	
	<script>
		function invoke(){
			var width = 600; //팝업창 넓이
			var height = 400; //팝업창 높이
	 		var left = (screen.width - width) / 2 ; //왼쪽 좌표
	 		var top = 0; /* (screen.height - height) / 2 ; */ //왼쪽 좌표

	 		window.open('<spring:url value="/bpm/workflow/taskInvoke?popTitle=비용발생" />','비용발생','width='+width+', height='+height+', left='+left+', top='+top);
		}
	</script>