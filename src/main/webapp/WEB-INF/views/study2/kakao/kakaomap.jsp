<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>kakaomap.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>카카오 Map 지도 연습</h2>
  <div id="map" style="width:100%;height:500px;"></div>
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
  <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f451b201a16dee4dd7330f9ef1e771c3"></script>
	<script>
	var container = document.getElementById('map');
	var options = {
		center: new kakao.maps.LatLng(36.635007192648985, 127.45951222020699),
		level: 3
	};
	var map = new kakao.maps.Map(container, options);
	</script>
	<hr/>
	<jsp:include page="kakaoMenu.jsp" />
</div>
	
</body>
</html>