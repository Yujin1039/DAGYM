<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시판 글 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript">
window.onload=function(){
	const myForm = document.getElementById('update_form');
	//이벤트 연결
	myForm.onsubmit=function(){
		const nbo_title = document.getElementById('nbo_title');
		if(nbo_title.value.trim()==''){
			alert('제목을 입력하세요');
			nbo_title.value = '';
			nbo_title.focus();
			return false;
		}
		const content = document.getElementById('nbo_content');
		if(nbo_content.value.trim()==''){
			alert('내용을 입력하세요');
			nbo_content.value = '';
			nbo_content.focus();
			return false;
		}
	};
};
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>공지게시판 글 수정</h2>
		<form id="update_form" action="nboardUpdate.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="nbo_num" value="${nboard.nbo_num}">
			<ul>
				<li>
					<label for="nbo_title">제목</label>
					<input type="text" name="nbo_title" id="nbo_title" value="${nboard.nbo_title}" maxlength="50">
				</li>
				<li>
					<label for="nbo_content">내용</label>
					<textarea rows="5" cols="40" name="nbo_content" id="nbo_content">${nboard.nbo_content}</textarea>
				</li>
				<li>
					<label for="nbo_filename">이미지</label>
					<input type="file" name="nbo_filename" id="nbo_filename" accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty nboard.nbo_filename}">
					<div id="file_detail">
					<img src="${pageContext.request.contextPath}/upload/${nboard.nbo_filename}" width="100">
					<br>
					<input type="button" value="파일 삭제" id="file_del">
					</div>
					<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
					<script type="text/javascript">
						$(function(){
							$('#file_del').click(function(){
								let choice = confirm('삭제하시겠습니까?');
								if(choice){
									//서버와 통신
									$.ajax({
										url:'deleteFile.do',
										type:'post',
										data:{nbo_num:${nboard.nbo_num}},
										dataType:'json',
										success:function(param){
											if(param.result == 'wrongAccess'){
												alert('잘못된 접속입니다.');
											}else if(param.result=='success'){
												$('#file_detail').hide();
											}else{
												alert('파일 삭제 오류 발생');
											}
										},
										error:function(){
											alert('네트워크 오류 발생');
										}
									});
								}
							});
						});
					</script>
					</c:if>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="공지글 수정">
				<input type="button" value="목록" onclick="location.href='nboardList.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>