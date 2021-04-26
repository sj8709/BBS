<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>uploadAjax</title>
</head>

<div class='bigPictureWrapper'>
	<div class='bigPicture'>
	</div>
</div>

<Style>
.uploadResult {
	width:100%;
	background-color:gray;
}

.uploadResult ul {
	display:flex;
	flex-flow:row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding:10px;
	align-context: center;
	text-align: center;
}

.uploadResult ul li img {
	width:100px;
}

.uploadResult ul li span {
	color: white;
}

.bigPictureWapper{
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	height: 100%;
	background-color: grey;
	z-index: 100;
	background: rgba(255,255,255,0.5);
}

.bigPicture{
	position: absolute;
	display: flex;
	justify-content: center;
	align-items: center;
}

.bigPicture img {
	width:600px;
}


</Style>



<body>
	<h1>Upload with Ajax</h1>
	<div class='uploadDiv'>
		<input type='file' name='uploadFile' multiple>
	</div>
	<div class='uploadResult'>
		<ul>
		
		</ul>
	</div>
	
	<button id='uploadBtn'>Upload</button>
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"
		integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
		crossorigin="anonymous"></script>
	<script>
/* 		$(document).ready(function() {
			$("#uploadBtn").on("click", function(e) {
				var formData = new FormData();
				var inputFile = $("input[name='uploadFile']");
				var files = inputFile[0].files;
				console.log(files);
				
				for(var i = 0; i < files.length; i++) {
					formData.append("uploadFile", files[i]);
				}
				
				$.ajax({
					url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData,
					type: 'POST',
					success: function(result){
						alert("Uploaded");
					}
				});
			});
		}); */
		
		// 업로드 된 이미지 클릭시 작동 함수
		function showImage(fileCallPath) {
			$(".bigPictureWrapper").css("display", "flex").show();
			
			
			$(".bigPicture")
			.html("<img src='/display?fileName="+encodeURI(fileCallPath)+"'>")
			.animate({width:'100%', height:'100%'}, 1000);
			}
		
		
		// 확대된 이미지 축소 작동 함수
		$(".bigPictureWrapper").on("click", function(e){
			$(".bigPicture").animate({width:'0%', height:'0%'}, 1000);
			setTimeout(function(){
				$('.bigPictureWrapper').hide();
			}, 1000);
		});
		
		
		// x표시 클릭시 이벤트 처리
		$(".uploadResult").on("click", function(e) {
			var targetFile = $(this).data("file");
			var type = $(this).data("type");
			console.log("targetFile: " + targetFile);
			
			// ajax로 첨부파일의 경로와 이름, 파일의 종류를 전송해 controller단에서 처리
			$.ajax({
				url: '/deleteFile',
				data: {fileName: targetFile, type:type},
				dataType: 'text',
				type: 'POST',
				success: function(result){
					alert(result);
				}
			});
		});
		
		
		
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880; // 5MB
		
		function checkExtension(fileName, fileSize) {
			if(fileSize > maxSize) {
				alert("파일 사이즈 초과");
				return false;
			}
			
			if(regex.test(fileName)) {
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			return true;
		}
		
		// 화면에 업로드 선택된 파일 이름 <li> 처리
		var uploadResult = $(".uploadResult ul");
		
		function showUploadFile(uploadResultArr) {
			var str = "";
			
			$(uploadResultArr).each(function(i, obj) {
				// 이미지 파일이 아닌 다른 파일일 경우
				if(!obj.image) {
					// 파일의 이미지 클릭시 다운로드 가능하게 만들기 위해
					// 다운로드에 필요한 경로, uuid 추가
					// 브라우저에서 공백, 한글 등이 문제가 될 수 있기 때문에 'encodeURIComponent'를 이용해 URI 호출에 적합한 문자열로 인코딩 처리
					var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid + "_"+obj.fileName);
					var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
					str += "<li><div><a href='/download?fileName="+fileCallPath+"'>"+
			           "<img src='/resources/img/attach.png'>"+obj.fileName+"</a>"+
			           "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+
			           "</div></li>"
				// 이미지 파일일 경우
				} else {
					// str += "<li>" + obj.fileName + "</li>";
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_"+obj.uuid+"_"+obj.fileName);
					var originPath = obj.uploadPath+"\\"+obj.uuid+"_"+obj.fileName;
					originPath = originPath.replace(new RegExp(/\\/g), "/");
					str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\">"+
		              "<img src='display?fileName="+fileCallPath+"'></a>"+
		              "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+
		              "<li>";
				}
			});
			
			uploadResult.append(str);
		}
		
		// 첨부파일 업로드 전 빈 <input type='file'> 객체가 포함된 <div>를 복사
		// 첨부파일을 업로드한 뒤에 복사된 객체를 <div> 내에 다시 추가해서 첨부파일을 초기화
		var cloneObj = $(".uploadDiv").clone();
		
		$("#uploadBtn").on("click", function(e) {
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log(files);
			for (var i=0; i<files.length; i++){
				if(!checkExtension(files[i].name, files[i].size)) {
					return false;
				}
				
				formData.append("uploadFile", files[i]);
			}
			
			$.ajax({
				url: '/uploadAjaxAction',
				processData: false,
				contentType: false,
				data: formData,
				type: 'POST',
				dataType: 'json',
				success: function(result){
					console.log(result);
					alert("Uploaded");
					
					showUploadFile(result);
					
					$(".uploadDiv").html(cloneObj.html());
				}
			});
			
		});
	</script>
</body>
</html>