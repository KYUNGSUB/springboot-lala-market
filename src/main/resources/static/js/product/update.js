var oEditors = [];
$(function() {
	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "pc_detail",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "../../smarteditor/SmartEditor2Skin_ko_KR.html",  
      htParams : {
          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseToolbar : true,             
          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseVerticalResizer : true,     
          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
          bUseModeChanger : true,         
          fOnBeforeUnload : function(){ }
      }, 
      fOnAppLoad : function(){
          //textarea 내용을 에디터상에 바로 뿌려주고자 할때 사용
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "mobile_detail",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "../../smarteditor/SmartEditor2Skin_ko_KR.html",  
      htParams : {
          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseToolbar : true,             
          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseVerticalResizer : true,     
          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
          bUseModeChanger : true,         
          fOnBeforeUnload : function(){ }
      }, 
      fOnAppLoad : function(){
          //textarea 내용을 에디터상에 바로 뿌려주고자 할때 사용
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "pc_delivery",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "../../smarteditor/SmartEditor2Skin_ko_KR.html",  
      htParams : {
          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseToolbar : true,             
          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseVerticalResizer : true,     
          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
          bUseModeChanger : true,         
          fOnBeforeUnload : function(){ }
      }, 
      fOnAppLoad : function(){
          //textarea 내용을 에디터상에 바로 뿌려주고자 할때 사용
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "mobile_delivery",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "../../smarteditor/SmartEditor2Skin_ko_KR.html",  
      htParams : {
          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseToolbar : true,             
          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseVerticalResizer : true,     
          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
          bUseModeChanger : true,         
          fOnBeforeUnload : function(){ }
      }, 
      fOnAppLoad : function(){
          //textarea 내용을 에디터상에 바로 뿌려주고자 할때 사용
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "pc_exchange",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "../../smarteditor/SmartEditor2Skin_ko_KR.html",  
      htParams : {
          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseToolbar : true,             
          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseVerticalResizer : true,     
          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
          bUseModeChanger : true,         
          fOnBeforeUnload : function(){ }
      }, 
      fOnAppLoad : function(){
          //textarea 내용을 에디터상에 바로 뿌려주고자 할때 사용
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "mobile_exchange",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "../../smarteditor/SmartEditor2Skin_ko_KR.html",  
      htParams : {
          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseToolbar : true,             
          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
          bUseVerticalResizer : true,     
          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
          bUseModeChanger : true,         
          fOnBeforeUnload : function(){ }
      }, 
      fOnAppLoad : function(){
          //textarea 내용을 에디터상에 바로 뿌려주고자 할때 사용
      },
      fCreator: "createSEditor2"
   });

	$("input[name='infoBtn']").change(function(e) {
		var value = $(this).val();
		var $tdTag = $(this).parent().parent().parent().next();
		if(value == 'no') {	// 미사용 선택
			$tdTag.css("display", "none");
		} else {
			$tdTag.css("display", "table-cell");
		}
	});

	$("input[name='infoadd']").on("click", function(e) {
		if($("#infoArea p").length == 4) {
			alert("4개까지 추가할 수 있습니다.");
			return;
		}
		var data = "<p><input type='checkbox' name='iitem'>&nbsp;" +
			"<input type='text' name='iname' placeholder='항목명'>&nbsp;" +
			"<input type='text' name='idescription' placeholder='설명'></p>"; 
		$("#infoArea").append(data);
	});
	
	$("input[name='infodel']").on("click", function(e) {
		$("#infoArea input[type='checkbox']:checked").each(function() {
			var pTag = $(this).parent().remove();
		});
	});
	
	$("input[name='optionBtn']").change(function(e) {
		var value = $(this).val();
		var $tdTag = $(this).parent().parent().parent().next();
		if(value == 'no') {	// 미사용 선택
			$tdTag.css("display", "none");
		} else {
			$tdTag.css("display", "table-cell");
		}
	});
	$("input[name='optadd']").on("click", function(e) {
		if($("#optionArea > div").length == 3) {
			alert("3개까지 추가할 수 있습니다.");
			return;
		}
		var data = "<div><p><input type='checkbox' name='oitem'>" +
					"<input type='text' size='10' name='oname' placeholder='옵션명'>" +
					"<input type='text' size='30' name='odescription' placeholder='설명'>" +
					"<input type='text' size='10' name='oprice' placeholder='가격'>" +
					"<input type='button' name='oaddBtn' value='항목 추가'></p></div>";
		$("#optionArea").append(data);
	});
	
	$("input[name='optdel']").on("click", function(e) {
		$("#optionArea input[type='checkbox']:checked").each(function() {
			var divTag = $(this).parent().parent().remove();
		});
	});
	
	$("#optionArea").on("click", "input[name='oaddBtn']", function(e) {
		var $divTag = $(this).parent().parent();
		var oname = $(this).parent().children()[1].value;
		var data = "<p><span style='display: inline-block; width: 137px;'>&nbsp;</span>" +
					"<input type='hidden' name='oname' value='" + oname + "'>" +
					"<input type='text' size='30' name='odescription' placeholder='설명'>" +
					"<input type='text' size='10' name='oprice' placeholder='가격'>" +
					"<input type='button' name='odelBtn' value='항목 삭제'></p>";
		$divTag.append(data);
	});
	
	$("#optionArea").on("click", "input[name='odelBtn']", function(e) {
		$(this).parent().remove();
	});

	// 탭을 제어하는 부분
	$(".tablinks1").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		// Declare all variables
		var i, tabcontent, tablinks;

		// Get all elements with class="tabcontent" and hide them
		tabcontent = $(".tabcontent1");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}

		// Get all elements with class="tablinks" and remove the class "active"
		tablinks = $(".tablinks1");
		for (i = 0; i < tablinks.length; i++) {
			tablinks[i].className = tablinks[i].className.replace(" active", "");
		}

		// Show the current tab, and add an "active" class to the button that opened the tab
		$("#"+ operation).css("display", "block");
		e.currentTarget.className += " active";
	});

	$(".tablinks2").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		// Declare all variables
		var i, tabcontent, tablinks;

		// Get all elements with class="tabcontent" and hide them
		tabcontent = $(".tabcontent2");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}

		// Get all elements with class="tablinks" and remove the class "active"
		tablinks = $(".tablinks2");
		for (i = 0; i < tablinks.length; i++) {
			tablinks[i].className = tablinks[i].className.replace(" active", "");
		}

		// Show the current tab, and add an "active" class to the button that opened the tab
		$("#"+ operation).css("display", "block");
		e.currentTarget.className += " active";
		$("#"+ operation + " iframe").css("height", "260px");
	});

	$(".tablinks3").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		// Declare all variables
		var i, tabcontent, tablinks;

		// Get all elements with class="tabcontent" and hide them
		tabcontent = $(".tabcontent3");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}

		// Get all elements with class="tablinks" and remove the class "active"
		tablinks = $(".tablinks3");
		for (i = 0; i < tablinks.length; i++) {
			tablinks[i].className = tablinks[i].className.replace(" active", "");
		}

		// Show the current tab, and add an "active" class to the button that opened the tab
		$("#"+ operation).css("display", "block");
		e.currentTarget.className += " active";
		$("#"+ operation + " iframe").css("height", "260px");
	});

	$(".tablinks4").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		// Declare all variables
		var i, tabcontent, tablinks;

		// Get all elements with class="tabcontent" and hide them
		tabcontent = $(".tabcontent4");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}

		// Get all elements with class="tablinks" and remove the class "active"
		tablinks = $(".tablinks4");
		for (i = 0; i < tablinks.length; i++) {
			tablinks[i].className = tablinks[i].className.replace(" active", "");
		}

		// Show the current tab, and add an "active" class to the button that opened the tab
		$("#"+ operation).css("display", "block");
		e.currentTarget.className += " active";
		$("#"+ operation + " iframe").css("height", "260px");
	});
	
	$(".tablinks4").eq(0).click();
	$(".tablinks3").eq(0).click();
	$(".tablinks2").eq(0).click();
	$(".tablinks1").eq(0).click();
	
	var form = $("form");
	$("input[type='submit']").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		if(operation == 'update') {	// 등록
			//id가 smarteditor인 textarea에 에디터에서 대입
    		oEditors.getById["pc_detail"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["mobile_detail"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["pc_delivery"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["mobile_delivery"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["pc_exchange"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["mobile_exchange"].exec("UPDATE_CONTENTS_FIELD", []);
			form.submit();
		} else {
			if(confirm("정말로 취소하시겠습니까?")) {
				location.href = "aindex";
			}
		}
	});
	
	$("input[name='dguide']").change(function(e) {
		if($(this).val() == "common") {	// 공통 배송 안내
			$(".tab3").css("display", "none");
			$(".tabcontent3").css("display", "none");
		} else {	// 개별 배송 안내
			$(".tab3").css("display", "block");
			$(".tabcontent3").css("display", "block");
		}
	});

	$("input[name='exchange']").change(function(e) {
		if($(this).val() == "common") {	// 공통 배송 안내
			$(".tab4").css("display", "none");
			$(".tabcontent4").css("display", "none");
		} else {	// 개별 배송 안내
			$(".tab4").css("display", "block");
			$(".tabcontent4").css("display", "block");
		}
	});

	$("input[name='point']").change(function(e) {
		if($(this).val() == "apart") {	// 적립 포인트 : 별도 포인트 적용
			$("input[name='deposit']").prop("disabled", false);
		} else {
			$("input[name='deposit']").attr("disabled", "disabled");
		}
	});
	
	$("input[name='fee']").change(function(e) {
		if($(this).val() == "apart") {	// 적립 포인트 : 별도 포인트 적용
			$("input[name='delivery']").prop("disabled", false);
		} else {
			$("input[name='delivery']").attr("disabled", "disabled");
		}
	});
	
	$(".delFile").on("click", function(e) {
		e.preventDefault();
		if(confirm("정말로 삭제하시겠습니까?")) {
			var $aTag = $(this);
			var uuid = $aTag.data("uuid");
			var path = $aTag.data("path");
			var filename = $aTag.data("filename");
			var query = { uuid: uuid, path: path, filename: filename };
			$.ajax({
				url: "/pds/delete.do",
				method: "POST",
				data: query,
				success: function(data) {
					if(data == "success") {
						var divTag = $aTag.parent();
						divTag.remove();
					} else {
						alert("이미지 파일 삭제 실패");
					}
				}
			});
		}
	});
});