<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
  		폴더선택 
  		<select name="part" id="part" onchange="partChange()">
  			<option ${part=="ckeditor" ? "selected" : ""} value="ckeditor">ckeditor</option>
  			<option ${part=="fileUpload" ? "selected" : ""} value="fileUpload">fileUpload</option>
  		</select>
  	</div>
  </div>
  <div id="downLoadFile">
    <h3>서버에 저장된 파일정보(총 : ${fileCount}건)</h3>
    <div class="row mb-2">
      <div class="col">
        저장경로 : ${ctp}/resources/data/fileUpload/*.*
      </div>
      <div class="col">
        <input type="button" value="전체삭제" onclick="fileAllDelete()" class="btn btn-danger" />
      </div>
    </div>
    <table class="table table-hover text-center">
      <tr class="table-secondary">
        <th>번호</th>
        <th>파일명</th>
        <th>파일형식</th>
        <th>비고</th>
      </tr>
      <c:forEach var="file" items="${files}" varStatus="st">
        <tr>
          <td>${st.count}</td>
          <td>${file}</td>
          <td>  <!-- 'jpg/gif/png/zip/hwp/doc/ppt/pptx/pdf/txt' -->
          	<c:set var="fNameArray" value="${fn:split(file,'.')}"/>
          	<c:set var="extName" value="${fn:toLowerCase(fNameArray[fn:length(fNameArray)-1])}"/>
          	<c:if test="${extName == 'zip'}">압축파일</c:if>
          	<c:if test="${extName == 'hwp'}">한글문서파일</c:if>
          	<c:if test="${extName == 'doc'}">Word파일</c:if>
          	<c:if test="${extName == 'ppt' || extName == 'pptx'}">파워포인트파일</c:if>
          	<c:if test="${extName == 'pdf'}">pdf문서파일</c:if>
          	<c:if test="${extName == 'txt'}">텍스트문서파일</c:if>
          	<c:if test="${extName == 'jpg' || extName == 'gif' || extName == 'png'}">
          	  <img src='${ctp}/${part}/${file}' width="150px"/>
          	</c:if>
          </td>
          <td>
            <input type="button" value="삭제" onclick="fileDelete('${file}')" class="btn btn-danger btn-sm"/>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
  
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>