<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<title>Convert Json To Table</title>
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>
<H1>Json to HTML Table Conversion</H1>
        	<FORM ACTION="/convertjson" METHOD="POST">
            Please enter your text:
            <BR>
            <TEXTAREA NAME="jsontextarea" ROWS="20" COLS="100"></TEXTAREA>
            <BR>
            <INPUT TYPE="SUBMIT" VALUE="Convert Json To Table">
            </FORM>

</body>
</html>
