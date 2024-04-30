package com.oop.socials.post.comment;

import com.oop.socials.post.PostDetails;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    int commentID;

    String commentBody;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostDetails post;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private CommentCreator commentCreator;

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

    public CommentCreator getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(CommentCreator commentCreator) {
        this.commentCreator = commentCreator;
    }
}
