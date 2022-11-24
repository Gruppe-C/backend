CREATE TABLE IF NOT EXISTS `subject`
(
    id             character(10) NOT NULL,
    name           character(32) NOT NULL,
    color          character(10) NOT NULL,
    created_at     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    school_year_id character(10) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (school_year_id) REFERENCES `school_year` (id)
);
