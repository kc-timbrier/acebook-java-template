package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.FriendRequest;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendsControllerTest {

    @InjectMocks
    private FriendsController testSubject;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    @Nested
    class AddFriendTest {

        @Test
        public void savesCurrentUser() {
            User currentUser = setupCurrentUser();

            testSubject.add(new FriendRequest(), principal);

            verify(userRepository).save(same(currentUser));
        }

        @Test
        public void addsUserWithRequestedUsernameToSavedUser() {
            setupCurrentUser();

            FriendRequest request = new FriendRequest();
            request.setUsername("some-friend");
            User friend = new User();
            when(userRepository.findByUsername("some-friend")).thenReturn(friend);

            testSubject.add(request, principal);

            ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
            verify(userRepository).save(savedUser.capture());
            assertThat(savedUser.getValue().getFriends()).contains(friend);
        }

        @Test
        public void redirectsToFeedPageOnSuccess() {
            setupCurrentUser();

            RedirectView result = testSubject.add(new FriendRequest(), principal);

            assertThat(result.getUrl()).isEqualTo("/feed");
        }
    }

    private User setupCurrentUser() {
        when(principal.getName()).thenReturn("current-user");
        User currentUser = new User();
        when(userRepository.findByUsername("current-user")).thenReturn(currentUser);
        return currentUser;
    }

}