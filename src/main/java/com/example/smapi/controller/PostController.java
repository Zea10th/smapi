package com.example.smapi.controller;

import com.example.smapi.model.Post;
import com.example.smapi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Post post,
                                        Authentication authentication) {
        return postService.create(post, authentication);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts(Authentication authentication) {
        return postService.getPosts(authentication);
    }
}
