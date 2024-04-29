package com.oop.socials.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PostController {
    private final PostRepository postRepository;

    PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/post")
    Object getPost(@RequestParam("postID") Integer postId) {
        Optional<PostDetails> postDetailsOptional = postRepository.findById(postId);
        if (postDetailsOptional.isPresent()) {
            return postDetailsOptional.get();
        } else {
            return "Post does not exist";
        }
    }

//    @PostMapping("/post")

}
