DROP TABLE IF EXISTS posts;

CREATE TABLE posts (
  id bigserial PRIMARY KEY,
  content varchar(250) NOT NULL,
  user_id bigserial NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);
