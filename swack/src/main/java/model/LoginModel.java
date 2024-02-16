package model;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

/**
 * ログイン認証を実行するクラス
 */
public class LoginModel {

	/**
	 * 存在するユーザか確認
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return ユーザ情報(ログインできなかった場合はnull)
	 */
	public User selectMailAddress(String mailAddress) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.selectmailAddress(mailAddress);

	}

	/**
	 * 存在するユーザか確認
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return ユーザ情報(ログインできなかった場合はnull)
	 */
	public boolean checkMailAddress(String mailAddress) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.checkMailAddress(mailAddress);

	}

	/**
	 * 存在するユーザか確認
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return ユーザ情報(ログインできなかった場合はnull)
	 */
	public boolean checkPassword(String mailAddress, String password) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.selectPassword(mailAddress, password);
	}

	/**
	 * 退会されているかの確認
	 * @param user ユーザの情報
	 * @return ture(退会)　false(異常なし)
	 */
	public boolean checkExit(User user) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		boolean result = false;
		try {
			String state = usersDAO.checkState(user.getUserId());
			if (state.equals("EXIT")) {
				result = true;
			}
		} catch (NullPointerException e) {
			return result;
		}

		return result;
	}

	/**
	 * ロックされているかの確認
	 * @param user ユーザの情報
	 * @return ture(ロック済み)　false(異常なし)
	 */
	public boolean checkLock(User user) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		boolean result = false;
		try {
			String state = usersDAO.checkState(user.getUserId());
			if (state.equals("LOCK")) {
				result = true;
			}
		} catch (NullPointerException e) {
			return result;
		}

		return result;
	}

	public boolean checkDate(String userid) throws SwackException {
		boolean rs = false;
		UsersDAO usersdao = new UsersDAO();
		int date = usersdao.checkDate(userid);
		System.out.println(date);
		if (date > 21) {
			rs = true;
		}
		return rs;
	}

	/**
	 * 連続失敗回数の確認
	 * @param user ユーザの情報
	 * @return int 回数
	 */
	public int getCount(User user) throws SwackException {
		return new UsersDAO().getMissCount(user.getUserId());
	}

	/**
	 * 連続失敗回数のセット
	 * @param user ユーザの情報、回数
	 * @return true(異常なし)　false(処理失敗)
	 */
	public boolean setCount(User user, int cnt) throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		boolean result = usersDAO.setMissCount(user.getUserId(), cnt);
		if (!result) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}
}
