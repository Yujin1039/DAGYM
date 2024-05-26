<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�����</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>ȸ�����(������)</h2>
		<%-- �˻� --%>
		<form id="search_form" action="adminList.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1"><c:if test="${param.keyfield==1}">selected</c:if>���̵�</option>
						<option value="2"><c:if test="${param.keyfield==2}">selected</c:if>���</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="�˻�">
				</li>
			</ul>
		</form>
		<%-- ��� --%>
		<div class="list-span align-right">
			<input type="button" value="���" onclick="location.href='adminList.do'">
		</div>
		<hr size="1" width="100%" noshade="noshade">
		<c:if test="${count == 0}">
			<div class="result-display">
				ǥ���� ȸ�������� �����ϴ�
			</div>
		</c:if>
		<c:if test="${count > 0 }">
		<table>
			<tr>
				<th>���̵�</th>
				<th>�̸�</th>
				<th>��ȭ��ȣ</th>
				<th>�������</th>
				<th>������</th>
				<th>���</th>
			</tr>
			<c:forEach var="member" items="${list}">
			<c:if test="${member.mem_auth <= 2}">
			<tr>
				<td><a href="adminUserForm.do?mem_num=${member.mem_num}">${member.mem_id}</a></td>
				<td>${member.mem_name}</td>
				<td>${member.mem_phone}</td>
				<td>${member.mem_birth}</td>
				<td>${member.mem_reg_date}</td>
				<td>
					<c:if test="${member.mem_auth == 0}">Ż��</c:if>
					<c:if test="${member.mem_auth == 1}">����</c:if>
					<c:if test="${member.mem_auth == 2}">�Ϲ�</c:if>
				</td>
			</tr>
			</c:if>
			</c:forEach>
		</table>
		<hr size="1" width="%" noshade="noshade">
		<div class="align-center">${page}</div>
		</c:if>
	</div>
</div>
</body>
</html>