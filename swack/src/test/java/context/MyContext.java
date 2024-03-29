package context;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class MyContext implements Context {
	private final Hashtable<Object, Object> environment = new Hashtable<>();

	@Override
	public Object lookup(String name) throws NamingException {
		return environment.get(name);
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		environment.put(name, obj);
	}

	@Override
	public Object lookup(Name name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public void bind(Name name, Object obj) throws NamingException {
		// pass

	}

	@Override
	public void rebind(Name name, Object obj) throws NamingException {
		// pass

	}

	@Override
	public void rebind(String name, Object obj) throws NamingException {
		// pass

	}

	@Override
	public void unbind(Name name) throws NamingException {
		// pass

	}

	@Override
	public void unbind(String name) throws NamingException {
		// pass

	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		// pass

	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		// pass

	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		// pass

	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		// pass

	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		// pass
		return null;
	}

	@Override
	public String composeName(String name, String prefix) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Object removeFromEnvironment(String propName) throws NamingException {
		// pass
		return null;
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		// pass
		return null;
	}

	@Override
	public void close() throws NamingException {
		// pass

	}

	@Override
	public String getNameInNamespace() throws NamingException {
		// pass
		return null;
	}

}