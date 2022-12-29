$(function() {
	$("#allagree-check input").change(function() {	// 모두 동의 버튼의 변경에 따른 처리
		var value = this.checked;
		$("input[name='agreement']").each(function(e) {	// 영향받는 버튼을 변경
			$(this).prop("checked", value);
		});
	});
	
	var agreeForm = $("#agreeForm");
	$(".btnA[type=submit]").on("click", function(e) {	// 다음 버튼을 누른 경우 처리
		e.preventDefault();

		var $checkBtns = $("input[name='agreement']");
		for(var i = 0;i < $checkBtns.length;i++) {		// 각 약관에 대하여
			var $btn = $checkBtns.eq(i);
			var $articleTag = $btn.parent().parent().parent();
			if($btn.data("oper") == true && $btn.prop("checked") == false) {	// 필수 미동의 시
				alert($articleTag.attr("title") + "에 동의해 주세요!");
				return;
			}
			// 동의(선택)이 여러 개일 경우 수정 필요
			if($btn.data("oper") == false) {	// 선택사항일 경우 선택 여부를 서버로 전송
				agreeForm.append("<input type='hidden' name='agreement'" + 
					" value='" + $btn.prop('checked') +  "'>");
			}
		}
		agreeForm.submit();
	});
	
	$(".btnA[type=button]").on("click", function() {
		var reply = confirm("회원 가입을 취소하시겠습니까?");
		if(reply) {
			location.href = "/index";
		}
	});
});