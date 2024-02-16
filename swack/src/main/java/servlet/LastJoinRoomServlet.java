package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.RoomModel;

/**
 * Servlet implementation class LastJoinRoomServlet
 */
@WebServlet("/LastJoinRoomServlet")
public class LastJoinRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LastJoinRoomServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//パラメーター取得
		String roomId = request.getParameter("roomId");
		//セッションからuserを取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//準備
		RoomModel roomModel = new RoomModel();
		try {
			//自分が表示したルームを更新
			boolean result = roomModel.setLastJoinRoom(user.getUserId(), roomId);
			if (!result) {
				//失敗
				request.setAttribute("errorMsg", ERR_DB_PROCESS);
			}
			//成功
			//リダイレクト
			response.sendRedirect("MainServlet");
			return;
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("MainServlet").forward(request, response);
			return;
		}

	}

}
