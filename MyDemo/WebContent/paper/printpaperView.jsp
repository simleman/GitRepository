<%@ page contentType="application/msword; charset=UTF-8" %>
<% response.setHeader("Content-Disposition","attachment;filename=s.doc");%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="application/msword; charset=UTF-8" />
<title>预览</title>
</head>
<body>
	<h1 align="center">${paperList.papername}</h1>
	<div style="position: absolute; left: 50%; margin: 0 0 0 -180px;">岗位要求：${paperList.station.name}</div>
	<div style="position: absolute; left: 50%; margin: 0 0 0 20px;">日期：${paperList.makedate}</div>
	<div>
		<table align="left" style="margin-top: 50px; margin-left: 410px;">
				<c:forEach items="${qtList}" var="qt">
						<tr><td style="font-weight: bold;padding-top: 20px;">${qt.type}</td></tr>
							<c:forEach items="${paperList.testquestions}" var="tq"	varStatus="status">
								<c:if test="${qt.type==tq.questiontype.type }">
			
									<tr>
										<td width="700"
											style="padding-left: 20px;table-layout: fixed; word-wrap: break-word; word-break: break-all">${ status.index + 1}、${tq.questionname }${tq.questiontype.ischoose==1?"(  )":""}</td>
									</tr>
									<tr>
										<c:choose>
											<c:when test="${tq.questiontype.ischoose==1 }">
												<td style="height: 30px; padding-left: 70px;">${tq.questionoption }</td>
											</c:when>
											<c:otherwise>
												<td><textarea
														style='padding-left: 20px;width: 700px; height: 100px; background-attachment: fixed; background-repeat: no-repeat; border-style: solid; border-color: #FFFFFF';></textarea>
												</td>
											</c:otherwise>
										</c:choose>
			
									</tr>
								</c:if>
							</c:forEach>
				</c:forEach>
		</table>
	</div>
</body>
</html>