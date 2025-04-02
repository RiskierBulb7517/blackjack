<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="<%=application.getContextPath()%>/index.jsp">Home</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <% if(session.getAttribute("username") == null) { %>
          <li class="nav-item">
            <a class="nav-link" href="<%=application.getContextPath()%>/login.jsp">Accedi</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=application.getContextPath()%>/signin.jsp">Registrati</a>
          </li>
        <% } %>
      </ul>
      
      <% if(session.getAttribute("username") != null) { %>
        <ul class="navbar-nav ms-auto">
        <li class="nav-item">
        	<a class="nav-link" href="<%=application.getContextPath()%>/account.jsp"><%=(String) session.getAttribute("username") %></a>
        </li>
          <li class="nav-item">
            <a class="btn btn-outline-danger" href="<%=application.getContextPath()%>/logout.jsp">Logout</a>
          </li>
        </ul>
      <% } %>
    </div>
  </div>
</nav>


