package dao;

import static parameter.Messages.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Room;
import exception.SwackException;

/**
 * RoomsテーブルとJoinRoomテーブルを操作するDAO
 * @author s20223054
 *
 */

public class RoomDAO extends BaseDAO {
	public RoomDAO() throws SwackException {
		super();
	}

	/**
	 * 現時点でのルームIDの最大値を取得
	 * ルーム作成と同時にINSERTするために使用している
	 * @return String maxRoomId
	 * @throws SwackException
	 */
	public String getMaxRoomId() throws SwackException {
		String sql = "SELECT MAX(ROOMID) AS roomId FROM ROOMS";
		String roomId = null;
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();
			rs.next();
			roomId = rs.getString("roomId");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
		return roomId;
	}

	/**
	 * roomテーブルに新規作成したルームをINSERTする
	 * @param roomId ルームID(モデルで作成)
	 * @param roomName ルーム名
	 * @param createdUserId ルーム作成者ユーザID
	 * @param directed ダイレクトフラグ
	 * @param privated 公開フラグ
	 * @return boolean 成功(true)失敗(false)
	 * @throws SwackException
	 */
	public boolean createRoom(String roomId, String roomName, String createdUserId,
			boolean directed, boolean privated) throws SwackException {
		//Userの中にUserIDも入っている状態で受け取る
		//ルームID,ルーム名、ルーム作成ユーザID、ダイレクトフラグ、フラグの順番
		String sql = "INSERT INTO rooms (roomid,roomname,createduserid,directed,privated) VALUES (?,?,?,?,?)";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SQL組み立て
			//roomId,roomName,createdUserId,directed,privatedの順番でセットする
			pStmt.setString(1, roomId);
			pStmt.setString(2, roomName);
			pStmt.setString(3, createdUserId);
			pStmt.setBoolean(4, directed);
			pStmt.setBoolean(5, privated);

			//SQL実行
			int result = pStmt.executeUpdate();

			//結果
			if (result != 1) {
				return false;//失敗
			}
			return true;//成功

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**
	 * joinroomテーブルに新たに参加するユーザをINSERTする
	 * @param roomId ルームID
	 * @param userId ユーザID
	 * @return boolean 成功(true)失敗(false)
	 * @throws SwackException
	 */

	public boolean insertJoinRoom(String roomId, String userId) throws SwackException {
		//SQL
		String sql = "INSERT INTO joinroom (roomid,userid) VALUES (?,?)";
		try (Connection conn = dataSource.getConnection()) {

			PreparedStatement pStmt = conn.prepareStatement(sql);

			//SQL組み立て
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);

			//SQL実行
			int result = pStmt.executeUpdate();

			//結果
			if (result != 1) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**
	 * 指定したルームIDのルーム情報(ルームID,ルーム名)を取得する
	 * @param roomId ほしいルームのID
	 * @return Room(roomId,roomName) ルームID,ルーム名が入ったRoom型で返却
	 * @throws SwackException
	 */
	public Room getRoom(String roomId) throws SwackException {
		//SQL
		String sql = "SELECT roomid,roomname,createduserid,directed,privated FROM rooms WHERE roomid=?";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SQL組み立て
			pStmt.setString(1, roomId);

			//SQL実行
			ResultSet rs = pStmt.executeQuery();
			rs.next();
			String roomid = rs.getString("roomId");
			String roomname = rs.getString("roomname");
			Room room = new Room(roomid, roomname);

			return room;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	public ArrayList<Room> selectUnJoinedRoom(String userId) throws SwackException {
		//SQL
		String sql = "SELECT r.roomid,r.roomname FROM rooms r WHERE directed IS FALSE AND privated IS FALSE AND roomid NOT IN (SELECT j.roomid FROM joinroom j WHERE j.userid=?)";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SQL組み立て
			pStmt.setString(1, userId);

			//SQL実行
			ResultSet rs = pStmt.executeQuery();

			//結果をリストに詰める
			ArrayList<Room> roomList = new ArrayList<Room>();
			while (rs.next()) {
				String listRoomId = rs.getString("roomid");
				String roomName = rs.getString("roomname");
				Room room = new Room(listRoomId, roomName);
				roomList.add(room);
			}

			return roomList;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**
	 * LastRoomテーブルに新規参加したユーザを'R0000'にINSERTする
	 * @param userId ユーザID
	 * @param roomId ルームID
	 * @return boolean 成功(true)失敗(false)
	 * @throws SwackException
	 */
	public boolean CreateLastRoom(String userId) throws SwackException {
		String sql = "INSERT INTO LASTROOM (USERID,ROOMID) VALUES(?,'R0000')";

		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			//SQL実行
			int result = pStmt.executeUpdate();

			//結果
			if (result != 1) {
				return false;//失敗
			}
			return true;//成功

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**
	 * userIdのroomIdを変更する
	 * @param userId ユーザのID
	 * @param roomId ルームのID
	 * @return boolean 成功(true)失敗(false)
	 * @throws SwackException
	 */
	public boolean updateLastRoom(String userId, String roomId) throws SwackException {
		String sql = "UPDATE LASTROOM SET ROOMID = ? WHERE USERID = ?";

		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, roomId);
			pStmt.setString(2, userId);

			//SQL実行
			int result = pStmt.executeUpdate();

			//結果
			if (result != 1) {
				return false;//失敗
			}
			return true;//成功

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**
	 * ユーザが最後に表示していたルームを取得する
	 * @param userId ユーザID
	 * @return 最後に表示していたルームのルームID
	 * @throws SwackException
	 */
	public String getLastRoom(String userId) throws SwackException {
		//SQL
		String sql = "SELECT ROOMID FROM LASTROOM WHERE USERID = ?";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SQL組み立て
			pStmt.setString(1, userId);

			//SQL実行
			ResultSet rs = pStmt.executeQuery();

			//結果をリストに詰める
			String roomId = null;
			if (rs.next()) {
				roomId = rs.getString("ROOMID");

			}

			return roomId;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

	/**
	 * 複数人ダイレクトチャットだった場合に専用テーブルにINSERTする
	 * @param roomId ルームID
	 * @param roomName ルーム名
	 * @param membercount メンバー数
	 * @return true(成功) false(失敗)
	 * @throws SwackException
	 */
	public boolean insertDirectGroup(String roomId, String roomName, int membercount) throws SwackException {
		//SQL
		String sql = "INSERT INTO directgroups (groupid,roomname,membercount) VALUES (?,?,?)";
		try (Connection conn = dataSource.getConnection()) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			//SQL組み立て
			pStmt.setString(1, roomId);
			pStmt.setString(2, roomName);
			pStmt.setInt(3, membercount);

			//SQL実行
			int rs = pStmt.executeUpdate();
			if (rs != 1) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SwackException(ERR_DB_PROCESS, e);
		}
	}

}