<%--
  Created by IntelliJ IDEA.
  User: ？？？
  Date: 2021/6/15
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/task/manager" method="post">
    <p><input type="hidden" name="task_id" value=${entity.task_id}></p>
    <p>任务概要 <input type="text" name="taskProfile" value=${entity.taskProfile}></p>
    <p>任务内容 <input type="text" name="taskContent" value=${entity.taskContent}></p>
    <p>任务金额<input type="text" name="taskCost" value=${entity.taskCost}></p>
    <p>发布者id<input type="text" name="publisher_id" value=${entity.publisher_id}></p>
    <p>任务发布状态<input type="text" name="taskPublicState" value="${entity.taskPublicState}"></p>
    <p><input type="hidden" name="taskState" value="${entity.taskState}"></p>
    <p><input type="submit" name="submit" value="submit"></p>
</form>
</body>
</html>
