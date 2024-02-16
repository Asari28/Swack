package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ChatLog;
import bean.Room;
import dao.ChatDAO;
import exception.SwackException;

/**
 * チャット機能を実行するクラス
 */
public class ChatModel {

	public Room getRoom(String roomId, String userId) throws SwackException {
		return new ChatDAO().getRoom(roomId, userId);
	}

	public ArrayList<Room> getRoomList(String userId) throws SwackException {
		return new ChatDAO().getRoomList(userId);
	}

	public ArrayList<Room> getDirectList(String userId) throws SwackException, SQLException {
		ArrayList<Room> roomList = new ArrayList<Room>();
		ChatDAO chatDAO = new ChatDAO();
		if (userId.equals("U0000")) {
			//Admin用ダイレクトルーム取得
			roomList = chatDAO.adminGetDirectList();
			return roomList;
		}
		roomList = (chatDAO.getDirectList(userId));
		return roomList;
	}

	public List<ChatLog> getChatlogList(String roomId) throws SwackException {
		return new ChatDAO().getChatlogList(roomId);
	}

	public void saveChatLog(String roomId, String userId, String message) throws SwackException {
		new ChatDAO().saveChatlog(roomId, userId, message);
	}

	public boolean deleteChatLog(int chatLogId) throws SwackException {
		//成功したらtrue、失敗したらfalseを返す
		return new ChatDAO().deleteChatlog(chatLogId);
	}

	public String getChatlogUserId(int chatLogId) throws SwackException {
		//成功したらtrue、失敗したらfalseを返す
		return new ChatDAO().getChatlogUserId(chatLogId);
	}

	public boolean editChatLog(int chatLogId, String message) throws SwackException {
		//成功したらtrue、失敗したらfalseを返す
		return new ChatDAO().editChatLog(chatLogId, message);
	}

	public String sanitizing(String str) {
		if (null == str || "".equals(str)) {
			return str;
		}

		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("\'", "&#39;");

		return str;
	}
}