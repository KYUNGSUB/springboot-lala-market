$(document).ready(function() {
	var passCode = false;
	var csrfHeaderName = window.localStorage.getItem("csrfHeaderName");
	var csrfTokenValue = window.localStorage.getItem("csrfTokenValue");
		
	// 1차 카테고리 생성 버튼에 대한 처리 : 토글로 추가화면 표시
	$("#firstBtn").on("click", function(e) {
		var display = $("#firstId").css("display");
		if(display == 'block') {
			$("#firstId").css("display", "none");
		} else {
			// 메뉴 버튼 제어
			$("#firstId input[data-oper='add']").css("display", "inline");
			$("#firstId input[data-oper='modify']").css("display", "none");
			$("#firstId input[data-oper='remove']").css("display", "none");
			$("#firstId").css("display", "block");
		}
	});
	// 1차 카테고리 제어 : 추가, 수정, 취소, 삭제 버튼 클릭시 동작
	var firstForm = $("#firstId");
	$("#firstId input[type='submit']").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		console.log(operation);
		if(operation == 'add') {  // 카테고리 추가 메뉴 선택 시
			// 파라미터 유효성 검사
			var name = $("#firstId input[name='name']").val();
			var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
			if(name.length == 0) {
				alert("카테고리명을 입력하세요.");
				$("#firstId input[name='name']").focus();
				return;
			}
			if(regExp.test(name)) {
				alert("특수문자를 사용할 수 없습니다.");
				$("#firstId input[name='name']").focus();
				return;
			}
			if($("#firstId input[name='code']").val() == 0) {
				alert("카테고리 코드를 입력하세요.");
				$("#firstId input[name='code']").focus();
				return;
			}
			if(passCode == false) {
				alert("카테고리 코드 중복 확인을 해 주세요.");
				$("#firstId input[name='code']").focus();
				return;
			}
			firstForm.submit();
		} else if(operation == 'modify') {	// 수정 시
			// 카테고리 코드를 refresh 한다.
			var code = $("#firstId input[name='code']").val();
			firstForm.find("input[name='code']").remove();
			firstForm.append("<input type='text' name='code' value='" + code + "'>");
			firstForm.attr("action", "modify");	// 서버로 변경 요청을 전송
			firstForm.submit();
		} else if(operation == 'cancel') {	// 취소 시
			if(confirm("설정을 취소하시겠습니까?")) {		// 취소를 확인
				location.href = "/aindex";		// 관리자 메인 페이지로 이동
			} else {								// 취소를 취소
				$("#firstId").css("display", "none");// 카테고리 수정 부분을 감춘다.->현재 페이지 유지
			}
		} else if(operation == 'remove') {	// 삭제 시
			var code = $("#firstId input[name='code']").val();
			firstForm.find("input[name='code']").remove();
			firstForm.append("<input type='text' name='code' value='" + code + "'>");
			var codeTag = $("#firstId input[name='code']").clone();
			firstForm.empty();
			firstForm.append(codeTag);
			firstForm.attr("action", "remove");
			firstForm.submit();
		}
	});
	// 2차 카테고리 코드 중복 확인
	$("#duplicateBtn").on("click", function(e) {
		var codeTag = $("#firstId input[name='code']");
		if(codeTag.val().length == 0) {
			alert("카테고리 코드를 입력해 주세요.");
			codeTag.focus();
			return false;
		}
		var query = { code: codeTag.val() };
		$.ajax({
			type: "POST",
			url: "/category/check",
			data: query,
			// CSRF 토큰 값을 헤더로 전송
			beforeSend: function(xhr) {
		          xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		    },
			success: function(data) {
				if(data == "using") {	// 존재하는 카테고리 코드
					alert("이미 존재하는 카테고리 코드 입니다.");
					passCode = false;
				} else {
					alert("사용 가능한 카테고리 코드 입니다.");
					passCode = true;
				}
			}
		});
	});
	
	$(".secondBtn").on("click", function(e) {
		var display = $("#secondId").css("display");
		if(display == 'block') {
			$("#secondId").css("display", "none");
		} else {
			$("#secondId").css("display", "block");
			var liTag = this.parentElement;
			$("#fName").text(liTag.dataset.name);
			var parent = liTag.dataset.code;
			$("#fCode").text(parent);
			$("#secondId input[name='parent']").val(parent);
			$("#secondId input[data-oper='modify']").css("display", "none");
			$("#secondId input[data-oper='remove']").css("display", "none");
			
			var query = { code: parent };
			$.ajax({
				type: "POST",
				url: "/category/getSeed",
				data: query,
				// CSRF 토큰 값을 헤더로 전송
				beforeSend: function(xhr) {
		        	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		    	},
				success: function(data) {
					var secondCode = parent.slice(0,3) + makeSecondCategory(parseInt(data)) + "0";
					$("#secondId input[name='code']").val(secondCode);
					console.log(secondCode);
					console.log(data);
				}
			});
		}
	});
	
	function makeSecondCategory(index) {
		var code = "";
		var seed = 100;
		for(var i = 0;i < 3;i++) {
			code = code + parseInt(index / seed);
			seed = seed / 10;
		}
		return code;
	}

	// 추가, 수정, 취소, 삭제 버튼의 클릭을 감지하여 동작한다.
	var secondForm = $("#secondId");
	$("#secondId input[type='submit']").on("click", function(e) {
		e.preventDefault();		// 기본 이벤트 동작을 막고
		var operation = $(this).data("oper");	// 눌린 버튼의 정보를 가져온다.
		console.log(operation);
		if(operation == 'add') {			// 추가 버튼이 눌린 경우
			secondForm.append("<input type='hidden' name='gnb' value='yes'>");
			secondForm.attr("action", "add");
			secondForm.submit();
		} else if(operation == 'modify') {	// 수정 버튼이 눌린 경우
			// 지정된 카테고리 코드 값을 refresh 한다.
			var code = $("#secondId input[name='code']").val();
			secondForm.find("input[name='code']").remove();
			secondForm.append("<input type='text' name='code' value='" + code + "'>");
			secondForm.attr("action", "modify");
			secondForm.submit();	// 서버로 수정 요청을 한다.
		} else if(operation == 'cancel') {	// 취소 버튼이 눌린 경우
			if(confirm("설정을 취소하시겠습니까?")) {	// 취소를 확인
				location.href = "/aindex";	// 관리자 메인 페이지로 이동
			} else {							// 취소를 취소
				$("#secondId").css("display", "none");	// 현재 페이지에서 카테고리 정보 입력 부분을 감춘다.
			}
		} else if(operation == 'remove') {	// 삭제 버튼이 눌린 경우
			var codeTag = $("#secondId input[name='code']").clone();
			secondForm.empty();
			secondForm.append(codeTag);
			secondForm.attr("action", "remove");
			secondForm.submit();
		}
	});
	
	$(".expand").on("click", function(e) {
		e.preventDefault();
		var showFlag = this.innerText;
		// 현재 버튼을 기준으로 <ul>태그를 찾아간다.
		var ulTag = this.parentElement.nextElementSibling.nextElementSibling;
		if(showFlag == '-') {		// 감추기
			ulTag.style.display = "none";
			this.innerText = '+';
		} else {					// 보여주기
			ulTag.style.display = "block";
			this.innerText = '-';
		}
	});
	
	var moveForm = $("form[name='move']");
	$(".upArrow.1st").on("click", function(e) {	// 1차 카테고리 위로 이동
		var liTag = this.parentElement.parentElement;
		console.log(liTag.dataset.index);
		if(liTag.dataset.index == 1) {	// no action
			return;
		}
		$("input[name='step']").val(1);	// step
		$("input[name='direction']").val("up");
		moveForm.append("<input type='hidden' name='seq' value='" + (liTag.dataset.index) + "'>");
		moveForm.append("<input type='hidden' name='code' value='" + (liTag.dataset.code) + "'>");
		moveForm.submit();
	});
	
	$(".downArrow.1st").on("click", function(e) {	// 1차 카테고리 아래로 이동
		var liTag = this.parentElement.parentElement;
		console.log(liTag.dataset.index);
		if(liTag.dataset.index == ($("input[name='seq']").val() - 1)) {	// no action
			return;
		}
		$("input[name='step']").val(1);	// step
		$("input[name='direction']").val("down");
		moveForm.append("<input type='hidden' name='seq' value='" + (liTag.dataset.index) + "'>");
		moveForm.append("<input type='hidden' name='code' value='" + (liTag.dataset.code) + "'>");
		moveForm.submit();
	});
	
	$(".upArrow.2nd").on("click", function(e) {	// 2차 카테고리 위로 이동
		var liTag = this.parentElement;
		console.log(liTag.dataset.index);
		if(liTag.dataset.index == 0) {	// no action
			return;
		}
		$("input[name='step']").val(2);	// step
		$("input[name='direction']").val("up");
		moveForm.append("<input type='hidden' name='seq' value='" + (Number(liTag.dataset.index) + 1) + "'>");
		moveForm.append("<input type='hidden' name='code' value='" + (liTag.dataset.code) + "'>");
		moveForm.append("<input type='hidden' name='parent' value='" + (liTag.dataset.parent) + "'>");
		moveForm.submit();
	});
	
	$(".downArrow.2nd").on("click", function(e) {	// 2차 카테고리 아래로 이동
		var liTag = this.parentElement;
		console.log(liTag.dataset.index);
		var topliTag = liTag.parentElement.parentElement;
		if(liTag.dataset.index == (topliTag.dataset.size - 2)) {	// no action
			return;
		}
		$("input[name='step']").val(2);	// step
		$("input[name='direction']").val("down");
		moveForm.append("<input type='hidden' name='seq' value='" + (Number(liTag.dataset.index) + 1) + "'>");
		moveForm.append("<input type='hidden' name='code' value='" + (liTag.dataset.code) + "'>");
		moveForm.append("<input type='hidden' name='parent' value='" + (liTag.dataset.parent) + "'>");
		moveForm.submit();
	});
	
	// 1차 카테고리 이름을 클릭하면 처리한다.
	$(".firstName").on("click", function(e) {
		var display = $("#firstId").css("display");
		if(display == 'block') {
			$("#firstId").css("display", "none");
		} else {
			var liTag = this.parentElement.parentElement;
			$("#firstId input[name='name']").val(liTag.dataset.name);
			$("#firstId input[name='name']").attr("disabled", "disabled");
			$("#firstId input[name='code']").val(liTag.dataset.code);
			$("#firstId input[name='code']").attr("disabled", "disabled");
			$("#firstId input[data-oper='add']").css("display", "none");
			$("#firstId input[data-oper='modify']").css("display", "inline");
			$("#firstId input[data-oper='remove']").css("display", "inline");
			$("#firstId").css("display", "block");
			
			// 표시 창에 카테고리 노출 여부와 GNB 노출 여부 정보를 표시하기 위하여 서버로
			// 정보를 요청(/category/getOption)하여 가져온다.
			var codeTag = $("#firstId input[name='code']");
			var query = { code: codeTag.val() };
			$.ajax({
				type: "POST",
				url: "/category/getOption",
				data: query,
				// CSRF 토큰 값을 헤더로 전송
				beforeSend: function(xhr) {
		        	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		    	},
				success: function(data) {
					// 서버로부터 노출 여부 정보를 수신하여 화면에 표시한다.
					const obj = JSON.parse(data);
					if(obj.expose) {
						$("#fey").attr("checked", "checked");
					} else {
						$("#fen").attr("checked", "checked");
					}
					if(obj.gnb) {
						$("#fgy").attr("checked", "checked");
					} else {
						$("#fgn").attr("checked", "checked");
					}
					console.log(obj.expose);
					console.log(obj.gnb);
				}
			});
		}
	});
	
	$(".secondName").on("click", function(e) {
		var display = $("#secondId").css("display");
		if(display == 'block') {
			$("#secondId").css("display", "none");
		} else {
			var liSecond = this.parentElement.parentElement;
			var liFirst = liSecond.parentElement.parentElement;
			$("#secondId input[name='code']").val(liSecond.dataset.code);
			$("#secondId input[name='name']").val(liSecond.dataset.name);
			$("#secondId input[name='name']").attr("disabled", "disabled");
			$("#fName").text(liFirst.dataset.name);
			$("#fCode").text(liSecond.dataset.parent);
			$("#secondId input[data-oper='add']").css("display", "none");
			$("#secondId input[data-oper='modify']").css("display", "inline");
			$("#secondId input[data-oper='remove']").css("display", "inline");
			$("#secondId").css("display", "block");
			var query = { code: liSecond.dataset.code };
			$.ajax({
				type: "POST",
				url: "/category/getOption",
				data: query,
				// CSRF 토큰 값을 헤더로 전송
				beforeSend: function(xhr) {
		        	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		    	},
				success: function(data) {
					const obj = JSON.parse(data);
					if(obj.expose) {
						$("#sey").attr("checked", "checked");
					} else {
						$("#sen").attr("checked", "checked");
					}
					console.log(obj.expose);
					console.log(obj.gnb);
				}
			});
		}
	});
});