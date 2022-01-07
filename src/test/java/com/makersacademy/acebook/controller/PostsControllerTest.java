package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostsControllerTest {

    @InjectMocks
    private PostsController testSubject;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Nested
    class GetPostsPageTest {

        @Test
        public void createsAPostObjectToBePopulated() {
            Model model = new ExtendedModelMap();

            testSubject.getPostsPage(model);

            assertThat(model.getAttribute("post")).isNotNull();
            assertThat(model.getAttribute("post")).isInstanceOf(Post.class);
        }

        @Test
        public void populatesPostsObjectFromThePostRepository() {
            List<Post> existingPosts = List.of(new Post(), new Post());
            when(postRepository.findAll()).thenReturn(existingPosts);

            Model model = new ExtendedModelMap();

            testSubject.getPostsPage(model);

            assertThat(model.getAttribute("posts")).isNotNull();
            assertThat(model.getAttribute("posts")).isSameAs(existingPosts);
        }

        @Test
        public void returnsTheCorrectTemplateUrl() {
            String result = testSubject.getPostsPage(new ExtendedModelMap());

            assertThat(result).isEqualTo("posts/index");
        }
    }

    @Nested
    class CreatePostTest {

        @Test
        public void savesProvidedPost() {
            Post post = new Post();

            testSubject.create(post, mock(Principal.class));

            verify(postRepository).save(same(post));
        }

        @Test
        public void addsCurrentUserToSavedPost() {
            Principal principal = mock(Principal.class);
            when(principal.getName()).thenReturn("some-name");

            User user = new User();
            when(userRepository.findByUsername("some-name")).thenReturn(user);

            testSubject.create(new Post(), principal);

            ArgumentCaptor<Post> savedPost = ArgumentCaptor.forClass(Post.class);
            verify(postRepository).save(savedPost.capture());
            assertThat(savedPost.getValue().getUser()).isSameAs(user);
        }

        @Test
        public void redirectsToPostsPageOnSuccess() {
            RedirectView result = testSubject.create(new Post(), mock(Principal.class));

            assertThat(result.getUrl()).isEqualTo("/posts");
        }
    }

}