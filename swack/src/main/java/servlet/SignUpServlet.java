package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import exception.SwackException;
import model.LoginModel;
import model.RoomModel;
import model.SignUpModel;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//signup.jspにフォワード
		request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータ取得
		String username = request.getParameter("userName");
		String mailAddress = request.getParameter("mailAddress");
		String password = request.getParameter("password");
		System.out.println(username);
		System.out.println(mailAddress);
		System.out.println(password);

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (username == null || username.length() == 0) {
			errorMsg.append("ユーザー名が入っていません<br>");
		}
		if (mailAddress == null || mailAddress.length() == 0) {
			errorMsg.append("メールアドレスが入っていません<br>");
		}
		if (password == null || password.length() == 0) {
			errorMsg.append("パスワードが入っていません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
			return;
		}

		// 処理
		try {
			// 登録内容チェック
			boolean result = new SignUpModel().checkSignup(mailAddress);
			if (result) {
				// 登録内容に不備なし
				//Users表に新規ユーザを追加するINSERT
				boolean results = new SignUpModel().insert(username, mailAddress, password);
				if (results) {
					//新規ユーザのユーザIDを取得するためログインモデルを使用してユーザ情報取得
					User user = new LoginModel().selectMailAddress(mailAddress);
					//ユーザ登録が成功していたらR0000に参加させる
					boolean joinResult = new RoomModel().joinUser("R0000", user.getUserId());
					if (joinResult) {
						request.setAttribute("errorMsg", INFO_USERS_ENTRY_SUCCESS);
						request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
					}
					//ユーザ登録が成功していたらログイン時R0000を表示させるようにする
					joinResult = new RoomModel().createLastJoinRoom(user.getUserId());
					if (joinResult) {
						request.setAttribute("errorMsg", INFO_USERS_ENTRY_SUCCESS);
						request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
					}
				}
				return;
			} else {

				// 登録内容に不備あり
				//エラーメッセージをセット
				//signup.jspにフォワード
				request.setAttribute("errorMsg", ERR_USERS_ISREGISTERED);
				request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
				return;
			}

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
			return;
		}
	}

}
