<%@page import="sun.misc.BASE64Decoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String content="";
	if(request.getParameter("content") != null) {
		content = request.getParameter("content");
		content = content.replaceAll("%2B", "+");
		BASE64Decoder decoder = new BASE64Decoder();
		content = new String(decoder.decodeBuffer(content), "utf-8");
	}
%>
<%="发表一条微博，内容如下：" %>
<%=content %>