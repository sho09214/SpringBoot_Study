package com.example.SpringBoot_Study.controller;

import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    //一覧表示
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", service.findAll());
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
        service.save(post);
        return "redirect:/posts";
    }

    //詳細表示
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id,
                           Model model) {
        model.addAttribute("post", service.findById(id).orElseThrow());
        return "posts/detail";
    }

    //編集フォーム
    @GetMapping("{id}/edit")
    public String editPost(@PathVariable Long id,
                           Model model) {
        model.addAttribute("post", service.findById(id).orElseThrow());
        return "posts/edit";
    }

    //投稿更新
    @PostMapping("/{id}")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute Post post) {
        Post existingPost = service.findById(id).orElseThrow();
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        service.save(existingPost);
        return "redirect:/posts";
    }

    //投稿削除
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/posts";
    }
}
