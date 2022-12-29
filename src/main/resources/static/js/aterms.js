$(function() {
	$("#addBtn").on("click", function(e) {
		$("#newTerms").css("display", "block");
	});
	
	$("#usage").on("click", function(e) {
		$("#mandatory").prop("disabled", false);
	});
	
	$("#no_usage").on("click", function(e) {
		$("#mandatory").attr("disabled", "disabled");
	});
	
	$(".listArea #usage").on("click", function(e) {
		this.form[5].disabled = false;
	});
	
	$(".listArea #no_usage").on("click", function(e) {
		this.form[5].disabled = true;
	});
	
	$(".listArea input[type='submit']").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		var $formTag = $(this).parent().parent();
		if(operation == "remove") {
			if(confirm("약관을 삭제하겠습니까?")) {
				var $tidTag = $formTag.children().eq(0).clone();
				$formTag.empty();
				$formTag.append($tidTag);
				$formTag.attr("action", "remove");
			} else {
				return;	
			}
		}
		$formTag.submit();
		console.log("수정/삭제 요청");
	});
	
	$("#cancelBtn").on("click", function(e) {
		if(confirm("설정을 취소하시겠습니까?")) {
			location.href = "/aindex";
		}
	});
});