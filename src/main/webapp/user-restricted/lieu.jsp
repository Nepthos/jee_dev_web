<%--
  Created by IntelliJ IDEA.
  User: Adrien
  Date: 29/12/2020
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="pageTitle" scope="request" value="Lieu"/>
<jsp:include page="../header.jsp" />
<div class="mx-auto text-center col-auto col-sm-12 col-md-9 col-xl-6">
<c:out value="${lieu.nom}"/>
</div>
<jsp:include page="../footer.jsp" />
