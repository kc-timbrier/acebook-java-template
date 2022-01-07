package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.utils.NowProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NowProvider nowProvider;

    @PostMapping("/posts")
    public RedirectView create(@ModelAttribute Post post, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        post.setUser(user);

        post.setPostTime(nowProvider.now());

        postRepository.save(post);
        return new RedirectView("/feed");
    }

    @DeleteMapping("/posts/{id}")
    public RedirectView delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return new RedirectView("/feed");
    }
}
