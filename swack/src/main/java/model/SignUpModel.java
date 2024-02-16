package model;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

public class SignUpModel {
	/**
	 * メールアドレスに不備がないか確認する
	 * @param userName
	 * @param mailaddress
	 * @param password
	 * @return true = 不備あり false = 不備なし
	 * @throws SwackException 
	 */
	public boolean checkSignup(String mailaddress) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		boolean result = usersDAO.checkMailAddress(mailaddress);
		if (result) {
			//不備なし
			return true;
		} else {
			//不備あり
			return false;
		}
	}

	/**
	 * 名前、メールアドレス、パスワードを受け取ってInsertする
	 * ユーザIDはgetNewUserId()で取得する
	 * @param username
	 * @param mailAddress
	 * @param password
	 * @return
	 * @throws SwackException
	 */
	public boolean insert(String username, String mailAddress, String password) throws SwackException {
		//DAOインポート
		UsersDAO usersDAO = new UsersDAO();
		//ユーザIDの取得
		GetNewIdModel getNewIdModel = new GetNewIdModel();
		String userId = getNewIdModel.UserId();
		//User生成
		User user = new User(userId, username, mailAddress, password);
		boolean result = usersDAO.insert(user);
		return result;
	}

}
