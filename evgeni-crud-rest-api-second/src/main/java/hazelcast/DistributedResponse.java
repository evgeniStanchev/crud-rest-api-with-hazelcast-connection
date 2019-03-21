package hazelcast;

import java.io.Serializable;

public class DistributedResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    final Long id;
    final Object response;

    public DistributedResponse(final Long id, final Object response) {
	this.id = id;
	this.response = response;
    }

    public Object getResponse() {
	return response;
    }

    @Override
    public String toString() {
	return "DistributedResponse [id=" + id + ", response=" + response + "]";
    }

}
