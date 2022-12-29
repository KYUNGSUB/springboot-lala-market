var oEditors = [];
$(function() {
	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "dpc",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "/smarteditor/SmartEditor2Skin_ko_KR.html",  
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
          oEditors.getById["dpc"].exec("PASTE_HTML", [""]);
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "dmobile",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "/smarteditor/SmartEditor2Skin_ko_KR.html",  
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
          oEditors.getById["dmobile"].exec("PASTE_HTML", [""]);
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "epc",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "/smarteditor/SmartEditor2Skin_ko_KR.html",  
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
          oEditors.getById["epc"].exec("PASTE_HTML", [""]);
      },
      fCreator: "createSEditor2"
   });

	nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "emobile",
      //SmartEditor2Skin.html 파일이 존재하는 경로
      sSkinURI: "/smarteditor/SmartEditor2Skin_ko_KR.html",  
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
          oEditors.getById["emobile"].exec("PASTE_HTML", [""]);
      },
      fCreator: "createSEditor2"
   });

	// 탭을 제어하는 부분
	$(".tablinks1").on("click", function(e) {
		e.preventDefault();
		var cityName = $(this).data("oper");
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
		$("#"+ cityName).css("display", "block");
		e.currentTarget.className += " active";
		$("#"+ cityName + " iframe").css("height", "260px");
	});
	
	$(".tablinks2").on("click", function(e) {
		e.preventDefault();
		var cityName = $(this).data("oper");
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
		$("#"+ cityName).css("display", "block");
		e.currentTarget.className += " active";
		$("#"+ cityName + " iframe").css("height", "260px");
	});
	$(".tablinks1").eq(0).click();
	$(".tablinks2").eq(0).click();
//	$(".tablinks1").eq(1).click();
//	$(".tablinks2").eq(1).click();
	
	// 우편번호 찾기 연동 (카카오 API)
	$("input[name='search']").on("click", function(e) {
		new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
				// 참고 : https://postcode.map.daum.net/guide
                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }
				$("#address").text("[" + data.zonecode + "] " + roadAddr);	// extraRoadAddr 생략
            }
        }).open();
	});

	// 추가 버튼을 누른 경우 배송 불가 지역에 추가
	$("input[name='add']").on("click", function(e) {
		var value = $('#address').text();
		$("#addressArea").prepend("<span><input type='checkbox' name='area'><input type='hidden' name='post' value='" + value + "'>&nbsp;" + value + "</span>");
		$('#address').text("주소가 표시됩니다");
	});

	// 삭제 버튼을 누른 경우, checkbox를 누른 주소에 대하여 삭제를 한다.
	$("input[name='delete']").on("click", function(e) {
		e.preventDefault();
		$(":checked").each(function() {
			$(this).parent().remove();
		});
	});
	
	// 저장과 취소 버튼에 대한 처리
	var mainForm = $("form");
	$("input[type='submit']").on("click", function(e) {
		e.preventDefault();
		var operation = $(this).data("oper");
		if(operation == 'store') {	// 저장
			//id가 smarteditor인 textarea에 에디터에서 대입
    		oEditors.getById["dpc"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["dmobile"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["epc"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["emobile"].exec("UPDATE_CONTENTS_FIELD", []);
			mainForm.submit();
		} else {					// 취소
			if(confirm("설정을 취소하시겠습니까?")) {
				location.href = "/aindex";
			}
		}
	});
});