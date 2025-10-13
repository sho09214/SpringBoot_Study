package com.example.SpringBoot_Study.service;

import com.example.SpringBoot_Study.model.Like;
import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository repository;

    public LikeService(LikeRepository repository) {
        this.repository = repository;
    }

    public void toggleLike(User user, Post post) {
        Optional<Like> existingLike = repository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            repository.delete(existingLike.get());
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);

            repository.save(like);
        }
    }

    public boolean isLikedByUser(Post post, User user) {
        return repository.existsByPostAndUser(post, user);
    }

    public int countLikesForPost(Post post) {
        return repository.countByPost(post);
    }
}
