package com.example.SpringBoot_Study.controller;

import com.example.SpringBoot_Study.model.Comment;
import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.service.CommentService;
import com.example.SpringBoot_Study.service.PostService;
import com.example.SpringBoot_Study.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(CommentService service, CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    //コメントの追加
    @PostMapping("/add")
    public String addComment(@RequestParam Long postId,
                             @RequestParam String content) {
        Post post = postService.findById(postId).orElseThrow();
        User user = userService.getCurrentUser();

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setUser(user);

        commentService.save(comment);
        return "redirect:/posts/" + postId;
    }

    //コメント削除
    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam Long postId) {
        //現在のログインユーザーを取得
        User loggedInUser = userService.getCurrentUser();

        //コメントを取得
        Comment comment = commentService.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!commentService.verifyOwnership(comment, loggedInUser)) {
            return "redirect:/posts/" + postId + "?error=notAuthorized";
        }

        commentService.deleteById(id);
        return "redirect:/posts/" + postId;
    }
}
