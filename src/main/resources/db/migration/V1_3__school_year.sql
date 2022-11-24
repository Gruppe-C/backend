CREATE TABLE IF NOT EXISTS `school_year`
(
    id         character(10) NOT NULL,
    created_at timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    start_year number        NOT NULL,
    end_year   number        NOT NULL,
    group_id   character(10) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (group_id) REFERENCES `group` (id)
);
