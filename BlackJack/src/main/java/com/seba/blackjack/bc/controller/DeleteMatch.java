package com.seba.blackjack.bc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seba.blackjack.bc.PartitaPCBC;

@WebServlet("/delete")
public class DeleteMatch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4182465447478136846L;

	private PartitaPCBC partitaBC;

	@Override
	public void init() throws ServletException {
		partitaBC = PartitaPCBC.getPBC();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		try {

		if(id==null) {
			response.sendRedirect("account.jsp");
			return;
		}
		if(partitaBC.getPartitaByID(Long.parseLong(id))==null) {
			response.sendRedirect("account.jsp");
			return;
		}
		partitaBC.deleteMatchByID(Long.parseLong(id));
		response.sendRedirect("account.jsp");
		
		
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
	}

}
