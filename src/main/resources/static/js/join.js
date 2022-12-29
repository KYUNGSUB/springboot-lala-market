$(document).ready(function() {
	var idCheck = false;	// 아이디 중복 검사 통과 여부
	var passwordValid = false;	// 비밀번호 유효성 검사 결과
	var password2Valid = false;	// 비밀번호 확인 유효성 검사 결과
	var useridValid = false;	// 아이디 유효성 검사 결과
	var emailValid = false;	// 이메일 유효성 검사 결과

	var formObj = $(".form-join");
	
	// 아이디 유효성 검사
	$("input[name='userid']").on("keyup", function(e) {
		var regEx = /^[a-zA-Z0-9]{5,20}$/;
		var idVal = $("#id").val();
		if(idVal.length > 0 && !regEx.test(idVal)) {
			$("#id").css("color", "red");
			useridValid = false;
		} else {
			$("#id").css("color", "blue");
			useridValid = true;
		}
	});
	
	// 이메일 확인 유효성 검사
//	$("input[name='email']").on("keyup", function(e) {
//		var regExp = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;
//		if( !regExp.test($("input[name='email']").val()) ) {
//			$("input[name='email']").css("color", "#EE5656");
//			emailValid = false;
//		}
//		else {
//			$("input[name='email']").css("color", "blue");
//			emailValid = true;
//		}
//	});

	// 이메일 소속 선택 시 input 태그에 값 표시
	$("#emailCom").on("change", function(e) {
		$("#host").val($("#emailCom").val());
	});
	
	// 비밀번호 유효성 검사
	$("input[name='password']").on("keyup", function(e) {
		var regExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,}$/;
		if( !regExp.test($("input[name='password']").val()) ) {
			$("input[name='password']").css("color", "#EE5656");
			passwordValid = false;
		}
		else {
			$("input[name='password']").css("color", "blue");
			passwordValid = true;
		}
	});
	
	// 비밀번호 확인 유효성 검사
	$("input[name='pwd2']").on("keyup", function(e) {
		var regExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,}$/;
		if( !regExp.test($("input[name='pwd2']").val()) ) {
			$("input[name='pwd2']").css("color", "#EE5656");
			password2Valid = false;
		}
		else {
			$("input[name='pwd2']").css("color", "blue");
			password2Valid = true;
		}
	});

	// 아이디 중복 확인
	$("#duplicateCheck").on("click", function(e) {
		var userid = $("input[name='userid']").val();
		if(userid == null || userid.length == 0) {
			alert("아이디(이메일 형식)를 입력하세요");
			return;
		}
		if(useridValid == false) {
			alert("고유한 형식의 아이디를 사용하세요.");
			return;
		}
		// 서버로 중복 확인 요청
		$.ajax({
		  url: '/member/idCheck',
		  data: {userid: userid},
		  type: 'POST',
		  beforeSend: function(xhr) {
	          xhr.setRequestHeader(
				  window.localStorage.getItem("csrfHeaderName"),
	           	  window.localStorage.getItem("csrfTokenValue")
	          );
	      },
		  success: function(result){
			if(result == "ok") {
				idCheck = true;
				alert("사용할 수 있는 아이디 입니다.");
			} else {
				idCheck = false;
				alert("사용할 수 없는 아이디 입니다.");
			}
		  }
		}); //$.ajax
	});
	
	$(".btn-success").on("click", function(e) {
		e.preventDefault();
		var userid = $("input[name='userid']").val();
		if(userid == null || userid.length == 0) {
			alert("아이디를 입력하세요");
			userid.focus();
			return;
		}
		
		if(idCheck == false) {
			alert("아이디 중복확인을 하세요");
			return;
		}
		
		var email = $("#mid").val() + '@' + $("#host").val();
		var regExp = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/;
		if( !regExp.test(email) ) {
			alert("고유한 이메일 형식의 아이디를 사용하세요.");
			return;
		}
		$("input[name='email'").val(email);
		
		var password = $("input[name='password']").val();
		if(password == null || password.length == 0) {
			alert("암호를 입력하세요");
			password.focus();
			return;
		}
		
		if(passwordValid == false) {
			alert("특수문자, 영문, 숫자의 조합으로 8자 이상 15자 이하를 사용하세요.");
			return;
		}
		
		var password2 = $("input[name='pwd2']").val();
		if(password2 == null || password2.length == 0) {
			alert("암호 확인을 입력하세요");
			password2.focus();
			return;
		}
		
		if(password2Valid == false) {
			alert("특수문자, 영문, 숫자의 조합으로 8자 이상 15자 이하를 사용하세요.");
			return;
		}
		
		if(password != password2) {
			alert("암호 확인을 입력하세요");
			password2.focus();
			return;
		}
		
		var name = $("input[name='name']").val();
		if(name == null || name.length == 0) {
			alert("이름을 입력하세요");
			name.focus();
			return;
		}

		console.log("submit clicked");
		formObj.submit();
	});
});