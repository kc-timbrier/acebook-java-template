package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @InjectMocks
    private HomeController testSubject;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void rootRedirectsToTheFeedPage() {
        RedirectView result = testSubject.index();

        assertThat(result.getUrl()).isEqualTo("/feed");
    }

    @Nested
    class GetFeedPageTest {

        @Mock
        private Principal principal;

        private User currentUser;

        @BeforeEach
        public void setupCurrentUser() {
            currentUser = new User();
            when(principal.getName()).thenReturn("current-user");
            when(userRepository.findByUsername("current-user")).thenReturn(currentUser);
        }

        @Test
        public void createsAPostObjectToBePopulated() {
            Model model = new ExtendedModelMap();

            testSubject.getFeedPage(model, principal);

            assertThat(model.getAttribute("post")).isNotNull();
            assertThat(model.getAttribute("post")).isInstanceOf(Post.class);
        }

        @Test
        public void createsAFriendRequestObjectToBePopulated() {
            Model model = new ExtendedModelMap();

            testSubject.getFeedPage(model, principal);

            assertThat(model.getAttribute("friendRequest")).isNotNull();
            assertThat(model.getAttribute("friendRequest")).isInstanceOf(FriendRequest.class);
        }

        @Test
        public void onlyRequestsPostsMadeByCurrentUserOrFriends() {
            User friend1 = new User();
            User friend2 = new User();
            currentUser.setId(0L);
            friend1.setId(1L);
            friend2.setId(2L);
            currentUser.setFriends(List.of(friend1, friend2));

            testSubject.getFeedPage(new ExtendedModelMap(), principal);

            verify(postRepository).findByUserIdIn(List.of(0L, 1L, 2L));
        }

        @Test
        public void populatesPostsObjectFromThePostRepository() {
            List<Post> existingPosts = List.of(new Post(), new Post());
            when(postRepository.findByUserIdIn(any())).thenReturn(existingPosts);

            Model model = new ExtendedModelMap();

            testSubject.getFeedPage(model, principal);

            assertThat(model.getAttribute("posts")).isNotNull();
            assertThat(model.getAttribute("posts")).isSameAs(existingPosts);
        }

        @Test
        public void populatesFriendsObjectFromTheUserRepository() {
            currentUser.setFriends(List.of(new User(), new User()));

            Model model = new ExtendedModelMap();

            testSubject.getFeedPage(model, principal);

            assertThat(model.getAttribute("friends")).isNotNull();
            assertThat(model.getAttribute("friends")).isSameAs(currentUser.getFriends());
        }

        @Test
        public void returnsTheCorrectTemplateUrl() {
            String result = testSubject.getFeedPage(new ExtendedModelMap(), principal);

            assertThat(result).isEqualTo("feed/index");
        }
    }

}