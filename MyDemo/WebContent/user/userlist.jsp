<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'list.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
td{text-align: center;}
</style>
<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(function() {
	$("#submit")
			.bind("click",function() {
				var username = $("#username").val();
				var password = $("#password").val();
				if (username == "" || password == "") {
					alert("用户名或密码不能为空")
					return;
				}
				$.ajax({
					url : "user/login",
					type : "post",
					data : "mydemo.name=" + username
							+ "&mydemo.password=" + password,
					dataType : "json",
					success : function(data) {
						if (data.message == "true") {
							$("#loginDiv").html("");
							$("#loginDiv").append("欢迎您，"+data.mydemo.name+"<input type='button' value='退出' id='back' onclick='document.location.href=\"<%=request.getContextPath()%>/userlist/logout \"'/>");
						} else {
							$("#username").val("");
							$("#password").val("");
							alert(data.message);
						}
					},
					error: function(XMLHttpRequest, textStatus,
							errorThrown) {
						alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus);
					}
				});
			});
});
</script>
</head>

<body>
	<h1 align="center">用户列表</h1>
	<table align="center" border="2" width="40%">
		<tr>
			<td colspan="5">
				<div id="loginDiv">
					<c:choose>
						<c:when test="${sessionScope.loginUser!=null }">
							欢迎您,${sessionScope.loginUser.name}<input type='button' value='退出' id='back' onclick="document.location.href='<%=request.getContextPath()%>/userlist/logout' " />
						</c:when>
						<c:otherwise>
							 姓名<input id="username" type="text" name="mydemo.name">&nbsp;&nbsp;
							密码<input id="password" style="vertical-align: middle" type="password" name="mydemo.password"/>&nbsp;&nbsp;
							<button id="submit">登录</button>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
		</tr>
		<tr>
		<td colspan="5" style="text-align: right;">
			<input type="button" onclick="window.location.href='<%=request.getContextPath()%>/userlist/preadd'" value="添加用户" />
		</td>
		</tr>
		<tr>
			<td>编号</td>
			<td>姓名</td>
			<td>性别</td>
			<td>用户等级</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${userList }" var="user" varStatus="x">
			<c:choose>
			  <c:when test="${x.count%2!= '0' }">
			  	<tr style="background: gray;">
			  </c:when>
			  <c:otherwise>
			   	<tr>
			  </c:otherwise>
			</c:choose>
			<td><a href="<%=request.getContextPath()%>/userlist/preupdate?id=${user.id }">${user.id }</a></td>
			<td>${user.name }</td>
			<td>${user.sex }</td>
			<td>${user.userclass.userclass }</td>
			<td><button onclick="window.location.href='<%=request.getContextPath()%>/userlist/delete?id=${user.id }'">删除</button></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
		