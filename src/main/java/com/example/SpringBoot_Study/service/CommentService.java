package com.example.SpringBoot_Study.service;

import com.example.SpringBoot_Study.model.Comment;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    public List<Comment> findByPostId(Long postid) {
        return repository.findByPostId(postid);
    }

    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean verifyOwnership(Comment comment, User user) {
        if (comment.getUser() == null) {
            return false;
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            return false;
        }
        return true;
    }
}
