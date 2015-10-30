<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户登录</title>
</head>
<body>
	<h4>用户登录</h4>
	<form id="user" method="post" name="user" action="list/login">
		<div>
			<table>
				<tbody>
					<tr>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td class="field">用 户 名：</td>
						<td><input id="user_name" type="text" name="mydemo.name" /></td>
					</tr>
					<tr>
						<td>密 码：</td>
						<td><input type="password" name="mydemo.password"></td>
					</tr>
					<s:actionerror />
				</tbody>
			</table>
			<div id="msg"></div>
				<input value="登陆 " type="submit">
		</div>
	</form>
</body>
</html>
