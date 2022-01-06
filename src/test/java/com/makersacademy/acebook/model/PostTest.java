package com.makersacademy.acebook.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

	private final Post post = new Post("hello");

	@Test
	public void postHasContent() {
		assertThat(post.getContent()).contains("hello");
	}

}
