package com.example.myinstagram;

public class CommentsHelperClass {
    private String author;
    private String comment;
    private String postId;

    public CommentsHelperClass() {
    }
    public CommentsHelperClass(String author, String comment, String postId) {
        this.author = author;
        this.comment = comment;
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
