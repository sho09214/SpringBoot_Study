package com.example.SpringBoot_Study.service;

import com.example.SpringBoot_Study.model.Comment;
import com.example.SpringBoot_Study.model.Post;
import com.example.SpringBoot_Study.model.User;
import com.example.SpringBoot_Study.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<Post> findAll(String sortBy, String sortOrder) {
        //Sort.Orderを使ってソート順を設定
        Sort.Order order;
        if (sortOrder.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, sortBy);
        } else {
            order = new Sort.Order(Sort.Direction.DESC, sortBy);
        }

        //Sortオブジェクト作成
        Sort sort = Sort.by(order);
        return repository.findAll(sort);
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

    public List<Post> searchPosts(String keyword, String matchType, String sortBy, String sortOrder) {
        //Sort.Orderを使ってソート順を設定
        Sort.Order order;
        if (sortOrder.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, sortBy);
        } else {
            order = new Sort.Order(Sort.Direction.DESC, sortBy);
        }

        //Sortオブジェクト作成
        Sort sort = Sort.by(order);

        switch (matchType) {
            case "startswith":
                return repository.findByTitleStartingWithOrContainingStartingWith(keyword, keyword, sort);
            case "endswith":
                return repository.findByTitleEndingWithOrContainingEndingWith(keyword, keyword, sort);
            case "contains":
            default:
                return repository.findByTitleContainingOrContentContaining(keyword, keyword, sort);
        }
    }
}
