<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="<c:url value='/resources/js/jplot1.0.8r1250/excanvas.js'/>"></script><![endif]-->
<link href="<c:url value='/resources/js/jplot1.0.8r1250/jquery.jqplot.min.css' />" rel="stylesheet">
<link href="<c:url value='/resources/css/simplegrid.css' />" rel="stylesheet">
<style type="text/css">
    .jqplot-data-label {
		color: #ffffff;
		font-size: 0.8em;
		font-family:'Malgun Gothic';
    }
	
	.jqplot-title {
		font-size: 0.9em;
		font-family:'Malgun Gothic';
	}
	
	.main-title {
		font-size: 1.2em;
		text-align: center;
		font-family:'Malgun Gothic';
	}
  </style>
  
<script src="<c:url value='/resources/js/jplot1.0.8r1250/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/js/jplot1.0.8r1250/jquery.jqplot.min.js'/>"></script>
<script class="include" type="text/javascript" src="<c:url value='/resources/js/jplot1.0.8r1250/plugins/jqplot.barRenderer.min.js'/>"></script>
<script class="include" type="text/javascript" src="<c:url value='/resources/js/jplot1.0.8r1250/plugins/jqplot.highlighter.min.js'/>"></script>
<script class="include" type="text/javascript" src="<c:url value='/resources/js/jplot1.0.8r1250/plugins/jqplot.cursor.min.js'/>"></script>
<script class="include" type="text/javascript" src="<c:url value='/resources/js/jplot1.0.8r1250/plugins/jqplot.pointLabels.min.js'/>"></script>
<script language="javascript" type="text/javascript" src="<c:url value='/resources/js/jplot1.0.8r1250/plugins/jqplot.categoryAxisRenderer.min.js'/>"></script>

<script class="code" type="text/javascript">
	$(document).ready(function () {
		$.getJSON("http://bgfdemo:8001/obpm-workspace-ps6/sso/analysis/getProcessActivityAnalysisData?componentname=${param.componentname}","json").success(function(data) {
			
			//var s1 = [[2002, 112000], [2003, 122000], [2004, 104000], [2005, 99000], [2006, 121000], 
			//[2007, 148000], [2008, 114000], [2009, 133000], [2010, 161000], [2011, 173000]];	//line
			//var s2 = [[2002, 10200], [2003, 10800], [2004, 11200], [2005, 11800], [2006, 12400], 	//bar
			//[2007, 12800], [2008, 13200], [2009, 12600], [2010, 13100]];
			
			//var s1 = [['act1', 10], ['act2', 10], ['act3', 5]];
			//var s2 = [['act1', 14], ['act2', 12], ['act3', 15]];
			
			//var s1 = [[1, 10], [2, 10], [3, 5]];
			//var s2 = [[1, 14], [2, 12], [3, 15]];
			
			var ct = [];	//count	bar
			var lt = [];	//leadtime
			//var content = $.parseJSON( data);
			
			$.each(data.ct_result, function(key, value) {
			
				switch (key) {
				  case 'CreateMeeting'  : key = "회의진행"; break;
				  case 'ReceiveEvaluationResult' : key = "심사결과"; break;
				  case 'ConfirmRequest'  : key = "요청확인"; break;
				  case 'Verification'  : key = "검증"; break;
				  case 'ReceiveDecision'  : key = "판단"; break;
				  case 'ManagerDecision'  : key = "팀장판단"; break;
				  default   : key = "Undefined"; break;
				}
				
				ct.push([key,value]);
				//alert(key + ":" + value);
			});
			
			$.each(data.lt_result, function(key, value) {
				
				switch (key) {
				  case 'CreateMeeting'  : key = "회의진행"; break;
				  case 'ReceiveEvaluationResult' : key = "심사결과"; break;
				  case 'ConfirmRequest'  : key = "요청확인"; break;
				  case 'Verification'  : key = "검증"; break;
				  case 'ReceiveDecision'  : key = "판단"; break;
				  case 'ManagerDecision'  : key = "팀장판단"; break;
				  default   : key = "Undefined"; break;
				}
				
				lt.push([key,value]);
				//alert(key + ":" + value);
			});
			
			plot1 = $.jqplot("chart1", [ct, lt], {
				title: "업무 별 현황",
				// Turns on animatino for all series in this plot.
				animate: true,
				// Will animate plot on calls to plot1.replot({resetAxes:true})
				animateReplot: true,
				cursor: {
					show: true,
					zoom: true,
					looseZoom: true,
					showTooltip: false
				},
				series:[
					{
						pointLabels: {
							show: true
						},
						renderer: $.jqplot.BarRenderer,
						showHighlight: false,
						yaxis: 'y2axis',
						rendererOptions: {
							// Speed up the animation a little bit.
							// This is a number of milliseconds.  
							// Default for bar series is 3000.  
							animation: {
								speed: 2500
							},
							barWidth: 15,
							barPadding: -15,
							barMargin: 0,
							highlightMouseOver: false
						}
					}, 
					{
						rendererOptions: {
							// speed up the animation a little bit.
							// This is a number of milliseconds.
							// Default for a line series is 2500.
							animation: {
								speed: 2000
							}
						}
					}
				],
				axesDefaults: {
					pad: 0
				},
				axes: {
					// These options will set up the x axis like a category axis.
					xaxis: {
						//tickInterval: 1,
						//drawMajorGridlines: false,
						//drawMinorGridlines: true,
						//drawMajorTickMarks: false,
						//rendererOptions: {
						//	tickInset: 0.5,
						//	minorTicks: 1
						//}
						renderer: $.jqplot.CategoryAxisRenderer
					},
					yaxis: {
						tickOptions: {
							formatString: "%'d 초"
						},
						rendererOptions: {
							forceTickAt0: true
						}
					},
					y2axis: {
						tickOptions: {
							formatString: "%'d 건"
						},
						rendererOptions: {
							// align the ticks on the y2 axis with the y axis.
							alignTicks: true,
							forceTickAt0: true
						}
					}
				},
				highlighter: {
					show: true, 
					showLabel: true, 
					tooltipAxes: 'y',
					sizeAdjust: 7.5 , tooltipLocation : 'ne'
				}
			});
			
		});
	});
</script>
<div class="grid grid-pad">
	<div class="col-1-1">
		<div id="chart1" class="content"></div>
    </div>
	<div class="col-1-1">
		<div id="chart1" class="content"></div>
    </div>
</div>