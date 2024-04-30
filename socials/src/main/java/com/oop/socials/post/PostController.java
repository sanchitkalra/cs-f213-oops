package com.oop.socials.post;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PostMapping("/post")
    Object makePost(@RequestBody PostDetails postRequest) {
        postRequest.setDate(LocalDate.now());
        return postRepository.save(postRequest);
    }

    @PatchMapping("/post")
    Object patchPost(@RequestBody PostDetails postRequest) {
        Integer postId = postRequest.postID;
        Optional<PostDetails> postDetailsOptional = postRepository.findById(postId);
        if (postDetailsOptional.isPresent()) {
            PostDetails newPost = postDetailsOptional.get();
            newPost.setPostBody(postRequest.getPostBody());
            postRepository.save(newPost);
            return "Post edited successfully";
        } else {
            return "Post does not exist";
        }
    }

    @DeleteMapping("/post")
    Object deletePost(@RequestParam("postID") Integer postId) {
        Optional<PostDetails> postDetailsOptional = postRepository.findById(postId);
        if (postDetailsOptional.isPresent()) {
            postRepository.deleteById(postId);
            return "Post deleted";
        } else {
            return "Post does not exist";
        }
    }

}
