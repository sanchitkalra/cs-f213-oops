package com.oop.socials.post;

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
}
