package com.example.SpringBoot_Study.controller;

import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.service.LikeService;
import com.example.SpringBoot_Study.service.PostService;
import com.example.SpringBoot_Study.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;
    private final UserService userService;

    public LikeController(LikeService likeService, PostService postService, UserService userService) {
        this.likeService = likeService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/{postId}/like")
    public String toggleLike(@PathVariable Long postId) {
        //ログインユーザー取得
        User loggedInUser = userService.getCurrentUser();

        //投稿を取得
        Post post  = postService.findById(postId).orElseThrow();

        //いいねの切り替え
        likeService.toggleLike(loggedInUser, post);

        return "redirect:/posts/" + postId;
    }
}
