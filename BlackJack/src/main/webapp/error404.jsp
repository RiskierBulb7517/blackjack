<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="cdn.html"%>
    <title>Errore 404 - Pagina non trovata</title>
    <jsp:include page="navbar.jsp" />
    <style>
    body {
        background: linear-gradient(to right, #2c3e50, #3498db);
        color: #fff;
        min-height: 100vh;
    }
</style>
</head>
<body>

    <div class="container d-flex justify-content-center align-items-center" style="min-height: 90vh;">
        <div class="card bg-secondary bg-opacity-25 text-white p-5 rounded-4 shadow-lg text-center">
            <h1 class="display-4">404</h1>
            <h3 class="mb-3">Pagina non trovata</h3>
            <p class="lead">La pagina che stai cercando non esiste o è stata rimossa.</p>
            <hr class="bg-light">
            <a class="btn btn-outline-light mt-3" href="<%=application.getContextPath()%>/index.jsp">
                <i class="bi bi-house-door"></i> Torna alla Home
            </a>
        </div>
    </div>

</body>
</html>
