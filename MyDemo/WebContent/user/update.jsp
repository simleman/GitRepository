<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib  uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	<script type="text/javascript">
	  $(function(){
		  var sex="${mydemo.sex}";
		  if(sex=='男'){
			  $("input[name='mydemo.sex']").get(0).checked=true;
		  }else{
			  $("input[name='mydemo.sex']").get(1).checked=true;
		  }
		  var classes="${mydemo.userclass.id}";
		  $("option").get(classes-1).selected=true;
		  $("#classid").val(classes);
 		  $("#select_id").change(function(){
 			  var num=$("#select_id").val();
			  $("#classid").val(num);
 		  });
	  })
	</script>
  </head>
  
  <body>
   <h1 align="center">用户信息修改</h1>
   <form action="userlist/update" method="post">
   <input type="hidden" value="${mydemo.id }" name="mydemo.id ">
    <table align="center" border="1">
     <tr>
     	<td style="background: gray;">姓名</td>
     	<td><input name="mydemo.name" type="text" value="${mydemo.name }"/></td>
     </tr>
     <tr>
     	<td style="background: gray;">性别</td>
     	<td align="center"><input  name="mydemo.sex" type="radio" value="男">男&nbsp;<input name="mydemo.sex" type="radio" value="女">女</td>
     </tr>
     <tr>
     	<td style="background: gray;">用户等级</td>
     	<td><select id="select_id">
     			<c:forEach items="${classList }" var="classes">
     			   <option value="${classes.id }">${classes.userclass}</option>
     			</c:forEach>
     		</select>
     		<input id="classid" type="hidden" value="" name="classid">
     	</td>
     </tr>
     <tr align="center"><td colspan="2" align="center"><input  type="submit" value="保存"/><input type="button" value="返回" onclick="window.history.back();"/></td></tr>
     </table>
   </form>
  </body>
</html>
