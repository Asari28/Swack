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

import bean.User;
import exception.SwackException;
import model.RoomModel;
import model.UserModel;

/**
 * Servlet implementation class CreateRoomServlet
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateRoomServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//セッションからuserを取得する
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//エラーメッセージを取得
		String errorMsg = (String) request.getAttribute("errorMsg");
		System.out.println(errorMsg);
		UserModel usermodel = new UserModel();
		try {
			//ユーザリストを取得
			//エラーメッセージとユーザリストをセット
			ArrayList<User> userlist = usermodel.getUsers(user.getUserId());
			request.setAttribute("userList", userlist);
			request.setAttribute("errorMsg", errorMsg);
			//createroom.jspにフォワード
			request.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(request, response);
		} catch (SwackException e) {
			e.printStackTrace();
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

		// パラメータ取得
		String roomname = request.getParameter("roomName");
		String joinuserid = request.getParameter("joinUserId");
		String privated = request.getParameter("privated");
		boolean directed = false;
		//		System.out.println(roomname);
		//		System.out.println(joinuserid);

		//セッションからuserを取得する
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//準備
		RoomModel roommodel = new RoomModel();
		boolean result = false;
		boolean Privated;
		try {
			if (privated != null) {
				//ユーザがadminか確認
				if (user.getUserId().equals("U0000")) {
					//adminの場合
					Privated = true;
				} else {
					//他ユーザの場合
					request.setAttribute("errorMsg", "作成権限がありません");
					request.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(request, response);
					return;
				}

			} else {
				Privated = false;
			}
			//新しくルームを作る
			result = roommodel.createRoom(roomname, user.getUserId(), directed, Privated);
			if (result) {
				//成功
				boolean Result = false;
				String roomId = roommodel.getMaxRoomId();
				//作った本人をルームに追加
				boolean userresult = roommodel.joinUser(roomId, user.getUserId());
				//Adminが作った本人でなければルームに追加
				if (!(user.getUserId().equals("U0000"))) {
					roommodel.joinUser(roomId, "U0000");
				}
				if (userresult) {
					//ルームに他に参加させるユーザを追加
					Result = roommodel.joinUser(roomId, joinuserid);
					if (Result) {
						//成功
						//セッションにuserをセット
						//リダイレクト
						session.setAttribute("user", user);
						response.sendRedirect("MainServlet");
					} else {
						//失敗
						//エラーメッセージをセットしてcreateroom.jspにフォワード
						request.setAttribute("errorMsg", ERR_SYSTEM);
						request.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(request, response);
						return;
					}
				}
			} else {
				//失敗
				//エラーメッセージをセットしてcreateroom.jspにフォワード
				request.setAttribute("errorMsg", ERR_SYSTEM);
				request.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(request, response);
				return;
			}
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/createroom.jsp").forward(request, response);
			return;
		}

	}

}
