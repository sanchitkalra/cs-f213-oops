package com.oop.socials.feed;

import com.oop.socials.post.PostDetails;
import com.oop.socials.post.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FeedController {
    private final PostRepository postRepository;

    public FeedController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    List<PostDetails> getPosts() {
        List<PostDetails> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        return posts;
    }
}
