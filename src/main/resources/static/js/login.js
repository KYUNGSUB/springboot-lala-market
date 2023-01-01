$(function() {
	var idFlag = false;
	var pwFlag = false;
	
	$("#id").on("keyup", function(e) {
		var regEx = /^[a-zA-Z0-9]{5,20}$/;
		var idVal = $("#id").val();
		if(idVal.length > 0 && !regEx.test(idVal)) {
			$("#id").css("color", "red");
			idFlag = false;
		} else {
			$("#id").css("color", "blue");
			idFlag = true;
		}
	});
	
	$("#pwd").on("keyup", function(e) {
		var regEx = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{5,20}$/;
//		var regEx = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,}$/;
		var pwVal = $("#pwd").val();
		if(pwVal.length > 0 && !regEx.test(pwVal)) {
			$("#pwd").css("color", "red");
			pwFlag = false;
		} else {
			$("#pwd").css("color", "blue");
			pwFlag = true;
		}
	});

	$("form").on("submit", function(e) {
		if($("#id").val().length == 0) {
			alert("아이디를 입력해 주세요!");
			$("#id").focus();
			return false;
		}
		if(idFlag == false) {
			alert("아이디를 작성 정책에 맞게 입력해 주세요!");
			$("#id").focus();
			return false;
		}
		
		if($("#pwd").val().length == 0) {
			alert("비밀번호를 입력해 주세요!");
			$("#pwd").focus();
			return false;
		}
		if(pwFlag == false) {
			alert("비밀번호 생성규칙에 맞게 다시 입력해 주세요!");
			$("#pwd").focus();
			return false;
		}
		return true;
	});
	$("#id").trigger("keyup");
});