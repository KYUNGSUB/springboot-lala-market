$(function() {
	var csrfHeaderName = window.localStorage.getItem("csrfHeaderName");
	var csrfTokenValue = window.localStorage.getItem("csrfTokenValue");
	
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
		$(".modal").toggleClass("show");
		if($(".modal").hasClass("show") === true) {
			$("body").css("overflow", "hidden");
		}
	});
	
	$(".modal").on("click", function(e) {
		if(e.target === $(".modal")[0]) {
			$(".modal").toggleClass("show");
			if($(".modal").hasClass("show") === false) {
				$("body").css("overflow", "auto");
			}
		}
	});
	
	$("#searchBtn").on("click", function(e) {
		var category = $("select[name='c2_code']").val()
		if(category == '') {
			alert("카테고리를 선택해 주세요.");
			return;
		}
		var name = $("input[name='name']").val();
		if(name == null || name.length == 0) {
			alert("상품명을 입력하세요.");
			return;
		}
		
		var query = { "category1" : "0020000", "category2" : category };
		$.ajax({
			type: "GET",
			url: "/product/slist",
			data: query,
			// CSRF 토큰 값을 헤더로 전송
			beforeSend: function(xhr) {
		          xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		    },
			success: function(data) {
				console.log(data);
			}
		});
	});
});