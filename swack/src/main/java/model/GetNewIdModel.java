package model;

import dao.RoomDAO;
import dao.UsersDAO;
import exception.SwackException;

public class GetNewIdModel {

	/**
	 * Usersから最大値を取得して新しいユーザIDを返す
	 * @return String userId
	 * @throws SwackException 
	 */
	public String UserId() throws SwackException {
		// 登録済ユーザIDの最大値を取得
		UsersDAO usersDAO = new UsersDAO();
		String maxUserId = usersDAO.maxUserId();
		if (maxUserId != null) {
			// 登録済ユーザIDの最大値から"U"を取り除き、+1して次の番号(数値)にする
			int nextNo = Integer.parseInt(maxUserId.substring(1)) + 1;
			// 次の番号(数値)を4桁の文字列にして、先頭に"U"をつける
			System.out.println(nextNo);
			return "U" + String.format("%04d", nextNo);
		} else {
			// データが無かった(ユーザが登録されていない)場合は初期番号を設定
			System.out.println("初期番号設定");
			return "U" + "0001";
		}
	}

	/**
	 * Roomsから最大値を取得して新しいルームIDを返す
	 * @return String roomId
	 * @throws SwackException
	 */
	public String RoomId() throws SwackException {
		// 登録済ルームIDの最大値を取得
		RoomDAO roomDAO = new RoomDAO();
		String maxRoomId = roomDAO.getMaxRoomId();
		if (maxRoomId != null) {
			// 登録済ルームIDの最大値から"R"を取り除き、+1して次の番号(数値)にする
			int nextNo = Integer.parseInt(maxRoomId.substring(1)) + 1;
			// 次の番号(数値)を4桁の文字列にして、先頭に"R"をつける
			System.out.println(nextNo);
			return "R" + String.format("%04d", nextNo);
		} else {
			// データが無かった(ルームが登録されていない)場合は初期番号を設定
			System.out.println("初期番号設定");
			return "R" + "0001";
		}
	}
}