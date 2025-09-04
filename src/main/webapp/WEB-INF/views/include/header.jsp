<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 부트스트랩 CSS -->

<!-- 부트스트랩 JavaScript와 Popper.js --> 
<div class="p-5 bg-primary text-white text-center"> 
  <h1>길동이네 집에 오신걸을 환영합니다</h1>
  <p>(본사이트는 반응형으로 제작되었습니다.)</p>
	<div id="carouselExampleControlsNoTouching" class="carousel slide" data-bs-touch="false">
	  <div class="carousel-inner">
	    <div class="carousel-item active">
	      <img src="${pageContext.request.contextPath}/images/1-1.jpg" class="d-block  mx-auto" style="height: 600px; width: 80%;" alt="...">
	    </div>
	    <div class="carousel-item">
	      <img src="${pageContext.request.contextPath}/images/1-2.jpg" class="d-block  mx-auto" style="height: 600px; width: 80%;" alt="...">
	    </div>
	    <div class="carousel-item">
	      <img src="${pageContext.request.contextPath}/images/2.jpg" class="d-block  mx-auto" style="height: 600px; width: 80%;" alt="...">
	    </div>
	  </div>
	  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControlsNoTouching" data-bs-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="visually-hidden">Previous</span>
	  </button>
	  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControlsNoTouching" data-bs-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="visually-hidden">Next</span>
	  </button>
	</div>
</div>