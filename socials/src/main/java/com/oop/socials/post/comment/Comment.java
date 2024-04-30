package com.oop.socials.post.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oop.socials.post.PostDetails;
import com.oop.socials.user.UserDetails;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    int commentID;

    String commentBody;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private PostDetails post;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonIgnore
    private UserDetails commentCreator;

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public PostDetails getPost() {
        return post;
    }

    public void setPost(PostDetails post) {
        this.post = post;
    }

    public UserDetails getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(UserDetails commentCreator) {
        this.commentCreator = commentCreator;
    }
}
