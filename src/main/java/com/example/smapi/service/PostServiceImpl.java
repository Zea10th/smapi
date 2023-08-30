package com.example.smapi.service;

import com.example.smapi.model.Post;
import com.example.smapi.model.User;
import com.example.smapi.repository.PostRepository;
import com.example.smapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public ResponseEntity<?> create(Post post, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Account not found for username:" + username)
        );

        post.setDate(new Date());
        post.setUser(user);

        postRepository.save(post);
        return ResponseEntity.ok("Post created successfully");
    }

    @Override
    public ResponseEntity<List<Post>> getPosts(Authentication authentication) {
        String username = authentication.getName();
        List<Post> posts = postRepository.findByUserUsername(username).orElseThrow(
                () -> new NoSuchElementException("There is no posts made by " + username)
        );
        return ResponseEntity.ok(posts);
    }
}
