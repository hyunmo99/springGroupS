<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>sweetAlert.jsp</title>
  <script>
  	'use strict';
  	function ex1() {
  		Swal.fire("안녕하세요");
  	}
  	/* et ex2 = () => {
  		Swal.fire("안녕하세요2");
  	} */
  	let ex2 = () => Swal.fire("안녕하세요2")
  	
  	function ex3_1() {
  		Swal.fire({
  			title: "이곳은 제목입니다.",
  			text : "이곳은 본문 내용입니다",
  			icon : "success"
  		});
  	}
  	
  	//Jquery 영역안에서 수행
  	$(function() {
  		$("#ex3_2").click(function(){
	 			Swal.fire({
	 	  			title: "이곳은 제목입니다.",
	 	  			text : "이곳은 본문 내용입니다",
	 	  			icon : "success"
	 	  		});			
  		});
  		$("#ex3_3").click(function(){
  			Swal.fire("3.이곳은 제목입니다.", "이곳은 본문 내용입니다.", 'success');
  		});
  		$("#ex4").click(function(){
  			Swal.fire("4.이곳은 제목입니다.", "이곳은 본문 내용입니다.", 'error');
  		});
  		$("#ex5").click(function(){
  			Swal.fire("5.이곳은 제목입니다.", "이곳은 본문 내용입니다.", 'info');
  		});
  		$("#ex6").click(function(){
  			Swal.fire("6.이곳은 제목입니다.", "이곳은 본문 내용입니다.", 'warning');
  		});
  		$("#ex7").click(function(){
  			Swal.fire("7.이곳은 제목입니다.", "이곳은 본문 내용입니다.", 'question');
  		});
  		// '확인/취소 메세지'
  		$("#confirm1").click(() => {
  			Swal.fire ({
  				title:'이곳은 제목입니다.',
  				text :'내용이 보인느 곳입니다.',
  				icon:'info',	
  				
  				showCancelButton: true
  			});	
  		});
  		$("#confirm2").click(() => {
  			Swal.fire ({
  				title:'이곳은 제목입니다.',
  				text :'내용이 보인느 곳입니다.',
  				icon:'info',	
  				
  				showCancelButton	: true,
  				confirmButtonText	:'승인',
  				cancelButtonText	:'취소'
  				
  			});	
  		});
  		$("#confirm3").click(() => {
  			Swal.fire ({
  				title:'이곳은 제목입니다.',
  				text :'내용이 보인느 곳입니다.',
  				icon:'info',	
  				
  				showCancelButton	: true,
  				confirmButtonText	:'승인',
  				cancelButtonText	:'취소',
  				confirmButtonColor : "#55e",
  				cancelButtonColor	:'#e55'
  			});	
  		});
  		$("#confirm4").click(() => {
  			Swal.fire ({
  				title:'이곳은 제목입니다.',
  				text :'내용이 보인느 곳입니다.',
  				icon:'info',	
  				
  				showCancelButton	: true,
  				confirmButtonText	:'승인',
  				cancelButtonText	:'취소',
  				confirmButtonColor : "#55e",
  				cancelButtonColor	:'#e55'
  			}).then((res) => {
  				if(res.isConfirmed) {
  					Swal.fire("승인되었습니다.", "", "success");
  					//location.href="https://www.naver.com";
  				}
  			});
  		});
  		$("#confirm5").click(() => {
  			Swal.fire ({
  				title:'이곳은 제목입니다.',
  				text :'내용이 보인느 곳입니다.',
  				icon:'info',	
  				
  				showCancelButton	: true,
  				confirmButtonText	:'승인',
  				cancelButtonText	:'취소',
  				confirmButtonColor : "#55e",
  				cancelButtonColor	:'#e55'
  			}).then((res) => {
  				if(res.isConfirmed) Swal.fire("승인되었습니다.", "", "success");
  					//location.href="https://www.naver.com";
  				//else Swal.fire("추쇠되었습니다.", "", "error");
  				else if(res.isDenied) Swal.fire("취소되었습니다.", "", "error");
  				
  			});
  		});
  		$("#confirm6").click(() => {
  			Swal.fire ({
  				title:'이곳은 제목입니다.',
  				text :'내용이 보인느 곳입니다.',
  				icon:'info',	
  				// Yes버튼은 기본값으로 생략
  				showDenyButton	: true,	// 거부(아니요)
  				showCancelButton	: true,
  				confirmButtonText	:'승인',
  				denyButtonText	:'거부',
  				cancelButtonText	:'취소',
  				confirmButtonColor : "#55e",
  				denyButtonColor	:'#5e5',
  				cancelButtonColor	:'#e55',
  			}).then((res) => {
  				if(res.isConfirmed) Swal.fire("승인되었습니다.", "", "success");
  					//location.href="https://www.naver.com";
  				//else Swal.fire("추쇠되었습니다.", "", "error");
  				else if(res.isDenied) Swal.fire("거부되었습니다.", "", "info");
  				else Swal.fire("취소되었습니다.", "", "error");
  			});
  		});
  			
  		//input button
  		$("#prompt1").click(() => {
  			(async () => {
  				const {value: getName} = await Swal.fire({
  					title : '이름을 입력하세요', 
  					text : '이름을 한글로 입력하세요',
  					input : 'text',
  					inputPlaceholder: '이름을 입력해 주세요'
  				});
  				if(getName) {
  					Swal.fire('성명 : ' + getName, '승인되었습니다.', 'success');
  				}
  			})()
  		}); 
  		$("#image1").click(() => {
  			Swal.fire({
  				title : '1. 그림연습', 
  				imageUrl : 'https://media.istockphoto.com/id/1467199060/ko/%EC%82%AC%EC%A7%84/%EC%98%A4%EB%9E%98%EB%90%9C-%EB%82%98%EB%AC%B4-%EB%B0%B0%EA%B2%BD%EC%97%90-%EC%97%B0%EA%B8%B0%EC%99%80-%EC%BB%A4%ED%94%BC-%EC%BD%A9%EC%9D%84-%EA%B3%81%EB%93%A4%EC%9D%B8-%EC%BB%A4%ED%94%BC-%ED%95%9C-%EC%9E%94.jpg?s=1024x1024&w=is&k=20&c=EeRbmBIvZtEYtwvK_SDYyWuf3_JIoG6GmhHCNy7RC0Q=',
					imageAlt : '자연을 항하여~~~'  						
  			});
  		});	
  		$("#image2").click(() => {
  			Swal.fire({
  				title : '2. 그림연습', 
  				imageUrl : 'https://media.istockphoto.com/id/1467199060/ko/%EC%82%AC%EC%A7%84/%EC%98%A4%EB%9E%98%EB%90%9C-%EB%82%98%EB%AC%B4-%EB%B0%B0%EA%B2%BD%EC%97%90-%EC%97%B0%EA%B8%B0%EC%99%80-%EC%BB%A4%ED%94%BC-%EC%BD%A9%EC%9D%84-%EA%B3%81%EB%93%A4%EC%9D%B8-%EC%BB%A4%ED%94%BC-%ED%95%9C-%EC%9E%94.jpg?s=1024x1024&w=is&k=20&c=EeRbmBIvZtEYtwvK_SDYyWuf3_JIoG6GmhHCNy7RC0Q=',
  				imageWidth : 400,
  				imageHeight :250,
					imageAlt : '자연을 항하여~~~'  						
  			});
  		});	
  		$("#image3").click(() => {
  			Swal.fire({
  				title : '3. 그림연습', 
  				imageUrl : '${ctp}/images/2.jpg',
  				imageWidth 	: 400,
  				imageHeight : 250,
					imageAlt : '자연을 항하여~~~'  						
  			});
  		});	
  		$("#html1").click(() => {
  			Swal.fire({
  				title : '1.HTML 연습', 
  				icon : 'success',
  				html : `
  					Welcome to <b>한국</b>!!! <br/>
  					<a href="https://www.daum.net" class="btn btn-success btn-sm" target="_blank">다음</a> 방문하기
  				`
  			});
  		});	
  	});
  	function ajax1(idx) {
  		idx = idx + 100;
  		alert("이곳은 ajax실행전 입니다." + idx);
  		$.ajax({
  			url : 'ajaxSweet',
  			type : 'post',
  			data : {idx : idx},
  			success: (res) => {
  				if(res!= 0) swal.fire("결과 : " + res, '성공적으로 다녀왔습니다.', 'success');
  				else swal.fire("결과 : " + res, '실패~~', 'error');
  			},
  			error: () => alert("전송오류")
  		});
  	}
  	
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>SweetAlert연습</h2>
  <hr/>
  <div>일반 alert : <button type="button" onclick="alert('안녕하세요.');" class="btn btn-secondary">기본 Alert</button></div>
  <hr class="border-1 border-dark">
  <div class="mb-2">
  	<button type="button" onclick="ex1()" class="btn btn-success">ex1</button>
  	<button type="button" onclick="ex2()" class="btn btn-primary">ex2</button>
  	<button type="button" onclick="ex3_1()" class="btn btn-secondary">success1</button>
  </div>
  <div class="mb-2">
  	<button type="button" id="ex3_2" class="btn btn-secondary">success2</button>
  	<button type="button" id="ex3_3"class="btn btn-secondary">success3</button>
  	<button type="button" id="ex4"class="btn btn-danger">error</button>
  	<button type="button" id="ex5"class="btn btn-info">information</button>
  	<button type="button" id="ex6"class="btn btn-warning">warning</button>
  	<button type="button" id="ex7"class="btn btn-primary">question</button>
 	</div>
 	<hr class="border-1 border-dark">
 	<div>
  	<button type="button" id="confirm1"class="btn btn-outline-primary">confirm1</button>
  	<button type="button" id="confirm2"class="btn btn-outline-success">confirm2</button>
  	<button type="button" id="confirm3"class="btn btn-outline-info">confirm3</button>
  	<button type="button" id="confirm4"class="btn btn-outline-warning">confirm4</button>
  	<button type="button" id="confirm5"class="btn btn-outline-danger">confirm5</button>
  	<button type="button" id="confirm6"class="btn btn-outline-decondary">confirm6</button>
 	</div>
 	<hr class="border-1 border-dark">
 	<div>
  	<button type="button" id="prompt1"class="btn btn-outline-danger">prompt1</button>
  	<button type="button" id="image1"class="btn btn-outline-primary">image1</button>
  	<button type="button" id="image2"class="btn btn-outline-info">image2</button>
  	<button type="button" id="image3"class="btn btn-outline-warning">image3</button>
  	<button type="button" id="html1"class="btn btn-outline-danger">html1</button>
  	<button type="button" onclick="ajax1(100)"class="btn btn-outline-danger">ajax1</button>
 	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>