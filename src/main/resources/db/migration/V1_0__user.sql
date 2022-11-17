CREATE TABLE IF NOT EXISTS `user`
(
    id         character(10)  NOT NULL,
    username   character(255) NOT NULL,
    password   character(255) NOT NULL,
    created_at timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT PK_user_id PRIMARY KEY (id)
);
