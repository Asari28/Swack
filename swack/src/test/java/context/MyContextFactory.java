package context;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class MyContextFactory implements InitialContextFactory {
    private static final Context CONTEXT = new MyContext();

    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        return CONTEXT;
    }

}
