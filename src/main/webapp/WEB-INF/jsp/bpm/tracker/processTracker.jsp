<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<title>Oracle BPM 11g - Process Tracking Monitoring</title>
<link href="<c:url value='/resources/js/qtip2/jquery.qtip.css' />" rel="stylesheet">
<script src="<c:url value='/resources/bpms/js/jquery-ui/jquery-1.10.2.js'/>"></script>
<script src="<c:url value='/resources/bpms/js/default.js'/>"></script>
<script src="<c:url value='/resources/bpms/js/request.js'/>"></script>
<script src="<c:url value='/resources/js/qtip2/jquery.qtip.js'/>"></script>

<script type="text/javascript">
	function callUrl(node_id, label, task_number) {
		//var width=screen.width; 
		//var height=screen.height; 
	
		//var u = "<c:url value='/${param.issso}/custom/getProcessAggregatedTaskByUser?username=weblogic&activityid="+node_id+"&activityname="+label+"&assignmentfilter=ALL'/>";
		/* historyif.src = "<c:url value='/bpm/workflow/taskHistory?tasknumber="+task_number+"'/>";
		var ifHeight = historyif.contentWindow.document.body.scrollHeight; // IFRAME 으로 링크된 페이지의 높이
    	checkHeightTimer = setInterval(heightCheck, 500);
		document.getElementById("history_div").style.display = "inline"; */
		var u = "<c:url value='/bpm/workflow/taskHistory?tasknumber="+task_number+"'/>";
		CommonUtility.openWindow(u,"taskHistory","400","300","false","yes");
	}

	function closeParticipantsActionList(){
		self.close();
		//document.getElementById("history_div").style.display = "none";
	}

	function callSubProcess(node_id,status) {
		//http://oraclebpm:7003/bpms/tracker/custom/getSubProcessInfoByAPI?instance_id=70041&activity_id=ACT15584785873630&audit_instance_type=END
		
		var uri = "<c:url value='/tracker/custom/getSubProcessInfoByAPI?instance_id=${param.instanceid}&activity_id="+node_id+"&audit_instance_type=END'/>";
		
		$.getJSON(uri,"","")
			.success(function(data) {
				
				var compositedn = data.compositedn;
				var instanceid = data.id;
				var processid = data.componentnm;
				var partition = data.compositedn.substring(0,data.compositedn.indexOf("/"));
				var compositeid = data.compositedn.substring(data.compositedn.indexOf("/")+1, data.compositedn.indexOf("!"));
				var revision = data.compositedn.substring(data.compositedn.indexOf("!")+1, data.compositedn.length);
				
				//console.log(partition);
				//console.log(compositeid);
				//console.log(revision);
				
				CommonUtility.openWindow("<spring:url value='/tracker/custom/main' />?mode=${param.mode}&partition="+partition+"&compositeid="+compositeid+"&processid="+processid+"&version="+revision+"&instanceid="+instanceid+"&business_key=${param.business_key}&activity_list_view=${param.activity_list_view}&legend_view=${param.legend_view}&refresh_time=${param.refresh_time}&title_view=${param.title_view}&only_process_view=${param.only_process_view}&minimap_view=${param.minimap_view}&scale=${param.scale}&assignmentFilter=${param.assignmentFilter}&username=${param.username}&issso=${param.issso}","","1000","600","yes","yes");
				
				//success code
			})
			.error(function() {
				//alert('Error loading JSON data');
			})
			.complete(function() {
				//complete code
			});
	
	}
</script>
<iframe frameborder='no' src="<spring:url value="/resources/bpms/apps/obpm_tracker/OracleBPMTracker.jsp" />?mode=${param.mode}&partition=${param.partition}&compositeid=${param.compositeid}&processid=${param.processid}&version=${param.version}&instanceid=${param.instanceid}&business_key=${param.business_key}&activity_list_view=${param.activity_list_view}&legend_view=${param.legend_view}&refresh_time=${param.refresh_time}&title_view=${param.title_view}&only_process_view=${param.only_process_view}&minimap_view=${param.minimap_view}&scale=${param.scale}&assignmentFilter=${param.assignmentFilter}&username=${param.username}&issso=${param.issso}"
			scrolling="no" style='width:100%;height:99%;z-index:-1';/>
<iframe id="history_if" width="100%" height="100%"  style="z-index:-99;" scrolling="no" style="border:0 solid #FFFFFF" frameborder="0" ></iframe>