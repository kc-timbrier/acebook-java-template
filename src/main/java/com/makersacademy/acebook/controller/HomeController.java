package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomeController {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/")
	public RedirectView index() {
		return new RedirectView("/feed");
	}

	@GetMapping("/feed")
	public String getFeedPage(Model model, Principal principal) {
		User currentUser = userRepository.findByUsername(principal.getName());

		model.addAttribute("posts", getPostsVisibleTo(currentUser));
		model.addAttribute("post", new Post());
		model.addAttribute("friendRequest", new FriendRequest());
		model.addAttribute("friends", currentUser.getFriends());

		return "feed/index";
	}

	private List<Post> getPostsVisibleTo(User currentUser) {
		List<Long> friendIds = currentUser
				.getFriends()
				.stream()
				.map(User::getId)
				.collect(Collectors.toList());

		List<Long> visibleUserIds = new ArrayList<>();
		visibleUserIds.add(currentUser.getId());
		visibleUserIds.addAll(friendIds);

		return postRepository.findByUserIdIn(visibleUserIds);
	}
}
