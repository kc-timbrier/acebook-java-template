package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class FriendsController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/friends")
    public RedirectView add(@ModelAttribute FriendRequest request, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        User friend = userRepository.findByUsername(request.getUsername());

        currentUser.getFriends().add(friend);

        userRepository.save(currentUser);

        return new RedirectView("/feed");
    }
}
