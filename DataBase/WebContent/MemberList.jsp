<%@page import="model.MemberBean"%>
<%@page import="java.util.Vector"%>
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
		1. 데이터베이스에서 모든 회원의 정보를 가져온다.
		2. 테이블 태그를 이용하여 화면에 회원들의 정보를 출력
		  -->
		  
		<%
		
		MemberDAO mdao = new MemberDAO();
		Vector<MemberBean> vec = mdao.allSelectMember();	
		  
		%>
		<h3>모든회원정보보기</h3>
		<table width="800" border="1">
			<tr height="50">
				<td align="center" width="150">아이디</td>
				<td align="center" width="250">이메일</td>
				<td align="center" width="200">전화번호</td>
				<td align="center" width="200">취미</td>
			</tr>
			<%
				for(int i = 0; i < vec.size(); i++){
					MemberBean bean = vec.get(i);//벡터에 담긴 빈 클래스를 하나씩 추출
			%>
			<tr height="50">
				<td align="center" width="150">
					<a href="MemberInfo.jsp?id=<%=bean.getId()%>"><%=bean.getId() %></a>
				</td>
				<td align="center" width="250"><%=bean.getEmail() %></td>
				<td align="center" width="200"><%=bean.getTel() %></td>
				<td align="center" width="200"><%=bean.getHobby() %></td>
			</tr>
			<%
				}
			%>
		</table>
		
	</body>
</html>