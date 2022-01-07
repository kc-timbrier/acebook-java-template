package com.makersacademy.acebook.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NowProvider {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
