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
import model.UserModel;

/**
 * Servlet implementation class ExitServlet
 */
@WebServlet("/ExitServlet")
public class ExitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExitServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//セッションからuserを取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//エラーメッセージを取得後セット
		request.setAttribute("errorMsg", request.getAttribute("errorMsg"));
		try {
			//ユーザリストを取得
			//ユーザリストをセットしてalluser.jspにフォワード
			ArrayList<User> userList = new UserModel().getUsers(user.getUserId());
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("WEB-INF/jsp/alluser.jsp").forward(request, response);
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("MainServlet").forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//画面からユーザＩＤリストを取得
		String[] userIdList = request.getParameterValues("userIdList");
		try {
			//ユーザを退会させる
			boolean result = new UserModel().exitUser(userIdList);
			if (!result) {
				//失敗
				request.setAttribute("errorMsg", ERR_DB_PROCESS);
			}
			doGet(request, response);
			return;
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			doGet(request, response);
		}

	}

}
