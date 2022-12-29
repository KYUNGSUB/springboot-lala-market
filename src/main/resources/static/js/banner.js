$(function() {
	var newBanner = false;
	var kind;	// GNB
	var position;
	var bIndex;
	
	$("#addBtn").on("click", function(e) {
		$("#new-banner").css("display", "block");
		if($("input[name='location']:checked").val() == "login") {
			$("#loginArea").css("display", "block");
		}
	});
	
	$("input[name='location'").on("change", function(e) {
		if($("input[name='location']:checked").val() == "login") {
			$("#loginArea").css("display", "block");
		} else {
			$("#loginArea").css("display", "none");
		}
	});
	
	$("#new-banner input[name='infoCheck']").change(function() {
		newBanner = this.checked;
		if(newBanner) {
			$("#banner-btn button[data-oper='modify']").text("등록하기");
		}
	});
	
	$(".infoClass input[name='infoCheck']").change(function(e) {
		var oldBanner = this.checked;
		if(oldBanner) {
			$("#banner-btn button[data-oper='modify']").text("수정하기");
		}
	});
	
	kind = $("#kind").val();
	if(kind == "")
		kind = 1;
	position = $("#position").val();
	if(position == "")
		position = 1;
		
	$("aside ul li:nth-child(" + kind + ") a").css("color", "red");
	$("#banner-kind ul li:nth-child(" + position + ")").css("background-color", "#eee");
	
	console.log("kind=" + kind + ", position=" + position);
	
	$("aside li a").on("click", function(e) {
		e.preventDefault();
		kind = $(this).attr("href");
		location.href = "/banner/gnb?kind=" + kind + "&position=" + position;
	});
	
	$("#banner-kind li a").on("click", function(e) {
		e.preventDefault();
		position = $(this).attr("href");
		location.href = "/banner/gnb?kind=" + kind + "&position=" + position;
	});
	
	var addForm = $("#new-banner form");
	$("button[data-oper='modify']").on("click", function() {
		var operation = $("#banner-btn button[data-oper='modify']").text();
		if(newBanner && operation == "등록하기") {	// 배너 추가 선택됨
			addForm.find("#kind").val(kind);
			addForm.find("#position").val(position);
			addForm.find("#infoCheck").remove();
			var location = $("#expose-method input").clone();
			addForm.append(location);
			addForm.attr("action", "gnb");
			addForm.submit();
		} else if(operation == "수정하기") {
			$(".infoClass input[name='infoCheck']").each(function() {
				if(this.checked) {	// checked
					var value = this.value;
					var index = Number(value.substring(3, value.length));
					var modForm = $(".infoClass form").eq(index);
					modForm.find("#kind").val(kind);
					modForm.find("#position").val(position);
					modForm.find("#infoCheck").remove();
					var location = $("#expose-method input").clone();
					modForm.append(location);
					modForm.attr("action", "gnbMod");
					modForm.submit();
				}
			});
		}
	});
	
	$("button[data-oper='remove']").on("click", function(e) {
		$(".infoClass input[name='infoCheck']").each(function() {
			if(this.checked) {	// checked
				var value = this.value;
				var index = Number(value.substring(3, value.length));
				var removeForm = $(".infoClass form").eq(index);
				removeForm.find("#kind").val(kind);
				removeForm.find("#position").val(position);
				var csrf_tag = removeForm.find("#csrf").clone();
				var kind_tag = removeForm.find("#kind").clone();
				var position_tag = removeForm.find("#position").clone();
				var info_id_tag = removeForm.find("#info_id").clone();
				removeForm.empty();
				removeForm.append(csrf_tag);
				removeForm.append(kind_tag);
				removeForm.append(position_tag);
				removeForm.append(info_id_tag);
				removeForm.attr("action", "gnbDel");
				removeForm.submit();
			}
		});
	});
});