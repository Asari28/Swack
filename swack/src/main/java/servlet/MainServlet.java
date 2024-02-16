package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatLog;
import bean.Room;
import bean.User;
import exception.SwackException;
import model.ChatModel;
import model.RoomModel;
//import model.ChatModelDummy;

@WebServlet("/MainServlet")
public class MainServlet extends LoginCheckServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 画面から取得
		//前工程でエラーメッセージがあれば取得(主にメッセージ削除)
		try {
			String errorMsg = request.getParameter("errorMsg");
			if (errorMsg == null) {
				errorMsg = "";
			} else {
				errorMsg += "<br>";
			}
			String ErrorMsg = (String) request.getAttribute("errorMsg");
			if (ErrorMsg != null) {
				System.out.println(ErrorMsg);
				errorMsg += ErrorMsg;
			}
			request.setAttribute("errorMsg", errorMsg);
			System.out.println("MainServlet(errorMsg):" + errorMsg);
		} catch (Exception e) {
			System.out.println("MainServlet(errorMsg):エラーメッセージ取得失敗");
		}
		// ログイン情報から取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		RoomModel roomModel = new RoomModel();
		String roomId;
		//最後に開いていたルームを表示させる
		try {
			roomId = roomModel.getLastJoinRoom(user.getUserId());
			System.out.println("roomId :" + roomId);
		} catch (SwackException e1) {
			e1.printStackTrace();
			request.setAttribute("errorMsg", ERR_DB_PROCESS);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		//ルーム、ダイレクト、チャットログの情報を取得
		try {
			ChatModel chatModel = new ChatModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			System.out.println(room.getRoomName());
			List<Room> roomList = chatModel.getRoomList(user.getUserId());
			List<Room> directList = chatModel.getDirectList(user.getUserId());
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);

			// JSPに値を渡す
			request.setAttribute("room", room);
			request.setAttribute("roomList", roomList);
			request.setAttribute("directList", directList);
			request.setAttribute("chatLogList", chatLogList);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		//main.jspにフォワード
		request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//画面から取得
		String roomId = request.getParameter("roomId");
		String message = request.getParameter("message");

		// ログイン情報から取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		ChatModel chatModel = new ChatModel();
		message = chatModel.sanitizing(message);

		//入力されたメッセージをＤＢに保存
		try {
			chatModel.saveChatLog(roomId, user.getUserId(), message);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
		//GET処理にリダイレクト
		response.sendRedirect("MainServlet");
	}

}
