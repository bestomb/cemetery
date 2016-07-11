<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${!empty systemUser.systemMenuBos}">
  <c:forEach var="menu" items="${systemUser.systemMenuBos}">
    <li ref="${menu.url}">
      <a href="javascript:;" ><i class="fa fa-sitemap"></i> <span class="menuName">${menu.name}</span></a>
    </li>
  </c:forEach>
</c:if>