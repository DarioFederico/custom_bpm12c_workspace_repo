<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<!-- 
Smart developers always View Source. 

This application was built using Adobe Flex, an open source framework
for building rich Internet applications that get delivered via the
Flash Player or to desktops via Adobe AIR. 

Learn more about Flex at http://flex.org 
// -->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!--  BEGIN Browser History required  section -->
<link rel="stylesheet" type="text/css" href="history/history.css" />
<!--  END Browser History required section -->

<title>Process Monitoring - View, Modeler</title>
<script src="AC_OETags.js" language="javascript"></script>
<!--  BEGIN Browser History required section -->
<script src="history/history.js" language="javascript"></script>
<!--  END Browser History required section -->

<style>
html {
	height: 100%;
}

body {
	margin: 0px;
	overflow: hidden;
	height: 100%
}
</style>

<script language="JavaScript" type="text/javascript">// -----------------------------------------------------------------------------
// Globals
// Major version of Flash required
var requiredMajorVersion = 9;
// Minor version of Flash required
var requiredMinorVersion = 0;
// Minor version of Flash required
var requiredRevision = 124;
// -----------------------------------------------------------------------------
</script>
</head>

<script language="JavaScript" type="text/javascript">	
	
var hasProductInstall = DetectFlashVer(6, 0, 65);

//Version check based upon the values defined in globals
var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);

var actionType = "insert";
var processType = 'approval';  // approval, workflow ( ?processId=''&processVer='')
var mode = '${param.mode}';	//design, runtime, model'
var partition = '${param.partition}';
var compositeId = '${param.compositeid}';
var processId = '${param.processid}';
var version = '${param.version}';
var instanceId = '${param.instanceid}';
var process_model;
var custom_data_xml;
var activity_mapping_xml;
var assignmentFilter = '${param.assignmentFilter}';
var username = '${param.username}';

switch(processType) {
	//case 'workflow' : process_model = "data/TEST_DOMAIN_PROCESSID_VERSION_BUSINESSKEY.xml"; break;
	case 'workflow' : process_model = "<c:url value='/tracker/custom/model?partition_id="+partition+"&composite_id="+compositeId+"&process_id="+processId+"&revision_tag="+version+"'/>"; break;
	case 'approval' : process_model = "<c:url value='/tracker/custom/model?partition_id="+partition+"$composite_id="+compositeId+"$process_id="+processId+"$revision_tag="+version+"'/>"; break;
	default : alert("processType is required!!");
}

switch(mode) {
	case 'design' : activity_mapping_xml = "<c:url value='/tracker/custom/getProcessActivitiesXML?partition_id="+partition+"$composite_id="+compositeId+"$process_id="+processId+"$revision="+version+"$username="+username+"'/>"; break;
	//case 'design' : activity_mapping_xml = "<c:url value='/tracker/custom/getDummyProcessActivitiesXML?partition_id="+partition+"$composite_id="+compositeId+"$process_id="+processId+"$revision="+version+"$username="+username+"'/>"; break;
	case 'runtime' : custom_data_xml = "<c:url value='/tracker/custom/getProcessAuditDataXML2?instance_id="+instanceId+"$username="+username+"'/>"; break;
	//case 'runtime' : custom_data_xml = "<c:url value='/tracker/custom/getProcessAuditDataXML3?instance_id="+instanceId+"$composite_id="+compositeId+"$version="+version+"$process_id="+processId+"'/>"; break;
	case 'model' : custom_data_xml = "<c:url value='/tracker/custom/getProcessAggregatedTaskDataXML?assignmentfilter="+assignmentFilter+"$username="+username+"'/>"; activity_mapping_xml = ""; break;
	default : alert("mode is required!!");
}


var sSwfParams = "mode|"+mode+","
              +"domain|"+partition+","		// partition
              +"process_id|"+processId+","	// componentId
              +"version|"+version+","		// compositeVersion
              +"business_key|,"
              +"process_type|"+processType+","
              +"process_model|"+process_model+","
              +"custom_sensor|"+custom_data_xml+","
              +"activity_mapping|"+activity_mapping_xml+","
              +"scale|${param.scale},"
			  +"activity_list_view|${param.activity_list_view},"
              +"minimap_view|${param.minimap_view},"
              +"only_process_view|${param.only_process_view},"
              +"legend_view|${param.legend_view},"
              +"title_view|${param.title_view},"
              +"refresh_time|${param.refresh_time},"
              +"save_view|true,"
              +"refresh_view|true,"
              +"activity_set_view|true,"
              +"sub_process_set_view|false,"
              +"process_name_view|true,"
              +"default_title|Test Process";

	if ( hasProductInstall && !hasRequestedVersion ) {
		// DO NOT MODIFY THE FOLLOWING FOUR LINES
		// Location visited after installation is complete if installation is required
		var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
		var MMredirectURL = window.location;
	 document.title = document.title.slice(0, 47) + " - Flash Player Installation";
	 var MMdoctitle = document.title;
	
		AC_FL_RunContent(
			"src", "playerProductInstall",
			"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
			"width", "100%",
			"height", "100%",
			"align", "middle",
			"id", "OracleTracker",
			"quality", "high",
			"bgcolor", "#869ca7",
			"name", "OracleTracker",
			"wmode", "transparent",
			"allowScriptAccess","sameDomain",
			"type", "application/x-shockwave-flash",
			"pluginspage", "http://www.adobe.com/go/getflashplayer"
		);
	} else if (hasRequestedVersion) {
		// if we've detected an acceptable version
		// embed the Flash Content SWF when all tests are passed
		AC_FL_RunContent(
				"src", "OracleTracker.swf",
				"width", "100%",
				"height", "100%",
				"align", "middle",
				"id", "OracleTracker",
				"quality", "high",
				"bgcolor", "#869ca7",
				"name", "tracker",
				"wmode", "transparent",
				"flashvars","swfParams="+sSwfParams,
				"allowScriptAccess","sameDomain",
				"type", "application/x-shockwave-flash",
				"pluginspage", "http://www.adobe.com/go/getflashplayer"
		);
	} else {  // flash is too old or we can't detect the plugin
	 var alternateContent = 'Alternate HTML content should be placed here. '
		+ 'This content requires the Adobe Flash Player. '
		+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
	 document.write(alternateContent);  // insert non-flash content
	}

	function createXMLHttpRequest() {
    	xmlHttp = (window.ActiveXObject) ? new ActiveXObject("Microsoft.XMLHTTP") : (window.XMLHttpRequest)	? new XMLHttpRequest() : null;
	}

	function callSubProcess(nodeId, state, task_number) {
		parent.callSubProcess(nodeId, state);
	}
	
	function callModel(domainId, processId, revisionTag, businessKey) {
		parent.parent.callModel(domainId, processId, revisionTag, businessKey);
	}
	
	function callUrl(node_id, label, state, task_number) {
		parent.callUrl(node_id, label, task_number);
	}

	function approveHistory(node_id, state) {
		parent.parent.approveHistory(node_id, state);
	}

	function saveProcessModelXML( param ) {
		//alert( param.filePath );
		//alert( param.fileName );
		try {
			//parent.getViewScriptModel(param.fileData);
		} catch (e) {
			sendToActionScript(0, null);	// fail
		}
		//document.saveLink.location = "common/createXMLFile.jsp";
		//document.saveForm.target = "saveLink";
		//document.saveForm.action = "common/createXMLFile.jsp";
		//document.saveForm.method = "post";
		//document.saveForm.PATH.value = param.filePath;
		//document.saveForm.FILENAME.value = param.fileName;
		//document.saveForm.DATA.value = param.fileData;
		//document.saveForm.submit();
		
//		var o = document.getElementById("model_xml");
//		o.firstChild.nodeValue = param.fileData;
		
		var model_script = param.fileData;
		var description = "description";
		var author = "weblogic";
		var u;
		
		u  = "<c:url value='/tracker/custom/mergeTrackerModel'/>";
		
		var queryString = "partition_id="+partition+"&composite_id="+compositeId+"&process_id="+processId+"&revision_tag="+version+"&model_xml="+model_script+"&author="+author+"&description="+description;
		//var queryString = "filename="+param.fileName+"&model_xml="+param.fileData;
		
		getRequestXML("post", u, true, queryString);
	}
	
	function getRequestXML( _method, _url, _asy, _queryString ){
		createXMLHttpRequest();     
		xmlHttp.onreadystatechange = handleResponseXML;
		xmlHttp.open( _method, _url, _asy);
		
		if(_method.toLowerCase() == 'post') {
			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			xmlHttp.send(_queryString);
		} else {
			xmlHttp.send('');
		}
	}
	
	function handleResponseXML() {
		if(xmlHttp.readyState == 4) {
			if(xmlHttp.status == 200) {
				var xmlDoc = xmlHttp.responseXML;          
				
				sendToActionScript(1, version);
				actionType = "update";
				//sendToActionScript(0, null);
				
				/* 
				if(xmlDoc.getElementsByTagName("error_message")[0].childNodes.length == 0) {
					sendToActionScript(1, version);
				}
				else {
					alert(xmlDoc.getElementsByTagName("error_message")[0].childNodes[0].nodeValue);
					sendToActionScript(0, null);
				} */
			}
		}
	}

	function thisMovie( movieName ) {
		if (navigator.appName.indexOf("Microsoft") != -1) 
			return window[movieName];
		else 
			return document[movieName];
	}
     //1:success  0: fail   100:save (테스트 완료)
     function sendToActionScript( value, newVersion ) {
         thisMovie("tracker").sendToActionScript(value, newVersion);
     }
	
	 function sendToProcessTitle(title) {
		 try {
			 if(title != '')
				 	thisMovie("tracker").sendToProcessTitle(title);
			 
		 } catch (e) {
			 alert(e);
		 }
	 }
	 
	 // 다중 컴포넌트 삭제 (테스트 완료)
     function dropComponents( components ) {
         var componentsObj = eval('(' + components + ')');
         try {
        	 parent.xpdl.removeProcessComponents(componentsObj);
			 parent.getProcessDefaultInfo();
         } catch (e) {
        	 alert(e);
         }
      }
     
	 // ACTIVITY, EVENT, GATEWAY Dray & Drop (테스트 완료)
     function setComponent(id, name, type, gatewayType) {
		 parent.activityClickListner('append', id, name, type, gatewayType);
     }
	 
	 // 프로세스 이름설정
	 function setProcessTitle(title) {
		 alert(title);
	 }
	 
	 // 컴포넌트 더블 클릭 (테스트 완료)
	 function dClickComponent(id, type) {
		 alert("Dobble Click : "+id);
		 parent.activityClickListner("", id, "", type, "");
	 }
	 
	 // 컴포넌트 이름 변경, 완료
	 function setComponentName(id, name) {
		 parent.activityClickListner("rename", id, name, "", "");
	 }
	 
	 // 이승민
	 // 컴포넌트 삭제 (라인 제외)  -> 라인 포함하도록 유도
	 function dropComponent(id) {
		 parent.activityClickListner("remove", id, "", "", "");
	 }
	 
	 // 이승민
	 // Transition Drag & Drop (테스트 완료)
	 function setTransition(id, fromId, toId) {
		  parent.transitionClickListner('append', id, '', fromId, toId);
	 }
	 
	 // 이승민
	 // Transition 더블 클릭 (프리즘에서 이름 변경 창이 같이 뜸 -> 설계 변경 필요함)
	 function dClickTransition(id) {
		  parent.transitionClickListner('', id, '', '', '');
	 }
	 
	 // 이승민
	 // Transition 삭제 (테스트 완료)
	 function dropTransition(id) {
		  parent.transitionClickListner('delete', id, '', '', '');
	 }
	 
	 // 이승민
	 // Transition 이름 변경 (테스트 완료)
	 function setTransitionName(id, label) {
		   parent.transitionClickListner('rename', id, label, '', '');
	 }
	 
	 // 이승민
	 // Transition 수정 (테스트 완료)
	 function modifyTransition(id, fromId, toId) {
		   parent.transitionClickListner('replace', id, '', fromId, toId);
	 }
     -->
</script>

<body scroll="no">
	<noscript>
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="tracker" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie"
				value="OracleTracker.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#869ca7" />
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="wmode" value="transparent" />
			<embed src="OracleTracker.swf"
				quality="high" bgcolor="#869ca7" width="100%" height="100%"
				name="OracleTracker" align="middle" play="true" loop="false" quality="high"
				allowScriptAccess="sameDomain" type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
	</noscript>
</body>
</html>