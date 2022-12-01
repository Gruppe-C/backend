CREATE TABLE IF NOT EXISTS `file`
(
    id         character(10)  NOT NULL,
    created_at timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name       character(200) NOT NULL,
    media_id   character(10)  NOT NULL,
    owner_id   character(10)  NOT NULL,
    subject_id character(10)  NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES `user` (id),
    FOREIGN KEY (media_id) REFERENCES `file_media` (id),
    FOREIGN KEY (subject_id) REFERENCES `subject` (id)
);
