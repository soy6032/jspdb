<%@page import="model.MemberBean"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		
		<!--
		1. 데이터베이스에서 한명의 회원 정보를 가져온다.
		2. 테이블 태그를 이용하여 화면에 회원의 정보를 출력
		  -->
		  
		<%
		String id = request.getParameter("id");
		
		MemberDAO mdao = new MemberDAO();
		MemberBean mbean = mdao.oneSelectMember(id);
		  
		%>
		<h3>회원정보보기</h3>
		<table border="1">
			<tr height="50" >
				<td align="center"width="150">아이디</td>
				<td width="250"><%=mbean.getId() %></td>
			</tr>
			<tr height="50">
				<td align="center"width="150">이메일</td>
				<td width="250"><%=mbean.getEmail() %></td>
			</tr>
			<tr height="50">
				<td align="center"width="150">전화번호</td>
				<td width="250"><%=mbean.getTel() %></td>
			</tr>
			<tr height="50">
				<td align="center"width="150">취미</td>
				<td width="250"><%=mbean.getHobby() %></td>
			</tr>
			<tr height="50">
				<td align="center"width="150">직업</td>
				<td width="250"><%=mbean.getJob() %></td>
			</tr>
			<tr height="50">
				<td align="center"width="150">나이</td>
				<td width="250"><%=mbean.getAge() %></td>
			</tr>
			<tr height="50">
				<td align="center"width="150">하고싶은말</td>
				<td width="250"><%=mbean.getInfo() %></td>
			</tr>
			<tr height="50">
				<td align="center" colspan="2">
					<button onclick="location.href='MemberUpdateFrom.jsp?id=<%=mbean.getId()%>'"> 회원수정 </button>
					<button onclick="location.href='MemberDeleteFrom.jsp?id=<%=mbean.getId()%>'"> 회원삭제 </button>
					<button onclick="location.href='MemberList.jsp'"> 목록보기 </button>
					<button onclick="location.href='MemberJoin.jsp'"> 회원가입 </button>
				</td>
			</tr>
		</table>
		
	</body>
</html>