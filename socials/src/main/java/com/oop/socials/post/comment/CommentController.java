package com.oop.socials.post.comment;

import com.oop.socials.lib.Errors;
import com.oop.socials.post.PostDetails;
import com.oop.socials.post.PostRepository;
import com.oop.socials.user.UserDetails;
import com.oop.socials.user.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentCreatorRepository commentCreatorRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, CommentCreatorRepository commentCreatorRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentCreatorRepository = commentCreatorRepository;
    }

    @PostMapping("/comment")
    Object postComment(@RequestBody Map<String, Object> requestBody) {
        // request body has postId, userId, and commentBody

        Integer userId = (Integer) requestBody.get("userID");
        Optional<UserDetails> usr = userRepository.findById(userId);
        if (usr.isEmpty()) {
            return Errors.error("User does not exist");
        }

        Integer postId = (Integer) requestBody.get("postID");
        Optional<PostDetails> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return Errors.error("Post does not exist");
        }

//        CommentCreator cc = new CommentCreator();
//        cc.setName(usr.get().getName());
//        cc.setUserID(userId);

        Optional<CommentCreator> cc = commentCreatorRepository.findById(userId);
        if (cc.isEmpty()) {
            CommentCreator newCC = new CommentCreator();
            newCC.setName(usr.get().getName());
            newCC.setUserID(userId);
            CommentCreator savedCC = commentCreatorRepository.save(newCC);

            Comment newComment = new Comment();
            newComment.setCommentBody((String) requestBody.get("commentBody"));
            newComment.setCommentCreator(savedCC);
            newComment.setPost(post.get());

            commentRepository.save(newComment);
        } else {
            Comment newComment = new Comment();
            newComment.setCommentBody((String) requestBody.get("commentBody"));
            newComment.setCommentCreator(cc.get());
            newComment.setPost(post.get());

            commentRepository.save(newComment);
        }

        return "Comment created successfully";
    }

    @GetMapping("/comment")
    Object getComment(@RequestParam("commentID") Integer commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            return commentOptional.get();
        } else {
            return Errors.error("Comment does not exist");
        }
    }

    @DeleteMapping("/comment")
    Object deleteComment(@RequestParam("commentID") Integer commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            commentRepository.deleteById(commentId);
            return "Comment deleted";
        } else {
            return Errors.error("Comment does not exist");
        }
    }

    @PatchMapping("/comment")
    Object patchComment(@RequestBody Map<String, Object> requestBody) {
        Integer commentId = (Integer) requestBody.get("commentID");
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment newComment = commentOptional.get();
            newComment.setCommentBody((String) requestBody.get("commentBody"));
            commentRepository.save(newComment);
            return "Comment edited successfully";
        } else {
            return Errors.error("Comment does not exist");
        }
    }

}
