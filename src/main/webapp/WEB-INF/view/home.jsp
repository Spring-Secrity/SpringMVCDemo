<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shiro集成Spring-主页</title>
</head>
<body>
	<jsp:include page="./header.jsp" />
	<div>
		<!-- 使用shiro标签 -->
		<shiro:hasPermission name="admin:*">
			<a href="<%=request.getContextPath()%>/user/home">用户管理</a><br/>
		</shiro:hasPermission>
		<a href="#" onclick="updateRole('<%=request.getContextPath()%>/perm/delRole')">删除用户管理角色</a><br/>
		<a href="#" onclick="updateRole('<%=request.getContextPath()%>/perm/addRole')">添加用户管理角色</a><br/>
	</div>
	<jsp:include page="./footer.jsp" />
</body>
<script type="text/javascript" src="static/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	function updateRole(url) {
		$.post(url, function(data){
			console.log(data);
			window.location.reload();
		});
	}
</script>
</html>

