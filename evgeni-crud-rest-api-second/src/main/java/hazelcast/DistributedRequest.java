package hazelcast;

import java.io.Serializable;
import java.util.Arrays;

public class DistributedRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final String host;

    private final Long id;

    private final Object[] args;

    private final Class<?>[] types;

    private final String lookupName;

    private final String methodName;

    public DistributedRequest(String host, Long id, Object[] args, Class<?>[] types, String lookupName,
	    String methodName) {
	this.host = host;
	this.id = id;
	this.args = args;
	this.types = types;
	this.lookupName = lookupName;
	this.methodName = methodName;
    }

    public String getHost() {
	return this.host;
    }

    public Long getId() {
	return this.id;
    }

    public Object[] getArgs() {
	return this.args;
    }

    public Class<?>[] getTypes() {
	return this.types;
    }

    public String getLookupName() {
	return this.lookupName;
    }

    public String getMethodName() {
	return this.methodName;
    }

    @Override
    public String toString() {
	return "DistributedRequest [host=" + host + ", id=" + id + ", args=" + Arrays.toString(args) + ", types="
		+ Arrays.toString(types) + ", lookupName=" + lookupName + ", methodName=" + methodName + "]";
    }
}
