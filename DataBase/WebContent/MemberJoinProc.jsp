<%@page import="model.MemberDAO"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		
		<%
		
		request.setCharacterEncoding("UTF-8");
		
		/* 취미 부분은 별로도 읽어드려 다시 빈 클래스에 저장해야함 */
		String[] hobby = request.getParameterValues("hobby");
		
		String texthobby = "";
		for(int i = 0; i < hobby.length; i++){
			texthobby += hobby[i] + " ";
		}
		
		%>
		
		<jsp:useBean id="mbean" class="model.MemberBean">
			<jsp:setProperty name="mbean" property="*" />
		</jsp:useBean>
		
		
		<%
		mbean.setHobby(texthobby);//기존 취미는 주소 번지가 저장되기에 for문으로 돌린 texthobby을 넣어야함.
		
		MemberDAO mdao = new MemberDAO();
		mdao.insertMember(mbean);
		
		//회원가입이 되었다면 회원정보를 보여주는 MemberList.jsp로 이동 시킴
		response.sendRedirect("MemberList.jsp");
		%>
		
		
		
	</body>
</html>