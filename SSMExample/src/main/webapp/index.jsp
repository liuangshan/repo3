<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<body>
<a href="/SSMExample_war/user/queryAll">查询全部数据</a>
<br>
<form action="/SSMExample_war/user/insert" method="post">
    姓名:<input type="text" name="name"><br>
    年龄:<input type="text" name="age"><br>
    <input type="submit" value="插入表单数据">
</form>
</body>
</html>
