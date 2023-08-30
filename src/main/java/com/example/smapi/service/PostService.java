package com.example.smapi.service;

import com.example.smapi.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostService {
    ResponseEntity<?> create(Post post, Authentication authentication);

    ResponseEntity<List<Post>> getPosts(Authentication authentication);
}
