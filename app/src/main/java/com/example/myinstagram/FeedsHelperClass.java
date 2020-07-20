package com.example.myinstagram;

public class FeedsHelperClass {
    private String uname;
    private String comment;
    private String imageUrl;

    public FeedsHelperClass(){
        //empty constructor needed
    }

    public FeedsHelperClass(String uname,String comment, String imageUrl) {

        this.uname = uname;
        this.comment = comment.trim();
        this.imageUrl = imageUrl;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
