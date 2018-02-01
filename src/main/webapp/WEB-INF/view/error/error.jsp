<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shiro演示</title>
</head>
<body>
	<p>出错啦!!!&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/logout">重新登录</a></p>
	<%
		Object ex = request.getAttribute("exception");
		if(ex == null) {
			out.println("不存在异常对象");
			return;
		}
		Exception exception = (Exception)ex;
		out.print(exception.getMessage());
		exception.printStackTrace();
	%>
</body>
</html>