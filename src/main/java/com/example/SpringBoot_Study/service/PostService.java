package com.example.SpringBoot_Study.service;

import com.example.SpringBoot_Study.model.Comment;
import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean verifyOwnership(Post post, User user) {
        if (post.getUser() == null) {
            return false;
        }

        if (!post.getUser().getId().equals(user.getId())) {
            return false;
        }
        return true;
    }
}
