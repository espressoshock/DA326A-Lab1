package se.espressoshock.preparation.task4;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String username;
    private String text;
    private LocalDateTime snapshotTime;

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
        this.snapshotTime = LocalDateTime.now();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSnapshotTime() {
        return snapshotTime;
    }

    @Override
    public String toString() {
        String usernameColorCode = this.username.equals("SERVER") ? "\u001B[31m" : "\u001B[34m";
        return "(" + this.snapshotTime.toString() + ") " +
                usernameColorCode + this.username + usernameColorCode + ": \u001B[0m" +
                         this.text;
    }
}
