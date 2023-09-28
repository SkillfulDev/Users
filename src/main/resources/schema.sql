DROP TABLE IF EXISTS user_entity;
CREATE TABLE user_entity
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email        VARCHAR(255),
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    birthdate    date,
    address      VARCHAR(255),
    phone_number VARCHAR(255),
    CONSTRAINT pk_userentity PRIMARY KEY (id)
);