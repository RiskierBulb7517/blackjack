<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1" errorPage="error.jsp"%>
    
<% if(session.getAttribute("username")==null){
	%>    
<!DOCTYPE html>
<html>
<head>
<%@ include file="cdn.html"%>
<title>Accedi</title>
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
        <div class="card text-white bg-secondary bg-opacity-25 shadow-lg p-4 rounded-4" style="min-width: 350px; max-width: 500px; width: 100%;">
            <div class="card-body">
                <h2 class="card-title text-center mb-4">Accedi al tuo account</h2>

                <form action="/<%=application.getServletContextName()%>/accedi" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-person"></i></span>
                            <input type="text" name="username" id="username" class="form-control" placeholder="il tuo username..." required>
                        </div>
                    </div>

                    <div class="mb-4">
                        <label for="password" class="form-label">Password</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-lock"></i></span>
                            <input type="password" name="password" id="password" class="form-control" placeholder="la tua password..." required>
                        </div>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-lg">
                            Accedi <i class="bi bi-box-arrow-in-right ms-2"></i>
                        </button>
                    </div>
                </form>

                <div class="text-center mt-4">
                    <p>Non hai un account? <a href="signin.jsp" class="text-info">Registrati qui</a></p>
                </div>
            </div>
        </div>
    </div>
</html>

<%} else {
response.sendRedirect("account.jsp");
}%>