package com.example.SpringBoot_Study.controller;

import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.service.CommentService;
import com.example.SpringBoot_Study.service.PostService;
import com.example.SpringBoot_Study.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;


    public PostController(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    //一覧表示
    @GetMapping
    public String listPosts(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
                            @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String sortOrder,
                            @RequestParam(value = "matchType", required = false) String matchType) {
        //ログインユーザーチェック
        User loggedInUser = userService.getCurrentUser();
        model.addAttribute("loggedInUserId", loggedInUser.getId());

        //検索フォームの入力値
        List<Post> posts;
        if (keyword != null && !keyword.isEmpty() && matchType != null && !matchType.isEmpty()) {
            posts = postService.searchPosts(keyword, matchType, sortBy, sortOrder);
        } else {
            posts = postService.findAll(sortBy, sortOrder);
        }

        model.addAttribute("posts", posts);
        return "posts/list";
    }

    //新規投稿フォーム
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/new";
    }

    //投稿作成
    @PostMapping("")
    public String createPost(@ModelAttribute Post post) {
        //現在のユーザーを取得
        User user = userService.getCurrentUser();

        post.setUser(user);
        postService.save(post);
        return "redirect:/posts";
    }

    //詳細表示
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id,
                           Model model) {
        //現在のログインユーザー
        User loggedInUser = userService.getCurrentUser();

        model.addAttribute("post", postService.findById(id).orElseThrow());
        model.addAttribute("comments", commentService.findByPostId(id));
        model.addAttribute("loggedInUserId", loggedInUser.getId());
        return "posts/detail";
    }

    //編集フォーム
    @GetMapping("{id}/edit")
    public String editPost(@PathVariable Long id,
                           Model model) {
        //ログインユーザーチェック
        if (!loggedInUserCheck(id)) {
            return "redirect:/posts?error=notAuthorized";
        }

        model.addAttribute("post", postService.findById(id).orElseThrow());
        return "posts/edit";
    }

    //投稿更新
    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute Post post) {
        //ログインユーザーチェック
        if (!loggedInUserCheck(id)) {
            return "redirect:/posts?error=notAuthorized";
        }

        Post existingPost = postService.findById(id).orElseThrow();
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        postService.save(existingPost);
        return "redirect:/posts";
    }

    //投稿削除
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {

        //ログインユーザーチェック
        if (!loggedInUserCheck(id)) {
            return "redirect:/posts?error=notAuthorized";
        }

        //投稿削除
        postService.deleteById(id);
        return "redirect:/posts";
    }

    //ログインユーザーチェック
    private boolean loggedInUserCheck(Long postid) {

        //現在のログインユーザー
        User loggedInUser = userService.getCurrentUser();

        //投稿を取得
        Post post = postService.findById(postid).orElseThrow(() -> new RuntimeException("Post not found"));

        //投稿の所有者を確認
        if (!postService.verifyOwnership(post, loggedInUser)) {
            return false;
        }
        return true;
    }
}
