package com.oop.socials.post;

class PostDTO {
    String postBody;
    Integer userID;

    public PostDTO() {}

    public PostDTO(String postBody, Integer userID) {
        this.postBody = postBody;
        this.userID = userID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}