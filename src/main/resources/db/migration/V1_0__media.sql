CREATE TABLE IF NOT EXISTS `file_media`
(
    id         character(10)   NOT NULL,
    created_at timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name       character(1024) NOT NULL,
    `type`     character(255)  NOT NULL,
    size       bigint          NOT NULL,
    path       character(512),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `store_media`
(
    id         character(10)   NOT NULL,
    created_at timestamp       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    name       character(1024) NOT NULL,
    `type`     character(255)  NOT NULL,
    size       bigint          NOT NULL,
    `data`     blob,

    PRIMARY KEY (id)
);
