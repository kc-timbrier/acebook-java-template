package com.makersacademy.acebook.model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime postTime;

    @OneToOne
    private User user;

    public Post() {}

    public Post(String content) {
        this.content = content;
    }
    public String getContent() { return this.content; }
    public void setContent(String content) { this.content = content; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }

}
