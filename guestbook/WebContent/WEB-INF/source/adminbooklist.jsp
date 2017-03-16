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
				<a href="adminbooklist.it" class="btn btn-default active">방명록관리</a>
				<a href="adminpicturelist.it" class="btn btn-default">사진목록관리</a>
				
				<%-- Spring Security에 지정한 로그아웃 요청 주소 지정 --%>
				<a href="j_spring_security_logout" class="btn btn-default">로그아웃</a>
				
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">방명록</div>
				<div class="panel-body">
					<table class="table" id="boardTable">
						<thead>
							<tr>
								<th>번호</th>
								<th>글쓴이</th>
								<th>글내용</th>
								<th>작성일</th>
								<th>IP</th>
								<th>블라인드</th>
							</tr>
						</thead>
						<tbody>
							<!-- 
							<tr>
								<td>2</td>
								<td>홍길동</td>
								<td><span style="color:red;font-size:small;">[비밀글]</span>질문있습니다.</td>
								<td>2017-01-17</td>
								<td>211.63.89.68</td>
								<td><div class="btn-group">
									<a href="adminblind.it?gid=1&blind=1" class="btn btn-default btn-xs " role="button">On</a>
									<a href="adminblind.it?gid=1&blind=0" class="btn btn-default btn-xs active" role="button">Off</a>
									</div></td>
							</tr>
							<tr>
								<td>1</td>
								<td>관리자</td>
								<td>JSP 과정 진행 중입니다. 프로젝트 발표 사진도 올릴 예정입니다.</td>
								<td>2017-01-17</td>
								<td>211.63.89.68</td>
								<td><div class="btn-group">
									<a href="adminblind.it?gid=1&blind=1" class="btn btn-default btn-xs " role="button">On</a>
									<a href="adminblind.it?gid=1&blind=0" class="btn btn-default btn-xs  active" role="button">Off</a>
									</div></td>
							</tr>
							 -->
							<%-- 관리자용 방명록 출력 과정 작성. JSTL, EL 표현 사용. --%>
							<c:forEach var="g" items="${list}"> 
							 <tr>
								<td ${g.blind==1?"style=\"text-decoration: line-through\"":""}>${g.gid}</td>
								<td ${g.blind==1?"style=\"text-decoration: line-through\"":""}>${g.name}</td>
								<td ${g.blind==1?"style=\"text-decoration: line-through\"":""}>${g.userOption==1?"<span style=\"color:red;font-size:small;\">[비밀글]</span>":""} ${g.content}</td>
								<td ${g.blind==1?"style=\"text-decoration: line-through\"":""}>${g.writeday}</td>
								<td ${g.blind==1?"style=\"text-decoration: line-through\"":""}>${g.ipaddress}</td>
								<td>
								<div class="btn-group">
									<a href="adminblind.it?gid=${g.gid}&blind=1" class="btn btn-default btn-xs  ${g.blind==1?'active':''}" role="button">On</a>
									<a href="adminblind.it?gid=${g.gid}&blind=0" class="btn btn-default btn-xs  ${g.blind==0?'active':''}" role="button">Off</a>
								</div>
								</td>
							</tr>
							</c:forEach>
							
						</tbody>
					</table>

					<!-- action="" 속성이 없는 경우 자기 자신에게 전송된다. -->
					<form method="post" class="form-inline">

						<div class="form-group">
							<button type="button" class="btn btn-default btn-sm">
								TotalCount <span class="badge" id="totalCount">${totalcount}</span>
							</button>
						</div>


						<div class="form-group">
							<button type="button" class="btn btn-default btn-sm">
								Count <span class="badge" id="count">${count}</span>
							</button>
						</div>

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


</body>
</html>


