package com.oop.socials.post;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PostDetails {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    Integer postID;

    String postBody;

    LocalDateTime date;

    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments;
}
