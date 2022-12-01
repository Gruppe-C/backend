CREATE TABLE IF NOT EXISTS `group`
(
    id         character(10)  NOT NULL,
    created_at timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name       character(200) NOT NULL,
    color      character(20),
    owner_id   character(10)  NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES `user` (id)
);

CREATE TABLE IF NOT EXISTS `group_members`
(
    group_id  character(10) NOT NULL,
    member_id character(10) NOT NULL,

    FOREIGN KEY (group_id) REFERENCES `group` (id),
    FOREIGN KEY (member_id) REFERENCES `user` (id)
);
