<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script src="<c:url value='/resources/bpms/js/custom/kospo/schema.js'/>"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		ajax_jquery("<spring:url value="/bpm/instance/getcomments" />?instanceid=${task.instanceId}", "GET", "#commentlist", "");
		
		//ajax_jquery("<spring:url value="/bpm/instance/getattachments" />?instanceid=${task.instanceId}", "GET", "#attachmentlist", "");
		
		//ajax_jquery("<spring:url value="/workflow/task/getapprovehistory" />?instanceid=${task.instanceId}", "GET", "#approvehistory", "");
		
	})

	doAction = function(key, type) {
		
		var bizxml = "<BODY xmlns=\"http://bpm.oracle.co.kr/demo/MasterTypes\">\n";
		bizxml += "<DATA>\n";
		bizxml += "<"+prefix+":"+root+" xmlns:"+prefix+"=\""+ns+"\">\n";
		
		$("fieldset").each(function() {
			var result = "";
			if($(this).attr('type') == 'single') {
				bizxml += "<"+prefix+":"+$(this).attr('name')+">\n";
		
				$(this).find(':input').each(function(index) {
					if($(this).attr('id') != undefined) {
						bizxml += "<"+prefix+":"+$(this).attr('id')+">"+$(this).val()+"</"+prefix+":"+$(this).attr('id')+">\n";
					}
				});
				
				bizxml += "</"+prefix+":"+$(this).attr('name')+">\n";
			}	
			
		})
		bizxml += "</"+prefix+":"+root+">\n";
		bizxml +="</DATA>\n";
		bizxml += "</BODY>";
		$("#body_xml").val(bizxml);
		
		if(confirm("처리 하시겠습니까?")) {
			$("#outcome").val(key);
			document.f_taskdetail.submit();
		} else return;
	}
	
	openApplication = function(url) {
		window.open(url, "taskDetailApp", 'width='+screen.width*0.8+', height='+screen.height*0.8+', top=0, left=0, fullscreen=no, resizable=yes, menubar=no,status=no');
	}
</script>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal">×</button>
	<h3>${task.title}</h3>
</div>
<form class="form-horizontal" name="f_taskdetail" method="post" action="<spring:url value="/workflow/task/doaction" />">
<input type="hidden" id="outcome" name="outcome" value="">
<input type="hidden" id="tasknumber" name="tasknumber" value="${requestScope.task.number}">
<input type="hidden" id="redirecturi" name="redirecturi" value="<spring:url value="/" />">
<textarea id="body_xml" name="body_xml" style="display:none"></textarea>
<div id="detail" class="modal-body">
	<c:import url="/workflow/task/embeddedDetail" charEncoding="UTF-8">
		<c:param name="payload" value="${task.payload}" />
		<c:param name="redirectURI" value="obpm/workflow/kospo/taskDetail1" />
	</c:import>
	
	<div id="commentlist" class="box span3" style="width:670px; height:202px"></div>
	<!--
	<div id="attachmentlist" class="box span3" style="width:319px; height:202px"></div>
	<div id="approvehistory" class="box span3" style="width:670px; height:202px"></div>
	-->
</div>
<div class="modal-footer">
<c:choose>
	<c:when test="${fn:length(requestScope.task.customActions) > 0}">
	<c:forEach var="action" items="${requestScope.task.customActions}" varStatus="ts">
	<button type="button" class="btn btn-primary" onClick="doAction('${action.key}', 2)">${action.value}</button>
	</c:forEach>
	</c:when>
</c:choose>
	<a href="#" class="btn" data-dismiss="modal">닫기</a>
</div>
</form>