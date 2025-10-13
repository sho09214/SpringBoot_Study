package com.example.SpringBoot_Study.repository;

import com.example.SpringBoot_Study.model.Like;
import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    boolean existsByPostAndUser(Post post, User user);

    int countByPost(Post post);
}
