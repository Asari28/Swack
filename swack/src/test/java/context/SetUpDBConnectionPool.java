package context;

import static parameter.DAOParameters.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class SetUpDBConnectionPool {

	public static void setUp() {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "context.MyContextFactory");
		System.setProperty(Context.URL_PKG_PREFIXES, "dao");

		PoolProperties props = new PoolProperties();
		props.setDriverClassName(DB_DRIVER_NAME);
		props.setUrl(DB_ENDPOINT);
		props.setUsername(DB_USERID);
		props.setPassword(DB_PASSWORD);

		DataSource ds = new DataSource();
		ds.setPoolProperties(props);
		Context ctx;
		try {
			ctx = new InitialContext();
			ctx.bind(JNDI_NAME, ds);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
