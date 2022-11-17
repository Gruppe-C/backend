CREATE TABLE IF NOT EXISTS `user`
(
    id          character(10)  NOT NULL,
    username    character(15)  NOT NULL,
    display_name character(30),
    password    character(500) NOT NULL,
    created_at  timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT PK_user_id PRIMARY KEY (id)
);
