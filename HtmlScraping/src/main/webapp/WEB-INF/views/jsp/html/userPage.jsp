<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">

    <jsp:include page="../fragments/header.jsp" />

    <div class="container">

        <h1>Please Enter URL</h1>

        <br />

        <spring:url value="/html" var="userActionUrl" />

        <form:form class="form-horizontal" method="post" modelAttribute="analysisForm" action="${userActionUrl}">


            <spring:bind path="url">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Url</label>
                    <div class="col-sm-10">
                        <form:input path="url" type="text" class="form-control " id="url" placeholder="Url" />
                        <form:errors path="url" class="control-label" />
                    </div>
                </div>
            </spring:bind>



            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">

                    <button type="submit" class="btn-lg btn-primary pull-right">Analyse</button>

                </div>
            </div>
        </form:form>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <strong>Error!</strong> ${error}.
            </div>
        </c:if>
        <c:if test="${not empty result}">
            <h1>Analysis Result</h1>
            <div class="table">
                <table class="table table-striped">
                    <thead>

                    </thead>

                    <tr>
                        <td>
                            HTML Version
                        </td>
                        <td>
                            ${result.htmlVersion}
                        </td>
                    </tr>  
                    <tr>
                        <td>
                            Page Title
                        </td>
                        <td>
                            ${result.pageTitle}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Heading Groups Count
                        </td>
                        <td>
                            <c:forEach var="heading" items="${result.headingGroup}" varStatus="loop">
                                h${loop.index +1} count is ${heading}
                                <c:if test="${not loop.last}">,</c:if>
                            </c:forEach></td>

                    </tr>
                    <tr>
                        <td>
                            Internal Links Count
                        </td>
                        <td>
                            ${result.internalLinks}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            External Links Count
                        </td>
                        <td>
                            ${result.externalLinks}
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Has Login Form
                        </td>
                        <td>
                            ${result.loginForm}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            valid Links Count
                        </td>
                        <td>
                            ${result.validLinks}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Invalid Links Count
                        </td>
                        <td>
                            ${result.invalidLinks}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Invalid Links Reasons 
                        </td>
                        <td class="col-md-5">
                            <c:forEach var="Invalid" items="${result.invalidLinksReasons}" varStatus="loop">
                                ${Invalid}
                                <c:if test="${not loop.last}">,</c:if>
                            </c:forEach>
                        </td>
                    </tr>



                </table>
            </div>
        </c:if>

    </div>

    <jsp:include page="../fragments/footer.jsp" />

</body>
</html>