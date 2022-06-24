<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Certificates</title>
    <script>
        function disableEmptyInputs(form) {
            var controls = form.elements;
            for (var i = 0, iLen = controls.length; i < iLen; i++) {
                controls[i].disabled = controls[i].value == '';
            }
        }
    </script>
    <style>
        table, td, th {
            border: 1px solid;
            border-collapse: collapse;
            text-align: center;
        }

        .price {
            width: 140px;
        }

        #submit {
            margin-left: 180px;
        }

        #tags {
            width: 460px;
        }

        button {
            margin-top: 5px;
        }

        #menu {
            margin-left: 450px;
        }
    </style>
</head>
<body>
<div id="menu">
    <a href="${pageContext.request.contextPath}/certificates">CERTIFICATES</a>&nbsp;
    <a href="${pageContext.request.contextPath}/tags">TAGS</a>
</div>
<form action="${pageContext.request.contextPath}/certificates" method="post" onsubmit="disableEmptyInputs(this)">
    <label>Name:
        <input type="text" name="certificateName"/>
    </label>
    <label>Tag:
        <input type="text" name="tagName"/>
    </label><br><br>
    <label>Price from:
        <input class="price" type="text" name="priceFrom"/>
    </label>
    <label>Price to:
        <input class="price" type="text" name="priceTo"/>
    </label><br><br>
    <input id="submit" type="submit" value="Search"/>
</form>
${message}
<form action="${pageContext.request.contextPath}/certificates" method="post" onsubmit="disableEmptyInputs(this)">
    <input type="hidden" name="certificateName" value="${params.certificateName}"/>
    <input type="hidden" name="tagName" value="${params.tagName}"/>
    <input type="hidden" name="priceFrom" value="${params.priceFrom}"/>
    <input type="hidden" name="priceTo" value="${params.priceTo}"/>
    Sort by :
    <button name="sorting" value="price">price</button>
    <button name="sorting" value="certificate_name">name</button>
    <button name="sorting" value="creation_date">date</button>
    <button name="sorting" value="certificate_name, creation_date">name and date</button>
    <label>Sorting order:
        <select name="sortingOrder">
            <option value="asc">Asc</option>
            <option value="desc">Desc</option>
        </select>
    </label>
</form>
<table>
    <tr>
        <th>Name:</th>
        <th>Price:</th>
        <th>Duration:</th>
        <th>Creation date:</th>
        <th>Last updated:</th>
        <th>Tags:</th>
        <th>Full info:</th>
    </tr>
    <c:forEach items="${certificates}" var="certificate">
        <tr>
            <td>${certificate.certificateName}</td>
            <td>${certificate.price}</td>
            <td>${certificate.duration}</td>
            <td>${certificate.creationDate}</td>
            <td>${certificate.lastUpdateTime}</td>
            <td id="tags">
                <form action="${pageContext.request.contextPath}/certificates" method="post">
                    <c:forEach items="${certificate.tags}" var="tag">
                        <button name="tag_name" value="${tag.tagName}">${tag.tagName}</button>
                    </c:forEach>
                </form>
            </td>
            <td>
              <form method="post" action="${pageContext.request.contextPath}/delete-certificate">
                  <button name="id" value="${certificate.giftCertificateId}">Delete</button>
              </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/add-certificate-form">Add certificate</a>
</body>
</html>
