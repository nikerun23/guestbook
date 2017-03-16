<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuestBook</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/script/util.js"></script>
<script src="http://maps.googleapis.com/maps/api/js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<script>
	$(document).ready(function() {

		//남은 날짜 표시
		$("#day").text("(D-" + dday("2017-03-28") + ")");

		//사진 삭제 버튼에 대한 click 이벤트 등록
		$("button.delete").on("click",	function() {
			//버튼의 value 속성의 값을 읽어서 hidden form에 지정
			$("#picturedeleteFormModal input#guestBookPicFileName").val($(this).val());
			//사진 comment 정보를 모달창 화면에 출력
			$("#picturedeleteFormModal #comment").text($(this).prev().text());
			//사진 삭제 모달창 호출
			$("#picturedeleteFormModal").modal();
		});

	}); //ready 이벤트
</script>


</head>
<body>

	<div class="container">

		<div>
			<h1 style="font-size: x-large;">
				<img src="${pageContext.request.contextPath}/img/sist_logo.png" alt="logo"
					style="vertical-align: bottom;"> 방명록[관리자] <small>응용SW개발자
					과정 2016.09.05 ~ 2017.03.28 <span style="color: red;" id="day">(D-0)</span>
				</small>
			</h1>
		</div>

		<div class="panel-group">
			<div class="btn-group btn-group-justified" style="margin-bottom: 5px;">
				<a href="adminbooklist.it" class="btn btn-default">방명록관리</a> <a
					href="adminpicturelist.it" class="btn btn-default  active">사진목록관리</a>
				
				<%-- Spring Security에 지정한 로그아웃 요청 주소 지정 --%>
				<a href="j_spring_security_logout" class="btn btn-default">로그아웃</a>
				
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">사진 업로드</div>
				<div class="panel-body" id="demo">

					<form role="form" action="adminpictureinsert.it" method="post" enctype="multipart/form-data" >
						<div class="form-group">
							<input type="text" class="form-control" id="comment" name="comment"
								placeholder="사진 설명 (100자 이내)" maxlength="100" required="required">
						</div>
						<div class="form-group">
							<input type="file" class="form-control" id="fileName"
								name="fileName"  
								required="required">
								<span class="help-block">(only .jpg or .png, 500K byte 이내)</span>
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
					</form>

				</div>
			</div>


			<div class="panel panel-default">
				<div class="panel-heading">사진 목록</div>
				<div class="panel-body">

					<div class="row">
						
						<!-- 
						<div class="col-md-3">
							<div class="thumbnail">
								<a href="${pageContext.request.contextPath}/picture/chicago.jpg" target="_blank">
									<img src="${pageContext.request.contextPath}/picture/chicago.jpg" alt="chicago.jpg"
									style="width: 100%">
								</a>
								<div class="caption">
									<p>sample images 001</p>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="thumbnail">
								<a href="${pageContext.request.contextPath}/picture/la.jpg" target="_blank"> <img
									src="${pageContext.request.contextPath}/picture/la.jpg" alt="la.jpg"
									style="width: 100%">
								</a>
								<div class="caption">
									<p>sample images 002</p>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="thumbnail">
								<a href="${pageContext.request.contextPath}/picture/ny.jpg" target="_blank"> <img
									src="${pageContext.request.contextPath}/picture/ny.jpg" alt="ny.jpg"
									style="width: 100%">
								</a>
								<div class="caption">
									<p>sample images 003</p>
								</div>
							</div>
						</div>
 						-->
 						
 						<%-- 문제) 데이터베이스에서 읽어온 사진 정보 출력하는 과정 작성 --%>
 						<c:forEach var="p" items="${plist}">
						<div class="col-md-3">
							<div class="thumbnail">
								<a href="${pageContext.request.contextPath}/picture/${p.guestBookPicFileName}" target="_blank">
									<img src="${pageContext.request.contextPath}/picture/${p.guestBookPicFileName}" alt="${p.guestBookPicFileName}"
									style="width: 100%">
								</a>
								<div class="caption">
									<p>${p.comment}</p>
									<%-- 
									문제)
									삭제버튼 클릭시 모달창 오픈
									모달창 삭제할까요? 메시지 및 확인, 취소 버튼 준비
									확인 버튼 클릭시 삭제 액션 요청
									요청할 때 사진 이름 정보(guestBookPicFileName)를 같이 전송할 것
									요청 주소는 adminpicturedelete.it
									--%>
									<button type="button" class="btn btn-default btn-xs delete"
											value="${p.guestBookPicFileName}">삭제</button>
								</div>
							</div>
						</div>
						</c:forEach>
 					 						
					</div>

				</div>
			</div>
		</div>

	</div>

	<!-- picturedeleteFormModal -->
	<div id="picturedeleteFormModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">사진 삭제</h4>
				</div>
				<div class="modal-body">

					<p>현재 선택한 사진(<span id="comment"></span>)을 삭제할까요?</p>

					<!-- 삭제 진행시 파일 이름 정보를 서버로 전송해야 한다. -->
					<form action="adminpicturedelete.it" method="post">

						<!-- 파일 이름 정보를 서버로 전송하기 위한 준비. jQuery. -->
						<%-- hidden form 추가 --%>
						<input type="hidden" id="guestBookPicFileName" name="guestBookPicFileName" value="">
						
						<button type="submit" class="btn btn-default">삭제</button>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


</body>
</html>


