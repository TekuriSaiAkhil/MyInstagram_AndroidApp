package com.example.myinstagram;

public class pschatHelperClass {
    private String user;
    private String message;
    private String friend;

    public pschatHelperClass() {
    }

    public pschatHelperClass(String user, String message, String friend) {
        this.user = user;
        this.message = message;
        this.friend = friend;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
