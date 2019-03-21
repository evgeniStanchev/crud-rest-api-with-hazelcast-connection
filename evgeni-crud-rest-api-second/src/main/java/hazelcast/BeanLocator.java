package hazelcast;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BeanLocator {

    public static Object lookup(final String jndiName) {

	InitialContext ic = null;
	try {
	    ic = new InitialContext();
	} catch (NamingException e) {
	    throw new RuntimeException(e);
	}
	try {
	    return ic.lookup(jndiName);
	} catch (NamingException e) {
	    throw new RuntimeException(e);
	}

    }

    public static <T> T lookup(final Class<T> clazz, final String jndiName) {
	return clazz.cast(lookup(jndiName));
    }

}
