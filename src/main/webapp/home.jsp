`<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>헤더 목록 출력</title>
</head>
<body>
<%
Enumeration headerEnum = request.getHeaderNames();
while (headerEnum.hasMoreElements()) {
String headerName = (String) headerEnum.nextElement();
String headerValue = request.getHeader(headerName);
%>
<%=headerName%>
<%=headerValue%><br />
<%
}
%>
</body>
</html>