package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.SwackException;
import model.ChatModel;

/**
 * Servlet implementation class EditChatLogServlet
 */
@WebServlet("/EditChatLogServlet")
public class EditChatLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditChatLogServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//パラメーター取得
		int chatLogId = Integer.parseInt(request.getParameter("chatLogId"));
		String message = request.getParameter("message");
		ChatModel chatModel = new ChatModel();
		String errorMsg = null;
		try {
			//編集処理
			boolean result = chatModel.editChatLog(chatLogId, message);
			if (result) {
				//成功
				//MainServletにエラーメッセージをセットしてリダイレクト
				errorMsg = "編集処理成功";
				request.setAttribute("errorMsg", errorMsg);
				response.sendRedirect("MainServlet?errorMsg=" + URLEncoder.encode(errorMsg, "UTF-8"));
			} else {
				//失敗
				//MainServletにエラーメッセージをセットしてリダイレクト
				errorMsg = "編集処理失敗";
				request.setAttribute("errorMsg", errorMsg);
				response.sendRedirect("MainServlet?errorMsg=" + URLEncoder.encode(errorMsg, "UTF-8"));
			}

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_DB_PROCESS);
			response.sendRedirect("MainServlet");
		}

	}
}
