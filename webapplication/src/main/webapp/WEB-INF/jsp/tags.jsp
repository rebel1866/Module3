<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tags</title>
    <style>
        table, td, th {
            border: 1px solid;
            border-collapse: collapse;
            text-align: center;
        }
        #menu {
            margin-left: 450px;
        }
    </style>
    <script>
        function disableEmptyInputs(form) {
            var controls = form.elements;
            for (var i = 0, iLen = controls.length; i < iLen; i++) {
                controls[i].disabled = controls[i].value == '';
            }
        }
    </script>
</head>
<body>
<div id="menu">
    <a href="${pageContext.request.contextPath}/certificates">CERTIFICATES</a>&nbsp;
    <a href="${pageContext.request.contextPath}/tags">TAGS</a>
</div>
<form action="${pageContext.request.contextPath}/tags" method="post">
    <label>Tag name:
        <input type="text" name="tagName"/>
    </label><br><br>
    <input id="submit" type="submit" value="Search"/>
</form>
<form action="${pageContext.request.contextPath}/tags" method="post" onsubmit="disableEmptyInputs(this)">
    <input type="hidden" name="tagName" value="${params.tagName}"/>
    Sort by :
    <button name="sorting" value="tag_id">id</button>
    <button name="sorting" value="tag_name">tag name</button>
    <label>Sorting order:
        <select name="sortingOrder">
            <option value="asc">Asc</option>
            <option value="desc">Desc</option>
        </select>
    </label>
</form>
<table>
    <tr>
       <th>ID:</th>
        <th>Name:</th>
    </tr>
    <c:forEach var="tag" items="${tags}">
        <tr>
            <td>${tag.tagId}</td>
            <td>${tag.tagName}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
