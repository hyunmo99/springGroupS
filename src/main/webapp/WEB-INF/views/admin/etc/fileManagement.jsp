<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>fileManagement.jsp</title>
  
  <script>
  	'use strict';
  	function partChange() {
  		let part = $("#part").val();
  		location.href = "fileManagement?part="+part+"";
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>임 시 파 일 관 리</h2>
  
  <div class="row">
  	<div class="col">
  		폴더선택 ${part}
  		<select name="part" id="part" onchange="partChange()">
  			<option ${part=="ckeditor" ? "selected" : ""} value="ckeditor">ckeditor</option>
  			<option ${part=="fileUpload" ? "selected" : ""} value="fileUpload">fileUpload</option>
  		</select>
  	</div>
  </div>
  
  
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>