package parameter;

/**
 * データベースアクセス情報管理クラス
 */
public class DAOParameters {

	/** コネクションプールJNDI名（tomcatのserver.xmlに詳細設定を記述） */
	public static final String JNDI_NAME = "java:comp/env/jdbc/postgres";

	/* ローカルDB用 */
	/** ローカル用DBドライバ名 */
	public static final String DB_DRIVER_NAME = "org.postgresql.Driver";
	/** ローカル用DBエンドポイント(スキーマ名付き) */
	public static final String DB_ENDPOINT = "jdbc:postgresql://localhost:5551/appdb";
	/** ローカル用DBユーザ */
	public static final String DB_USERID = "postgres";
	/** ローカル用DBパスワード */
	public static final String DB_PASSWORD = "postgres";
}
