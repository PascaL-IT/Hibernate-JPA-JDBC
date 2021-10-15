package be.pascalit.webui.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.pascalit.tennis.dto.PlayerDto;
import be.pascalit.tennis.services.PlayerService;


/**
 * Servlet implementation ListPlayersServlet class
 */
@WebServlet(urlPatterns = "ListPlayers", name = "ListPlayersServlet" , description = "Servlet to list of all (wo)men tennis players")
public class ListPlayersServlet extends HttpServlet {

	private static final long serialVersionUID = 8688842117930701542L;

	private static final String JSP_PLAYERS = "players.jsp";

	private PlayerService service;

	/**
	 * Default constructor.
	 */
	public ListPlayersServlet() {
		this.service = new PlayerService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<PlayerDto> men = this.service.getListPlayers('H');
		List<PlayerDto> women = this.service.getListPlayers('F');

		request.setAttribute("menPlayers", men);
		request.setAttribute("womenPlayers", women);

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSP_PLAYERS); // redirect to JSP players page
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
