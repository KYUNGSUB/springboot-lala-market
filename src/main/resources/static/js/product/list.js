$(function() {
	var category;
	var csrfHeaderName = window.localStorage.getItem("csrfHeaderName");
	var csrfTokenValue = window.localStorage.getItem("csrfTokenValue");
	
	$.ajax({	// 카테고리 정보를 가져와 category 변수에 저장
		type: "POST",
		url: "/category/show",
		// CSRF 토큰 값을 헤더로 전송
		beforeSend: function(xhr) {
		    xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		},
		success: function(data) {
			// 서버로부터 노출 여부 정보를 수신하여 화면에 표시한다.
			const obj = JSON.parse(data);	// JSON 문자열을 객체로 변환
			category = obj.data;
			makeCategoryField();	// 상세검색 부분의 category1을 생성
		}
	});
	
	var makeCategoryField = function() {	// option 태그에 1차 카테고리 추가
		var select1Tag = $("select[name='category1']");
		for(var i = 0;i < category.length;i++) {
			var str = "<option value='" + category[i].name + 
				"' data-oper='" + i + "'>" + category[i].name
				+ "</option>";	// 카테고리 인덱스를 data-oper 속성에 추가
			select1Tag.append(str);
		}
		// 디폴트로 처음에는 2차 카테고리를 선택할 수 없게 한다.(1차 카테고리가 선택되지 않았으므로)
		$("select[name='category2']").attr("disabled", "disabled");
	}
	
	// 1차 카테고리에 따라 그에 대한 2차 카테고리를 생성
	$("select[name='category1']").change(function(e) {
		var sTag = $("select[name='category1']")[0];
		var i = sTag.selectedIndex - 1;
		var $select2Tag = $("select[name='category2']");
		if(i == -1) {
			$select2Tag.attr("disabled", "disabled");
			return;
		}
		$select2Tag.prop("disabled", false);
		$select2Tag.empty();	// 기존 2차 카테고리를 삭제
		$select2Tag.append("<option value=''>2차 선택</option>");
		var list2 = category[i].list;
		for(var j = 0;j < list2.length;j++) {
			var str = "<option value='" + list2[j].name + "'>" + list2[j].name + "</option>";
			$select2Tag.append(str);
		}
	});
	
	// 상세검색 버튼을 클릭하면, 상세검색 입력 부분을 보이게 한다.
	$("#showBtn").on("click", function(e) {
		e.preventDefault();
		$("#searchArea table").css("display", "block");
		$("#closeBtn").css("display", "inline")
	});
	
	// 닫기 버튼을 클릭하면, 상세검색 입력부분을 감춘다.
	$("#closeBtn").on("click", function(e) {
		e.preventDefault();
		$("#searchArea table").css("display", "none");
		$("#closeBtn").css("display", "none")
	});
	
	// 검색 버튼을 누르면 유효성 검사를 한 후 서버로 검색 요청을 한다.
	var searchForm = $("form[name='search']");
	$("input[type='submit']").on("click", function(e) {
		e.preventDefault();
		// 유효성 검사
		if($("select[name='type']").val() == "none") {		// 검색 유형
			alert("검색 유형을 선택하세요.");
			$("select[name='type']").focus();
			return;
		}
		if($("input[name='keyword']").val().length == 0) {	// 검색어
			alert("검색어를 입력하세요.");
			$("input[name='keyword']").focus();
			return;
		}
		if($("#searchArea table").css("display") == "block") {	// 상세 검색을 요청하면
			if($("select[name='category1']").val() == "") {	// 1차 카테고리
				alert("1차 카테고리를 선택하세요.");
				$("select[name='category1']").focus();
				return;
			}
			if($("select[name='category2']").val() == "") {	// 2차 카테고리
				alert("2차 카테고리를 선택하세요.");
				$("select[name='category2']").focus();
				return;
			}
			if($("input[name='priceFrom']").val().length == 0) {	// 판매 가격 하한값
				alert("판매가격의 하한값을 입력하세요.");
				$("input[name='priceFrom']").focus();
				return;
			}
			if($("input[name='priceTo']").val().length == 0) {		// 판매 가격 상한값
				alert("판매가격의 상한값을 입력하세요.");
				$("input[name='priceTo']").focus();
				return;
			}
			if($("input[name='regFrom']").val().length == 0) {		// 상품 등록일 하한값
				alert("상품 등록일 하한값을 입력하세요.");
				$("input[name='regFrom']").focus();
				return;
			}
			if($("input[name='regTo']").val().length == 0) {		// 상품 등록일 상한값
				alert("상품 등록일 상한값을 입력하세요.");
				$("input[name='regTo']").focus();
				return;
			}
			if($("input:checkbox[name='exposeArr']").is(":checked") == false) {
				alert("진열 여부를 선택하세요.");
				$("input:checkbox[name='exposeArr']").focus();
				return;
			}
			searchForm.append("<input type='hidden' name='detail' value='yes'>");
		} else {
			searchForm.find("select[name='category1']").remove();
			searchForm.find("input[name='priceFrom']").remove();
			searchForm.find("input[name='priceTo']").remove();
			searchForm.find("input[name='regFrom']").remove();
			searchForm.find("input[name='regTo']").remove();
			searchForm.append("<input type='hidden' name='detail' value='no'>");
		}
		searchForm.submit();
	});

	// 진열 버튼을 누르면, 체크박스로 선택된 상품의 노출여부를 진열로 변경 
	$("input[name='show']").on("click", function(e) {
		e.preventDefault();
		var status = $(this).val();
		$(".boxBtn:checked").each(function(e) {
			updateExposeStatus($(this), status);
		});
	});
	
	// 숨김 버튼을 누르면, 체크박스로 선택된 상품의 노출여부를 숨김으로 변경 
	$("input[name='hide']").on("click", function(e) {
		e.preventDefault();
		var status = $(this).val();
		$(".boxBtn:checked").each(function(e) {
			updateExposeStatus($(this), status);
		});
	});
	
	// 품절 버튼을 누르면, 체크박스로 선택된 상품의 노출여부를 품절로 변경 
	$("input[name='out']").on("click", function(e) {
		e.preventDefault();
		var status = $(this).val();
		$(".boxBtn:checked").each(function(e) {
			updateExposeStatus($(this), status);
		});
	});
	
	// 서버로 Ajax를 통해 상품에 대한 노출 여부를 변경
	var updateExposeStatus = function(cbBtn, status) {
		var $pidTag = cbBtn.parent().next();
		var $exposeTag = $pidTag.next().next().next();
		if($exposeTag.text() == status) {
			alert("이미 상태가 " + status + "입니다.");
			return;
		}
		if(confirm("선택한 상품을 " + status + "(으)로 변경하시겠습니까?")) {
			var query = { pid: $pidTag.text(), expose: status };
			$.ajax({
				type: "POST",
				url: "/product/updateExpose",
				data: query,
				success: function(data) {
					if(data == "success") {
						alert("변경되었습니다.");
						$exposeTag.text(status);
					}
				}
			});
		}
	}
	
	// 삭제 버튼이 눌리면, 체크박스로 선택된 상품에 대하여 삭제한다.
	$("input[name='remove']").on("click", function(e) {
		e.preventDefault();
		$(".boxBtn:checked").each(function(e) {
			var pid = $(this).parent().next().text();
			var $trTag = $(this).parent().parent();
			if(confirm("선택한 상품을 삭제 하시겠습니까?")) {
				var query = { pid: pid };		// 상품 아이디
				$.ajax({
					type: "POST",
					url: "/product/delete",	// 상품 삭제
					data: query,
					success: function(data) {
						if(data == "success") {
							alert("삭제되었습니다.");
							$trTag.remove();
						}
					}
				});
			}
		});
	});
});