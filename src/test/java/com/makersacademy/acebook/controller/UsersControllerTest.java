package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.Authority;
import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.AuthoritiesRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController testSubject;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthoritiesRepository authoritiesRepository;

    @Nested
    class GetRegisterUserPageTest {

        @Test
        public void createsAUserObjectToBePopulated() {
            Model model = new ExtendedModelMap();

            testSubject.getRegisterUserPage(model);

            assertThat(model.getAttribute("user")).isNotNull();
            assertThat(model.getAttribute("user")).isInstanceOf(User.class);
        }

        @Test
        public void returnsTheCorrectTemplateUrl() {
            String result = testSubject.getRegisterUserPage(new ExtendedModelMap());

            assertThat(result).isEqualTo("users/new");
        }

    }

    @Nested
    class RegisterUserTest {

        @Test
        public void savesProvidedUser() {
            User user = new User();

            testSubject.register(user);

            verify(userRepository).save(same(user));
        }

        @Test
        public void savesAuthorityWithUsersNameAndCorrectRole() {
            User user = new User();
            user.setUsername("some-username");

            testSubject.register(user);

            ArgumentCaptor<Authority> savedAuthority = ArgumentCaptor.forClass(Authority.class);
            verify(authoritiesRepository).save(savedAuthority.capture());

            assertThat(savedAuthority.getValue().getUsername()).isEqualTo("some-username");
            assertThat(savedAuthority.getValue().getAuthority()).isEqualTo("ROLE_USER");
        }

        @Test
        public void redirectsToLoginPageOnSuccess() {
            RedirectView result = testSubject.register(new User());

            assertThat(result.getUrl()).isEqualTo("/login");
        }

    }
}