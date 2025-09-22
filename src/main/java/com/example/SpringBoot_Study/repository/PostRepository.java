package com.example.SpringBoot_Study.repository;

import com.example.SpringBoot_Study.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //部分一致
    List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword2);

    //前方一致
    List<Post> findByTitleStartingWithOrContainingStartingWith(String titleKeyword, String contentKeyword2);

    //後方一致
    List<Post> findByTitleEndingWithOrContainingEndingWith(String titleKeyword, String contentKeyword2);
}
