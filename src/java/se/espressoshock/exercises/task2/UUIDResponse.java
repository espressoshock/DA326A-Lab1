package se.espressoshock.exercises.task2;

import java.io.Serializable;

public class UUIDResponse implements Serializable {
    private String uuidStringified;
    private Object payload; // -> will contain the raw serialized JPanel

    public UUIDResponse(String uuid) {
        this.uuidStringified = uuid;
    }

    public UUIDResponse(Object payload) {
        this.payload = payload;
    }

    public UUIDResponse(String uuidStringified, Object payload) {
        this.uuidStringified = uuidStringified;
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getUuid() {
        return uuidStringified;
    }

    public void setUuid(String uuid) {
        this.uuidStringified = uuid;
    }
}
