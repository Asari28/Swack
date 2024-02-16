package model;

import java.util.ArrayList;

import bean.Room;
import dao.RoomDAO;
import exception.SwackException;

public class RoomModel {

	/**
	 * 新しくルームを作成する
	 * @param roomName ルーム名
	 * @param createdUserId ルーム作成者のユーザID
	 * @param directed ダイレクトフラグ
	 * @param privated プライベートフラグ
	 * @return true=成功 false=失敗
	 * @throws SwackException
	 */
	public boolean createRoom(String roomName, String createdUserId,
			boolean directed, boolean privated) throws SwackException {
		//新しいルームIDの取得
		GetNewIdModel getNewIdModel = new GetNewIdModel();
		String roomId = getNewIdModel.RoomId();
		//INSERT
		RoomDAO roomDAO = new RoomDAO();
		boolean result = roomDAO.createRoom(roomId, roomName, createdUserId, directed, privated);
		if (result) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 新しく作ったルームのIDを取得するメソッド
	 * CreateRoomServletで使用する
	 * @return Striang roomId 新しく作ったルームのID 
	 * @throws SwackException
	 */
	public String getMaxRoomId() throws SwackException {
		// 登録済ルームIDの最大値を取得
		RoomDAO roomDAO = new RoomDAO();
		String maxRoomId = roomDAO.getMaxRoomId();
		return maxRoomId;
	}

	/**
	 * ルームに人を追加する
	 * @param roomId ルームID
	 * @param joinUserId 参加するユーザのユーザID
	 * @return true = 成功 false = 失敗
	 * @throws SwackException
	 */
	public boolean joinUser(String roomId, String joinUserId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		boolean result = roomDAO.insertJoinRoom(roomId, joinUserId);
		if (result) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ルームのIDからルーム情報を取得するメソッド
	 * JoinMemberServletで使用する
	 * @return Room room ルームIDのルーム情報 
	 * @throws SwackException
	 */
	public Room getRoomId(String roomId) throws SwackException {
		// ルームIDをもとにルーム情報を取得
		RoomDAO roomDAO = new RoomDAO();
		Room room = roomDAO.getRoom(roomId);
		return room;
	}

	/**
	 * 自分が参加していないルーム一覧を取得するメソッド
	 * @param userId 自分のユーザID
	 * @return ArrayList<Room> 自分が参加していないルーム一覧
	 * @throws SwackException
	 */
	public ArrayList<Room> getNotJoinRoom(String userId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		ArrayList<Room> roomList = roomDAO.selectUnJoinedRoom(userId);
		return roomList;
	}

	/**
	 * 新規登録したユーザをeveryoneを表示させる
	 * @param userId 新規登録のユーザID
	 * @return true = 成功 false = 失敗
	 * @throws SwackException
	 */
	public boolean createLastJoinRoom(String userId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		return roomDAO.CreateLastRoom(userId);
	}

	/**
	 * 自分が表示したルームを更新
	 * @param userId 自分のユーザID
	 * @param roomId 新しく表示したルームID
	 * @return true = 成功 false = 失敗
	 * @throws SwackException
	 */
	public boolean setLastJoinRoom(String userId, String roomId) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		return roomDAO.updateLastRoom(userId, roomId);
	}

	/**
	 * ユーザのIDから最後に表示していたルームのIDを取得するメソッド
	 * MainServletで使用する
	 * @return String roomId 最後に表示していたルームのID 
	 * @throws SwackException
	 */
	public String getLastJoinRoom(String userId) throws SwackException {
		// ユーザIDをもとにルームIDを取得
		RoomDAO roomDAO = new RoomDAO();
		String roomId = roomDAO.getLastRoom(userId);
		return roomId;
	}

	/**
	 * 複数人ダイレクトチャット専用テーブルにINERTする
	 * @param roomId ルームID
	 * @param roomName ルーム名
	 * @param membercount メンバー数
	 * @return true(成功) false(失敗)
	 * @throws SwackException
	 */
	public boolean insertDirectGroup(String roomId, String roomName, int membercount) throws SwackException {
		RoomDAO roomDAO = new RoomDAO();
		boolean result = roomDAO.insertDirectGroup(roomId, roomName, membercount);
		return result;
	}
}
