<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add certificate</title>
</head>
<body>
<h1>Add New Certificate</h1>
<form:form method="post" action="addCertificate" modelAttribute="certificate">
    <table >
        <tr>
            <td>Name : </td>
            <td><form:input path="certificateName"  /></td>
            <form:errors path="certificateName" cssClass="error"/><br><br>
        </tr>
        <tr>
            <td>Price :</td>
            <td><form:input path="price" /></td>
            <form:errors path="price" cssClass="error"/><br><br>
        </tr>
        <tr>
            <td>Duration :</td>
            <td><form:input path="duration" /></td>
            <form:errors path="duration" cssClass="error"/><br><br>
        </tr>
        <tr>
            <td>Description :</td>
            <td><form:textarea path="description" /></td>
            <form:errors path="description" cssClass="error"/><br><br>
        </tr>
        <tr>
            <td> </td>
            <td>
                Теги:<br>
                <form:checkboxes path="tags" items="${tags}" itemLabel="tagName" itemValue="tagId" delimiter="</br>"/><br><br>
                <form:errors path="tags" cssClass="error"/><br><br>
                <input type="submit" value="Save" /></td>
        </tr>
    </table>
</form:form>
</body>
</html>
