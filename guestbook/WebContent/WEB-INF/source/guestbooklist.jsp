<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GuestBook</title>

<!-- 1 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 2 -->
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<!-- 외부 스크립트 파일 연결 -->
<script src="${pageContext.request.contextPath}/script/util.js"></script>

<!-- 3 -->
<script src="http://maps.googleapis.com/maps/api/js"></script>

<!-- 4 -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<!-- 5 -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

<script>

//페이징 처리를 위한 변수 추가
var totalPages; //전체 페이지수 저장용 전역변수
var currentPageNum; //현재 페이지 번호 저장용 전역변수
var pagePerCount = ${pagePerCount == null?10:pagePerCount}; //한 페이지당 게시물 수

$(document).ready(function() {
	
	//툴팁 설정
	$('[data-toggle="tooltip"]').tooltip();
	
	//페이징 처리를 위한 초기화
	pageInit();
	
	//남은 날짜 표시
	$("#day").text("(D-" + dday("2017-03-28") + ")");
	
	//구글 맵 버튼에 대한 이벤트 등록
	$(".btnMap").click(function() {
		//모달 창 호출 액션
		$("#mapModal").modal();
	});
	//모달 창이 화면에 나타나면 myMap() 함수 호출
	$("#mapModal").on('shown.bs.modal', function() {
		myMap();
	});
	//모달 창이 화면에서 사라질때 기존의 영역에 출력된 지도 정보 삭제
	$("#mapModal").on('hidden.bs.modal', function() {
		$("#googleMap").empty();
	});
	
	//검색 수행시 키워드 표시하는 과정 추가
	$("#skey").find("option[value='${skey}']").attr("selected", "seleted");
	$("#svalue").val("${svalue}");
	$("#skey").on("change", (function() {
		$("#svalue").val("");
	}));

	//삭제 진행을 위한 버튼 click 이벤트 추가
	//->hidden form에 gid 지정
	//->모달 창 호출
	$("button.delete").on("click",function() {
		$("#gid2").val(	$(this).parents("tr").children().eq(0).text());
		
		//현재 페이지 번호, 페이지당 게시물 수를 hidden form에 저장
		$("#deleteFormModal input.page").val("${page}");
		$("#deleteFormModal input.pagePerCount").val("${pagePerCount}");
		
		$("#deleteFormModal").modal();
	});

	//<tr> 태그에 class 식별자 추가한 후 click 이벤트 
	$("tr.trlist").on("click", function() {
		$(this).siblings().css("background-color", "white");
		$(this).css("background-color", "#F5F5F5");
	});
	
	
	//비밀글 보기 버튼 이벤트 등록
	$(".userOption").click(function() {
		$("#userOptionModal form input[type='hidden']").val($(this).val());
		$("#userOptionModal").modal();
		//console.log($("#userOptionModal #pw").val());
	});
	
	//비밀글 보기에서 패스워드 전송 및 응답 처리 과정 추가
	$(".btnUserOption").click(function() {
		
		//모달창 닫기
		$("#userOptionModal").modal("hide");

		//Ajax 요청 및 응답 처리
		//전송 -> 글번호 및 패스워드, POST 방식 전송
		//응답 -> "글내용" -> 정상적인 경우는 버튼이 있는 영역에 전달받은 글내용을 덮어쓰기.
		//응답 -> "" -> 실패한 경우 버튼 옆에 에러 메시지 출력.
		
		//입력 폼의 데이터 읽기
		var gid = $(this).parents("form").find(":hidden").val();
		var pw = $(this).parents("form").find("#pw").val();
		
		//패스워드가 입력된 경우만 Ajax 요청
		if (pw!="") {
			
			//Ajax 요청. POST 방식. bid, pw 전송.
			$.post("guestbookajax.it", {gid:gid, pw:pw}, function(data) {
				
				//응답 결과 분석 및 결과 출력
				//JSP 페이지 Directive trimDirectiveWhitespaces="true" 설정에 따라서 공백문자 포함 여부가 달라짐.
				console.log("["+data+"]");
				
				//응답 결과 문자열에서 공백문자 제거후 검사
				if ( $.trim(data) == "" ) {
					//패스워드가 틀린 경우 에러 메시지 출력
					$(".userOption[value='"+gid+"']").parents("td").find(".err").html(" 패스워드가 틀렸습니다.");
				} else {
					//패스워드가 맞는 경우 콘텐츠 내용 출력
					$(".userOption[value='"+gid+"']").parents("td").html(data);
				}
			});
			
		}
		
	});
	
	
	//페이징 처리를 위한 버튼 이벤트 등록
	$(".btnPrevious").click(function() {
		var previousNum = parseInt(currentPageNum)-1;
		//특정 페이지 요청 액션
		$(location).attr("href", "guestbooklist.it?pagePerCount="+pagePerCount+"&page="+previousNum);
		currentPage(previousNum);
	});
	$(".btnNext").click(function() {
		var nextNum = parseInt(currentPageNum)+1;
		$(location).attr("href", "guestbooklist.it?pagePerCount="+pagePerCount+"&page="+nextNum);
		currentPage(nextNum);
	});
	

}); //ready 이벤트


//페이징 처리를 위한 함수 지정
function pageInit() {
	totalPages = Math.ceil(parseInt("${totalcount==null?0:totalcount}")/pagePerCount);
	$(".totalPages").text(totalPages);
	currentPageNum = "${page==null?1:page}";
	currentPage(currentPageNum);

	//검색 출력인 경우 페이지 액션 관련 버튼 비활성 처리
	if ($("#svalue").val().trim() != "") {
		$(".btnTotalPages").attr("disabled", true);
		$(".btnCurrentPage").attr("disabled", true);
		$(".btnPrevious").attr("disabled", true);
		$(".btnNext").attr("disabled", true);
	}
}

//페이징 처리를 위한 함수 지정
function currentPage(idx) {
	$(".currentPage").text(idx);
	currentPageNum = idx;
	
	//이전, 다음 버튼에 대한 활성, 비활성 처리
	//이전 버튼은 현재 페이지가 1인 경우 비활성 처리
	//다음 버튼은 현재 페이지가 전체 페이지수와 일치하는 경우 비활성 처리
	//그외 나머지 경우는 이전, 다음 버튼 활성 처리
	$(".btnPrevious").attr("disabled", false);
	$(".btnNext").attr("disabled", false);
	if (parseInt(idx) == 1) {
		$(".btnPrevious").attr("disabled", true);
	} else if (parseInt(idx) == totalPages) {
		$(".btnNext").attr("disabled", true);
	}
	
}

</script>


<script>
	//한독약품빌딩
	//서울특별시 강남구 역삼1동 735
	//37.499362, 127.033202
	var position01 = new google.maps.LatLng(37.500197, 127.033471);
	var position02 = new google.maps.LatLng(37.499362, 127.033202);

	function myMap() {

		//구글 맵 정보 설정
		var mapProp = {
			center : position01,
			zoom : 17,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("googleMap"),
				mapProp);

		//마커 정보 설정
		var marker = new google.maps.Marker({
			position : position02,
		});
		marker.setMap(map);

		//윈포윈도우 설정
		var infowindow = new google.maps.InfoWindow(
				{
					content : "한독약품빌딩<br>서울특별시 강남구 역삼1동 735<br><img src=\"${pageContext.request.contextPath}/img/handok_small.png\">"
				});
		google.maps.event.addListener(marker, 'click', function() {
			infowindow.open(map, marker);
		});

	}
</script>

</head>
<body>

	<div class="container">

		<div>
			<h1 style="font-size: x-large;">
				<a href="guestbooklist.it"><img src="${pageContext.request.contextPath}/img/sist_logo.png" alt="logo"
					style="vertical-align: bottom;"></a> 방명록 <small>응용SW개발자
					과정 2016.09.05 ~ 2017.03.28 <span style="color: red;" id="day">(D-0)</span>
				</small>
		</h1>
		</div>

		<div class="panel-group">
			<div class="panel panel-default">
				<div class="panel-heading">
					서울특별시 강남구 역삼1동 735 한독약품빌딩 8층 쌍용교육센터 2강의실, 지하철 2호선 역삼역 3번 출구
					<button type="button" class="btn btn-default btn-xs btnPictureList"
						data-toggle="modal" data-target="#pictureListModal">
						<%-- pictureList <span class="badge">3</span> --%>
						pictureList <span class="badge">${pCount}</span>
					</button>
					<button type="button" class="btn btn-default btn-xs btnMap"
						id="map">Map</button>
					<button type="button" class="btn btn-default btn-xs" id="admin" data-toggle="modal" data-target="#loginFormModal">Admin</button>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">방명록 글쓰기</div>
				<div class="panel-body" id="demo">

					<!-- action="" 속성의 요청주소를 서블릿 주소로 변경 -->
					<form role="form" action="guestbookinsert.it" method="post">
						<div class="form-group">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="NAME (20자 이내)" maxlength="20" required="required">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" id="pw" name="pw"
								placeholder="PASSWORD (20자 이내)" maxlength="20"
								required="required">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="content"
								name="content" placeholder="CONTENT (500자 이내)" maxlength="500"
								required="required">
						</div>
						<button type="submit" class="btn btn-default">Submit</button>
						
						<%-- 비밀글 옵션 추가 --%>
						<label class="checkbox-inline"  data-toggle="tooltip" data-placement="right" title="비밀글 설정시 본인 및 관리자만 볼 수 있습니다."><input type="checkbox" value="1" id="userOption" name="userOption">비밀글</label>
						
					</form>

				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">방명록 글목록</div>
				<div class="panel-body">
					<table class="table" id="boardTable">
						<thead>
							<tr>
								<th>번호</th>
								<th>글쓴이</th>
								<th>글내용</th>
								<th>작성일</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody>

							<%--											
							<tr  class="trlist">
								<td>1</td>
								<td>관리자</td>
								<td>JSP 과정 진행 중입니다. 프로젝트 발표 사진도 올릴 예정입니다.</td>
								<td>2017-01-17</td>
								<td><button type="button" class="btn btn-default btn-xs delete ">삭제</button></td>
							</tr>
							 --%>
							<c:forEach var="g" items="${list}">
							<tr  class="trlist">
								<td>${g.gid}</td>
								<td>${g.name}</td>
								<td>

								<%-- 비밀글 옵션 추가 --%>
								<c:choose>
								<c:when test="${g.userOption==1}"><button type="button" class="btn btn-default btn-xs userOption" data-toggle="tooltip" data-placement="right" title="글쓴이 확인 후 내용보기가 가능합니다." value="${g.gid}">비밀글 보기</button><span class="err"></span></c:when>
								<c:otherwise>${g.content}</c:otherwise>
								</c:choose>
								
								</td>
								<td>${g.writeday}</td>
								<td><button type="button" class="btn btn-default btn-xs delete ">삭제</button></td>
							</tr>
							</c:forEach>

						</tbody>
					</table>

					<!-- action="" 속성이 없는 경우 자기 자신에게 전송된다. -->
					<form method="post" class="form-inline">

						<div class="form-group">
							<button type="button" class="btn btn-default btn-sm">
								<%-- TotalCount <span class="badge" id="totalCount">1</span>  --%>
								TotalCount <span class="badge" id="totalCount">${totalcount}</span>
							</button>
						</div>


						<div class="form-group">
							<button type="button" class="btn btn-default btn-sm">
								<%-- Count <span class="badge" id="count">1</span>  --%>
								Count <span class="badge" id="count">${count}</span>
							</button>
						</div>
						
						<!-- 페이징 처리를 위한 버튼 추가 -->
						<button type="button" class="btn btn-default btn-sm btnTotalPages">
							Pages <span class="badge totalPages">0</span>
						</button>
						<button type="button"
							class="btn btn-default btn-sm btnCurrentPage">
							Page <span class="badge currentPage">0</span>
						</button>

						<button type="button" class="btn btn-default btn-sm btnPrevious"
							value="">
							<span class="glyphicon glyphicon-chevron-left"></span> Previous
						</button>
						<button type="button" class="btn btn-default btn-sm btnNext"
							value="">
							Next <span class="glyphicon glyphicon-chevron-right"></span>
						</button>
						

						<div class="form-group">
							<select class="form-control" name="skey" id="skey">
								<option value="all">전체</option>
								<option value="name">글쓴이</option>
								<option value="content">글내용</option>
							</select>
						</div>

						<div class="form-group">
							<input type="text" class="form-control" id="svalue" name="svalue">
						</div>

						<button type="submit" class="btn btn-default">Search</button>

					</form>

				</div>
			</div>
		</div>

	</div>

	<!-- pictuerList Modal -->
	<div id="pictureListModal" class="modal fade" role="dialog">
		<!-- 모달 창의 크기 조절을 원하면 class="modal-lg" 속성 추가. 기본값은 중간 크기입니다. -->
		<div class="modal-dialog ">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Picture List</h4>
				</div>
				<div class="modal-body">

					<div id="myCarousel" class="carousel slide" data-ride="carousel">

						<!-- 이미지 출력 액션 코드 작성. JSTL, EL 표현 사용 -->
						<!-- Indicators -->
						<!-- data-slide-to="0" 속성을 사진 갯수만큼 지정한다. -->
						<!-- class="active" 속성은 첫 번째로 보여질 사진에 지정한다. -->
						<ol class="carousel-indicators">
						<!-- 
							<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
							<li data-target="#myCarousel" data-slide-to="1"></li>
							<li data-target="#myCarousel" data-slide-to="2"></li>
						 -->	
						<c:forEach var="p" items="${plist}" varStatus="pcount">
							<li data-target="#myCarousel" data-slide-to="${pcount.index}"  ${(pcount.index==0)?"class=\"active\"":""}></li>
						</c:forEach>
						 
						</ol>

						<!-- Wrapper for slides -->
						<div class="carousel-inner" role="listbox">

							<!-- 이미지 출력 액션 코드 작성. JSTL, EL 표현 사용 -->
							<!-- 사진 한 장 당 <div><img></div> 태그 구성 -->
							<!-- 이미지 파일 이름은 영숫자(소문자)로만 표기된 파일 이름 권장 -->
							<!-- class="active" 속성은 첫 번째로 보여질 사진에 지정 -->
							<!-- 
							<div class="item active">
								<img src="${pageContext.request.contextPath}/picture/chicago.jpg" alt="chicago.jpg">
							</div>

							<div class="item">
								<img src="${pageContext.request.contextPath}/picture/la.jpg" alt="la.jpg">
							</div>

							<div class="item">
								<img src="${pageContext.request.contextPath}/picture/ny.jpg" alt="ny.jpg">
							</div>
							 -->
							<c:forEach var="p" items="${plist}" varStatus="pcount">
							<div  ${(pcount.index==0)?"class=\"item active\"":"class=\"item\""}>
								<img src="${pageContext.request.contextPath}/picture/${p.guestBookPicFileName}" alt="${p.guestBookPicFileName}">
							</div>
							</c:forEach>
							
						</div>

						<!-- Left and right controls -->
						<a class="left carousel-control" href="#myCarousel" role="button"
							data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control" href="#myCarousel"
							role="button" data-slide="next"> <span
							class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
					<p>Note: Carousels are not supported properly in Internet
						Explorer 9 and earlier.</p>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>

	<!-- Google Map Modal -->
	<div id="mapModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Google Map</h4>
				</div>
				<div class="modal-body">

					<div id="googleMap"
						style="width: 550px; height: 380px; margin: auto;"></div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
	</div>


	<!-- Delete Form Modal -->
	<div id="deleteFormModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">게시물 삭제</h4>
				</div>
				<div class="modal-body">

					<!-- 삭제 진행시 번호와 패스워드를 서버로 전송해야 한다. -->
					<form action="guestbookdelete.it" method="post">

						<!-- 현재 페이지 번호, 한 페이지당 게시물 수를 서버로 전송하기 위한 준비. jQuery. -->
						<!-- 번호, 현재 페이지 번호, 한 페이지당 게시물 수 전송은 hidden form 사용 -->
						<%-- hidden form 추가 --%>
						<input type="hidden" id="gid2" name="gid" value="">
						<input type="hidden" class="page" name="page" value="">
						<input type="hidden" class="pagePerCount" name="pagePerCount" value="">

						<!-- 패스워드는 입력 폼 사용 -->
						<div class="form-group">
							<input type="password" placeholder="PASSWORD (20자 이내)"
								class="form-control" id="pw2" name="pw" required maxlength="20">
						</div>
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

	<!-- Login Form Modal -->
	<div id="loginFormModal" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Admin Login</h4>
				</div>
				<div class="modal-body">

					<!-- 로그인 진행시 아이디와 패스워드를 서버로 전송해야 한다. -->
					<!-- action="" 속성에 Spring Security 전용 주소 지정 -->
					<form action="j_spring_security_check" method="post">

						<!--아이디는 입력 폼 사용 -->
						<!-- name="" 속성에 Spring Security 전용 식별자 지정 -->
						<div class="form-group">
							<input type="text" placeholder="ID (20자 이내)" class="form-control"
								id="id" name="j_username"  required maxlength="20">
						</div>

						<!-- 패스워드는 입력 폼 사용 -->
						<!-- name="" 속성에 Spring Security 전용 식별자 지정 -->
						<div class="form-group">
							<input type="password" placeholder="PASSWORD (20자 이내)"
								class="form-control" id="pw" name="j_password" required maxlength="20">
						</div>

						<button type="submit" class="btn btn-default">로그인</button>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default btn-sm"
						data-dismiss="modal">Close</button>
				</div>
			</div>

		</div>
			
		
		
	</div>
	
	
	<!-- 비밀글 패스워드 확인 Modal -->
	<div id="userOptionModal" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">글쓴이 확인</h4>
					</div>
					<div class="modal-body">

						<form role="form">
							<input type="hidden" id="gid" name="gid" value="">
							<div class="form-group">
								<input type="password"
									class="form-control" id="pw" name="pw"
									placeholder="PASSWORD (20자 이내)" required="required">
							</div>
							<%-- Ajax 요청 처리를 위해서 submit 버튼이 아닌 일반 버튼으로 작성한다. --%>
							<button type="button" class="btn btn-default btnUserOption">
								확인</button>
						</form>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>	

</body>
</html>


