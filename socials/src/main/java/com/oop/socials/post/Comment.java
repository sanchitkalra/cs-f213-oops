package com.oop.socials.post;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Comment {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    int commentID;

    String commentBody;

    CommentCreator commentCreator;
}
