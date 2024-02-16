package dao;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import context.SetUpDBConnectionPool;
import exception.SwackException;

class UsersDAOTest {

	/*
	 * testInsert()について
	 * @Testの前のコメント//を消去してから実行してください
	 * テスト終わったらテストデータ消さないとバグります
	 * DELETE文コピペ
	 * 
	 * DELETE FROM users WHERE userId='UTEST'
	 *
	 */

	private static UsersDAO usersDAO;

	//テスト動かす前に準備
	@BeforeAll
	static void setUpBeforeClass() throws SwackException {
		SetUpDBConnectionPool.setUp();
		usersDAO = new UsersDAO();
	}

	//	@Test
	//	void testSelectSuccess() throws SwackException {
	//		User userT = usersDAO.select("taro@swack.com", "swack0001");
	//		System.out.println("testSelectSuccess():" + userT.toString());
	//		assertNotNull(userT);
	//	}
	//
	//	@Test
	//	void testSelectFailure() throws SwackException {
	//		User userF = usersDAO.select("taro@swack.com", "password");
	//		assertNull(userF);
	//		System.out.println("testSelectFailure():" + userF);
	//	}
	//
	//	@Test
	//	void testExists() throws SwackException {
	//		boolean result = usersDAO.checkMailAddress("taro@swack.com");
	//		assertFalse(result);
	//		if (result) {
	//			System.out.println("testExists():失敗");
	//		} else {
	//			System.out.println("testExists():成功");
	//		}
	//	}
	//
	//	@Test
	//	void testSelectMaxUserId() throws SwackException {
	//		String userId = usersDAO.maxUserId();
	//		System.out.println("testSelectMaxUserId():" + userId);
	//		assertNotNull(userId);
	//	}
	//
	//	//@Test
	//	void testInsert() throws SwackException {
	//		User user = new User("UTEST", "TESTUSER", "example.Test", "test");
	//		boolean result = usersDAO.insert(user);
	//		assertTrue(result);
	//		if (result) {
	//			System.out.println("testSelectInsert():true");
	//		} else {
	//			System.out.println("testSelectInsert():false");
	//		}
	//
	//	}
	//
	//	@Test
	//	void testSelectAllUser() throws SwackException {
	//		ArrayList<User> userList = usersDAO.selectAllUser("U0001");
	//		System.out.println("testSelectAllUser():Adminと情報太郎が出力されなければ成功");
	//		for (User user : userList) { //拡張for文
	//			System.out.println(user);
	//		}
	//		assertNotNull(userList);
	//	}
	//
	//	@Test
	//	void testSelectNotJoinUser() throws SwackException {
	//		ArrayList<User> userList = usersDAO.selectNotJoinUser("R0001");
	//		System.out.println("testSelectNotJoinUser():Admin、情報太郎、情報花子が出力されなければ成功");
	//		for (User user : userList) {//拡張for文
	//			System.out.println(user);
	//		}
	//		assertNotNull(userList);
	//	}

	//	@Test
	//	void testSelectUnDirectedUser() throws SwackException {
	//		ArrayList<User> userList = usersDAO.selectUnDirectedUser("U0001");
	//		System.out.println("testSelectNotJoinUser():情報太郎、情報次郎、情報花子以外が出力されたら成功");
	//		for (User user : userList) {//拡張for文
	//			System.out.println(user);
	//		}
	//		assertNotNull(userList);
	//	}

	//	@Test
	//	void testExitUser() throws SwackException {
	//		System.out.println("trueがでたら成功");
	//		boolean rs = usersDAO.exitUser("U0001");
	//		System.out.println(rs);
	//	}

	@Test
	void testCheckDate() throws SwackException {
		System.out.println("trueがでたら成功");
		int rs1 = usersDAO.checkDate("U0001");
		int rs2 = usersDAO.checkDate("U0000");
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		System.out.println(rs1);
	}

}
