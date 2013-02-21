<%@page import="sun.misc.BASE64Decoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%

	String content = request.getParameter("content");
	String nickname = request.getParameter("nickname");
	if(content != null && nickname != null) {
		nickname = new String(nickname.getBytes("iso-8859-1"), "utf-8");
		content = new String(content.getBytes("iso-8859-1"), "utf-8");
		String date = new java.util.Date().toLocaleString();
	
%>
<%="[ " + nickname + " ] 于" + date + "发表一条微博，内容如下：" %>
<%=content %>
<%}%>