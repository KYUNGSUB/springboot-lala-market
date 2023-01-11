$(function() {
	$("input[name='display']:radio").change(function(e) {
		if($("input[name='display']:checked").val() == "3") {	// 관리자 선택 택함
			$("#mdArea table").css("display", "table");
		} else {
			$("#mdArea table").css("display", "none");
		}
	})
	
	$(".sequence").on("click", function(e) {
		var $trTag = $(this).parent();
		var oper = $(this).data("oper");
		var position = $(this).data("position");
		console.log(oper + " 버튼 눌림 : " + position);
	});
});