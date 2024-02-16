package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Room;
import bean.User;
import exception.SwackException;
import model.RoomModel;
import model.UserModel;

/**
 * Servlet implementation class JoinMemberServlet
 */
@WebServlet("/JoinMemberServlet")
public class JoinMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JoinMemberServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//招待するルームIDのパラメータをmain.jspから取得
		String roomId = request.getParameter("roomId");
		//準備
		UserModel usermodel = new UserModel();
		RoomModel roommodel = new RoomModel();
		try {
			//自分とすでに参加しているユーザ以外のユーザ一覧を取得
			ArrayList<User> userlist = usermodel.getJoinUsers(roomId);
			//ルーム情報取得
			Room nowroom = roommodel.getRoomId(roomId);
			//joinmember.jspに値を入れる
			request.setAttribute("userList", userlist);
			request.setAttribute("nowRoom", nowroom);
			request.setAttribute("roomId", roomId);
			//joinmember.jspにフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/joinmember.jsp").forward(request, response);
		} catch (SwackException e) {
			//何らかのエラーになった場合はエラーメッセージをmain.jspに表示
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//セッションからuserとroomを取得する
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Room room = (Room) session.getAttribute("room");
		//パラメーター取得
		String RoomId = request.getParameter("roomId");
		String joinUserid = request.getParameter("joinUserIdList");
		System.out.println(RoomId);
		System.out.println(joinUserid);
		//準備
		RoomModel roommodel = new RoomModel();
		try {
			//受け取った招待するIDリストを順番にデータベースに格納させる
			boolean result = false;
			result = roommodel.joinUser(RoomId, joinUserid);
			if (result) {
				//正常に終了した場合はuser,roomをセットしてMainServletにリダイレクト
				session.setAttribute("user", user);
				session.setAttribute("room", room);
				response.sendRedirect("MainServlet");
			} else {
				//falseだった場合はエラーメッセージをjoinmember.jspに表示
				request.setAttribute("errorMsg", ERR_SYSTEM);
				request.getRequestDispatcher("/WEB-INF/jsp/joinmember.jsp").forward(request, response);
				return;
			}
		} catch (SwackException e) {
			//何らかのExceptionになった場合はエラーメッセージをjoinmember.jspに表示
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/joinmember.jsp").forward(request, response);
			return;

		}

	}

}
