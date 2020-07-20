package com.example.myinstagram;

public class ChatHelperClass {
    private String friend;
    public ChatHelperClass(){

    }

    public ChatHelperClass(String friend) {
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}
