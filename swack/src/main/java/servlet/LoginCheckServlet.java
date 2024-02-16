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

@WebServlet("/LoginCheckServlet")
public class LoginCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインチェック
		//セッションからuserを取得
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			// ログイン済の場合は、通常の処理を続行
			super.service(request, response);
		} else {
			// 未ログインの場合は、ログイン画面に遷移
			request.setAttribute("errorMsg", ERR_SESSION_TIMEOUT);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			return;
		}
	}
}
