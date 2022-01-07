DROP TABLE IF EXISTS friends;

CREATE TABLE friends (
    user_id bigserial NOT NULL,
    friend_id bigserial NOT NULL,
    constraint fk_user_id foreign key(user_id) references users(id),
    constraint fk_friend_id foreign key(friend_id) references users(id)
);
