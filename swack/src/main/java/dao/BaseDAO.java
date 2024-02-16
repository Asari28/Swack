package dao;

import static parameter.DAOParameters.*;
import static parameter.Messages.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import exception.SwackException;

public class BaseDAO {

	protected static DataSource dataSource = null;

	public BaseDAO() throws SwackException {
		try {
			if (dataSource == null) {
				dataSource = getDataSource();
			}
		} catch (NamingException e) {
			throw new SwackException(ERR_DB_CONNECT, e);
		}
	}

	/**
	 * スレッドに排他制御をかけて、コネクションプールを取得。ない場合は生成。
	 *
	 * @return dataSource
	 * @throws NamingException
	 */
	private synchronized DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		return (DataSource) context.lookup(JNDI_NAME);
	}
}
