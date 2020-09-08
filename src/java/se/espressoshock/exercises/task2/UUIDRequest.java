package se.espressoshock.exercises.task2;

import java.io.Serializable;
import java.util.UUID;

public class UUIDRequest implements Serializable {
    public static enum TYPE{
        TERMINATION,
        ACK,
        UUID
    }
    private TYPE type;
    private Object payload;

    public UUIDRequest(TYPE type) {
        this.type = type;
    }

    public UUIDRequest(TYPE type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
