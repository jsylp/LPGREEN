<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Test Encryption Result </title>
</head>
<body>
Test Encryption Result:<BR><BR>
Raw Message: <c:out value="${rawmsg}"/><br>
Encrypted Message: <c:out value="${encryptedmsg}"/><br>
Decrypted Message: <c:out value="${decryptedmsg}"/><br>
<p>
<a href="<c:url value="/testencryption.jsp"/>">Test encryption again</a><br>
<a href="<c:url value='/index.jsp'/>">Done testing</a><br>

</p>
</body>
</html>