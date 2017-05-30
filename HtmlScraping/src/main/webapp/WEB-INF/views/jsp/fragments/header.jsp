<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>HTML Analysing Web App </title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>


<nav class="navbar navbar-inverse ">
	<div class="container">

	</div>
</nav>