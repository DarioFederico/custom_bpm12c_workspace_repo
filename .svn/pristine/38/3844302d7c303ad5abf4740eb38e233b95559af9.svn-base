<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<title>${params.popTitle }</title>

<link rel="stylesheet" href="<c:url value='../../resources/skin/css/popup.css' />" />
<link rel="stylesheet" type="text/css" href="../../resources/skin/datetimepicker/jquery.datetimepicker.css"/>
<style type="text/css">
.custom-date-style {
	background-color: red !important;
}
.input{	
}
.input-wide{
	width: 500px;
}
</style>
<script type="text/javascript" src="<c:url value='../../resources/skin/js/jquery-1.11.0.min.js' />"></script>
<script src="../../resources/skin/datetimepicker/jquery.js"></script>
<script src="../../resources/skin/datetimepicker/build/jquery.datetimepicker.full.js"></script>
</head>
<body>

<div>
	<form id="invoke" name="invoke" action='<spring:url value='/soa/composite/invoke' />' method="post" onSubmit="javascript:formChk();">
		<table class="board_reservation_month">
			<thead>
			<th style="height:30px; font-size: 20px;" colspan="2" >${params.popTitle }</th>
			</thead>
			<tbody>
				<tr>
					<th>비용번호</th>
					<td><input type="text" id="expenseNumber" name="expenseNumber" value=""/></td>
				</tr>
				<tr>
					<th>지불구분</th>
					<td>
						<select name="paymentType">
							<option value="corporation">법인</option>
							<option value="individual">개인</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>비용구분</th>
					<td>
						<input type="radio" name="expenseType" value="차량유지비" checked>차량유지비
						<input type="radio" name="expenseType" value="접대비">접대비
						<input type="radio" name="expenseType" value="복리후생비">복리후생비
						<input type="radio" name="expenseType" value="출장비">출장비
					</td>
				</tr>
				<tr>
					<th>완료요청일</th>
					<td><input type="text" id="dueDate" name="dueDate" value=""/></td>
				</tr>
				<tr>
					<th>금액</th>
					<td><input type="text" id="amount" name="amount" value="" onkeyup="number_chk(this);" onkeypress="javascript:if((event.keyCode<48)||(event.keyCode>57))event.returnValue=false;" style="ime-mode:disabled; text-align: right;"/></td>
				</tr>
				<tr style='display:none;'>
					<th>SOA DOC NUMBER</th>
					<td><input type="text" id="docnum" name="docnum" value="" /></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div align="right"><input type="submit" value="신청"></div>

		<input type="hidden" name="partition" value="default">
		<input type="hidden" name="composite" value="ExpenseReportApplication">
		<input type="hidden" name="service" value="ExpenseReportProcessDirect.service">
		<input type="hidden" name="operation" value="start">
		<input type="hidden" name="payloadXML" value="">
		
		<input type="hidden" name="requesterId" value="">
		<input type="hidden" name="requestDate" value="">
		<input type="hidden" name="justification" value="">
		<input type="hidden" name="line" value="">
		<input type="hidden" name="type" value="">
		<input type="hidden" name="ruleset" value="">
	
		<!-- 2016.03.16 추가 SOA_RFC -->
		<input type="hidden" name="currency" value="">
	</form>
</div>

</body>
<script>
$('#dueDate').datetimepicker({
	step:'30',
	format:'Y-m-d H:i:00',
	value:'${requestScope.nextDay}'
});
$(document).ready(function(){
	var ran = Math.floor(Math.random()*900000+100000);
	$('#expenseNumber').val(ran);
});

$('#invoke').submit(function(){
	if($('#amount').val() == ''){
		alert('금액을 입력 하십시오.');
		//document.invoke.amount.focus();
		$('#amount').focus();
		return false;
	}else{
		//alert($('#amount').val().replace(/,/gi, ''));
		$('#currency').val($('#amount').val());
		$('#amount').val($('#amount').val().replace(/,/gi, ''));
		$('#invoke').submit();
	}
	
});

function number_chk(obj){
	var val = obj.value.replace(/,/g, "");
	var val2 = val.substr(0, 1);
	var val3 = val.length;
	if(val2 == 0){
		val = val.substr(1, val3);
	}
	obj.value = num_format(val);
}

function num_format(n){
	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식
	n = String(n);    //숫자 -> 문자변환
	while(reg.test(n)){
		n = n.replace(reg, "$1" + "," + "$2");
	}
	return n;
}

</script>
</html>