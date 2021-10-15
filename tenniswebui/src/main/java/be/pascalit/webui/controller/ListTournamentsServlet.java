package be.pascalit.webui.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.pascalit.tennis.services.TournamentService;


/**
 * Servlet implementation ListTournamentServlet class
 */
@WebServlet(urlPatterns = "ListTournaments", name = "ListTournamentsServlet" , description = "Servlet used to list tournaments")
public class ListTournamentsServlet extends HttpServlet {

	private static final long serialVersionUID = 313059407965712408L;

	private static final String JSP_TOURNAMENTS = "tournaments.jsp";

	private TournamentService service;

	/**
	 * Default constructor.
	 */
	public ListTournamentsServlet() {
		this.service = new TournamentService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.service.getFullListTournaments();
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_TOURNAMENTS); // redirect to JSP tournaments page
		dispatcher.forward(request, response);
	}

}
