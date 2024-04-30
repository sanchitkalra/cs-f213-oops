package com.oop.socials.post;

import com.oop.socials.lib.Errors;
import com.oop.socials.user.UserDetails;
import com.oop.socials.user.UserRepository;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/post")
    Object getPost(@RequestParam("postID") Integer postId) {
        Optional<PostDetails> postDetailsOptional = postRepository.findById(postId);
        if (postDetailsOptional.isPresent()) {
            return postDetailsOptional.get();
        } else {
            return Errors.error("Post does not exist");
        }
    }

    @PostMapping("/post")
    Object makePost(@RequestBody PostDTO postRequest) {
        Integer userId = postRequest.userID;

        UserDetails user = userRepository.findById(postRequest.userID).orElse(null);

        if (user == null) {
            return Errors.error("User does not exist");
        }

        PostDetails post = new PostDetails();
        post.setPostBody(postRequest.postBody);
        post.setDate(LocalDate.now());
        postRepository.save(post);

        return "Post created successfully";
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
            return Errors.error("Post does not exist");
        }
    }

    @DeleteMapping("/post")
    Object deletePost(@RequestParam("postID") Integer postId) {
        Optional<PostDetails> postDetailsOptional = postRepository.findById(postId);
        if (postDetailsOptional.isPresent()) {
            postRepository.deleteById(postId);
            return "Post deleted";
        } else {
            return Errors.error("Post does not exist");
        }
    }

}
