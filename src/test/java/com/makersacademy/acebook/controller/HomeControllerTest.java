package com.makersacademy.acebook.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.view.RedirectView;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerTest {

    private final HomeController testSubject = new HomeController();

    @Test
    public void redirectsToThePostsPage() {
        RedirectView result = testSubject.index();

        assertThat(result.getUrl()).isEqualTo("/posts");
    }

}