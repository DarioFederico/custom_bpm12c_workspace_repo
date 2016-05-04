<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<title>승인자리스트</title>
<link rel="stylesheet" href="<c:url value='../../resources/skin/css/popup.css' />" />
<script type="text/javascript" src="<c:url value='../../resources/skin/js/jquery-1.11.0.min.js' />"></script>
</head>
<body>
<table class="board_reservation_month">
	<thead>
		<tr>
			<th>승인체크</th><th>ID</th><th>이름</th>
		</tr>
	</thead>
	<tbody id="tb">
		<tr>
			<th><input type="radio" value="1"></th>
			<td>approvaluser1</td>
			<td>박명수(차장)</td>
		</tr>
		<tr>
			<th><input type="radio" value="2"></th>
			<td>approvaluser2</td>
			<td>유재석(부장)</td>
		</tr>
		<tr>
			<th><input type="radio" value="3"></th>
			<td>approvaluser3</td>
			<td>송혜교(상무)</td>
		</tr>
		<tr>
			<th><input type="radio" value="4"></th>
			<td>approvaluser4</td>
			<td>전지현(전무)</td>
		</tr>
		<tr>
			<th><input type="radio" value="5"></th>
			<td>approvaluser5</td>
			<td>김태희(사장)</td>
		</tr>	
	</tbody>
</table>
<p>
<div align="center">
	<input type="text" size="63%" id="users"/>
</div>
<p>
<div align="right">
	<input type="button" value="리셋" id="reset"/>&nbsp;
	<input type="button" value="적용" id="ok"/>&nbsp;
	<input type="button" value="취소" id="close"/>
</div>
<input type="hidden" id="line"/>
</body>
<script>
$(document).ready(function(){
	var cnt = 5;//승인자 총수
	var cntArr = new Array(cnt); 
	
	$('#reset').click(function(){
		$('#tb input:radio').prop('checked',false);	//체크박스해제
		$('#users').val("");		//유저순서목록삭제
		$('#line').val("");
		cntArr = new Array();	//배열초기화
	});
	
	$('input[type=radio]').click(function(){
		if(cntArr[$(this).val()]==null){
			var temp = $('#users').val();
			var temp2 = $('#line').val();
			if(temp.length!=0){
				temp += "→";
				temp2 += ",";
			}
			temp += $(this).parent().parent().children().eq(2).text();
			temp2 += $(this).parent().parent().children().eq(1).text();
			
			$('#users').val(temp);
			$('#line').val(temp2)
			
			cntArr[$(this).val()] = $(this).val();
		}
	});
	
	$('#ok').click(function(){
		opener.detail.line.value = $('#line').val();
		opener.detail.appList.value = $('#users').val();
		opener.btnCheck();
		self.close();
	});
	
	$('#close').click(function(){
		self.close();
	});
});
</script>
</html>