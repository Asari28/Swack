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
 * Servlet implementation class CreateDirectServlet
 */
@WebServlet("/CreateDirectServlet")
public class CreateDirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateDirectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//セッションから情報取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//パラメーター取得
		String roomId = request.getParameter("roomId");
		if (roomId == null) {
			roomId = (String) request.getAttribute("roomId");
		}

		String errorMsg = (String) request.getAttribute("errorMsg");
		System.out.println(errorMsg);
		UserModel usermodel = new UserModel();
		try {
			ArrayList<User> userlist = usermodel.getUnDirectedUsers(user.getUserId());
			request.setAttribute("userList", userlist);
			request.setAttribute("errorMsg", errorMsg);
			request.getRequestDispatcher("/WEB-INF/jsp/startdirectchat.jsp").forward(request, response);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.setAttribute("roomId", ERR_SYSTEM);
			request.getRequestDispatcher("MainServlet").forward(request, response);
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
		//パラメーター取得
		String RoomId = request.getParameter("roomId");
		try {
			String[] userIdList = request.getParameterValues("joinUserId[]");
			System.out.println(userIdList);
			//フラグ取得
			boolean privated = true;
			boolean directed = true;
			System.out.println(RoomId);
			//準備
			RoomModel roommodel = new RoomModel();
			//ダイレクトルームの名前取得
			String roomName = new UserModel().getDirectRoomName(user.getUserId(), userIdList[0]);
			for (int i = 1; i < userIdList.length; i++) {
				roomName += "," + userIdList[i];
			}

			//複数人ダイレクトチャットグループだったときの処理
			if (userIdList.length > 1) {
				//ダイレクトルーム作成
				roommodel.createRoom(roomName, user.getUserId(), directed, privated);
				//作ったルームのID取得
				String roomId = roommodel.getMaxRoomId();
				//名前作る
				String directGroupName = user.getUserName();
				//membercount
				int membercount = 1;
				for (int i = 0; i < userIdList.length; i++) {
					//getNameで名前取得していく
					UserModel userModel = new UserModel();
					String getUserName = userModel.getUserName(userIdList[i]);
					directGroupName += "," + getUserName;
					membercount++;
				}
				//DirectGroupテーブルにINSERT
				boolean rs = roommodel.insertDirectGroup(roomId, directGroupName, membercount);
				if (!(rs)) {
					throw new SwackException();
				}

			} else {
				//ダイレクトルーム作成
				roommodel.createRoom(roomName, user.getUserId(), directed, privated);
			}
			//作ったルームのID取得
			String roomId = roommodel.getMaxRoomId();
			//作成したルームにユーザを登録していく
			//受け取ったIDリストを順番にデータベースに格納させる
			//Adminの登録
			roommodel.joinUser(roomId, "U0000");
			boolean result = roommodel.joinUser(roomId, user.getUserId());
			for (String joinuser : userIdList) {
				if (result) {
					result = roommodel.joinUser(roomId, joinuser);
					if (!(result)) {
						break;
					}
				}
			}
			if (result) {
				//正常に終了した場合はuser,roomをセットしてMainServletにリダイレクト
				session.setAttribute("user", user);
				response.sendRedirect("MainServlet");
			} else {
				//falseだった場合はエラーメッセージをstartdirectchat.jspに表示
				request.setAttribute("errorMsg", ERR_SYSTEM);
				request.getRequestDispatcher("/WEB-INF/jsp/startdirectchat.jsp").forward(request, response);
				return;
			}
		} catch (SwackException e) {
			//何らかのExceptionになった場合はエラーメッセージをstartdirectchat.jspに表示
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/startdirectchat.jsp").forward(request, response);
			return;

		}
	}
}
