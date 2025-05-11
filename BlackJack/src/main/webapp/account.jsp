<%@page import="com.seba.blackjack.bc.model.PartitaPC"%>
<%@page import="com.seba.blackjack.bc.PartitaPCBC"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
String username = "";
if (session.getAttribute("username") != null) {
	username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="cdn.html" %>
    <title>Profilo: <%=username%></title>
    <jsp:include page="navbar.jsp" />
	<script src="js/datatable.js"></script>


    <!-- Custom Style -->
    <style>
        body {
            background: linear-gradient(to right, #2c3e50, #3498db);
            color: #fff;
            min-height: 100vh;
        }
        .card {
            background-color: rgba(255, 255, 255);
            color: black;
        }
        .table thead {
            background-color: rgba(255, 255, 255, 0.1);
        }
        .table tbody tr:hover {
            background-color: rgba(255, 255, 255, 0.1);
        }
        .btn-danger {
            background-color: #e74c3c;
            border: none;
        }
        .btn-danger:hover {
            background-color: #c0392b;
        }
        /* Compatta e stilizza i pulsanti del DataTable */
		.dataTables_wrapper .dataTables_paginate .paginate_button {
		    padding: 3px 3px;
		    margin: 0 2px;
		    background-color: rgba(255, 255, 255, 0.1);
		    border: none;
		    border-radius: 6px;
		    color: white !important;
		    font-weight: 500;
		    transition: background-color 0.2s;
		}
		
		.dataTables_wrapper .dataTables_paginate .paginate_button:hover {
		    background-color: rgba(255, 255, 255, 0.3);
		    color: #333399 !important;
		    border: none;
		    box-shadow: none;
		}
		
		.dataTables_wrapper .dataTables_paginate .paginate_button.current {
		    background-color: rgba(255, 255, 255, 0.4) !important;
		    color: #000 !important;
		    font-weight: bold;
		    border: none !important;
		}
		        
		select[name="partiteTable_length"] {
            padding-right: 1rem !important;
        }

        .dataTables_length select {
            background-image: none !important;
            appearance: auto !important;
            -webkit-appearance: auto !important;
            -moz-appearance: auto !important;
        }
    </style>
    <script>
	document.addEventListener("DOMContentLoaded", function () {
	    // SweetAlert2 for delete confirmation
	    document.querySelectorAll('.delete-form').forEach(form => {
	        form.addEventListener('submit', function (e) {
	            e.preventDefault(); // Stop the form from submitting
	
	            Swal.fire({
	                title: 'Sei sicuro?',
	                text: "Questa azione eliminerà la partita.",
	                icon: 'warning',
	                showCancelButton: true,
	                confirmButtonColor: '#e74c3c',
	                cancelButtonColor: '#3085d6',
	                confirmButtonText: 'Sì, elimina!',
	                cancelButtonText: 'Annulla',
	                showClass: {
	                    popup: 'animate__animated animate__zoomIn animate__faster'
	                },
	                hideClass: {
	                    popup: 'animate__animated animate__fadeOut animate__faster'
	                }
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    form.submit(); // Submit the form if confirmed
	                }
	            });
	        });
	    });
	});
	</script>

</head>
<body>

<div class="container-fluid py-5  justify-content-center">
    <div class="card rounded-4 p-4">
        <h2 class="mb-4">Storico Partite – <%=username%></h2>

        <%
        PartitaPC[] partite = PartitaPCBC.getPBC().getPartiteByUsername(username);
        %>

        <div class="table-responsive">
            <table id="partiteTable" class="table table-striped table-hover align-middle text-white">
                <thead>
                    <tr>
                        <th>Stato</th>
                        <th>Punti Banco</th>
                        <th>Punti Utente</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    for (PartitaPC partita : partite) {
                    %>
                    <tr>
                        <td><%=partita.getStato()%></td>
                        <td><%=partita.getPuntibanco()%></td>
                        <td><%=partita.getPuntiutente()%></td>
                        <td>
							<form method="POST"
							      action="/<%=application.getServletContextName()%>/delete?id=<%=partita.getId()%>"
							      class="delete-form">
							    <button class="btn btn-danger btn-sm" type="submit">
							        <i class="bi bi-trash"></i>
							    </button>
							</form>
                        </td>
                    </tr>
                    <%
                    }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
<%
} else {
    response.sendRedirect("login.jsp");
}
%>
