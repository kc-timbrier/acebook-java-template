package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Post;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.PostRepository;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.utils.NowProvider;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;

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

    @Mock
    private NowProvider nowProvider;

    @Nested
    class CreatePostTest {

        @Mock
        private Principal principal;

        @Test
        public void savesProvidedPost() {
            Post post = new Post();

            testSubject.create(post, principal);

            verify(postRepository).save(same(post));
        }

        @Test
        public void addsCurrentUserToSavedPost() {
            when(principal.getName()).thenReturn("some-name");

            User user = new User();
            when(userRepository.findByUsername("some-name")).thenReturn(user);

            testSubject.create(new Post(), principal);

            ArgumentCaptor<Post> savedPost = ArgumentCaptor.forClass(Post.class);
            verify(postRepository).save(savedPost.capture());
            assertThat(savedPost.getValue().getUser()).isSameAs(user);
        }

        @Test
        public void addsCurrentTimeToSavedPost() {
            LocalDateTime currentTime = LocalDateTime.of(2022, 1, 1, 12, 0);
            when(nowProvider.now()).thenReturn(currentTime);

            testSubject.create(new Post(), principal);

            ArgumentCaptor<Post> savedPost = ArgumentCaptor.forClass(Post.class);
            verify(postRepository).save(savedPost.capture());
            assertThat(savedPost.getValue().getPostTime()).isEqualTo(currentTime);
        }

        @Test
        public void redirectsToFeedPageOnSuccess() {
            RedirectView result = testSubject.create(new Post(), principal);

            assertThat(result.getUrl()).isEqualTo("/feed");
        }
    }

    @Nested
    class DeletePostTest {

        @Test
        public void deletesPostFromTheRepository() {
            testSubject.delete(123L);

            verify(postRepository).deleteById(123L);
        }

        @Test
        public void redirectsToFeedPageOnSuccess() {
            RedirectView result = testSubject.delete(321L);

            assertThat(result.getUrl()).isEqualTo("/feed");
        }
    }

}