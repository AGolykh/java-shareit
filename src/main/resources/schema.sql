DROP TABLE IF EXISTS PUBLIC.REQUESTS CASCADE;
DROP TABLE IF EXISTS PUBLIC.BOOKINGS CASCADE;
DROP TABLE IF EXISTS PUBLIC.ITEMS CASCADE;
DROP TABLE IF EXISTS PUBLIC.USERS CASCADE;
DROP TABLE IF EXISTS PUBLIC.COMMENTS CASCADE;

CREATE TABLE IF NOT EXISTS PUBLIC.USERS
(
    USER_ID   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    USER_NAME VARCHAR(100),
    EMAIL     VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS PUBLIC.REQUESTS
(
    REQUEST_ID   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    GENRE_NAME   VARCHAR(100)                                        NOT NULL,
    REQUESTOR_ID BIGINT                                              NOT NULL,
    CONSTRAINT FK_REQUESTS_USERS_ID FOREIGN KEY (REQUESTOR_ID) REFERENCES PUBLIC.USERS (USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PUBLIC.ITEMS
(
    ITEM_ID     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    ITEM_NAME   VARCHAR(100)                                        NOT NULL,
    DESCRIPTION VARCHAR(1000)                                       NOT NULL,
    OWNER_ID    BIGINT                                              NOT NULL,
    REQUEST_ID  BIGINT,
    AVAILABLE   BOOLEAN                                             NOT NULL,
    CONSTRAINT FK_ITEMS_USER_ID FOREIGN KEY (OWNER_ID) REFERENCES PUBLIC.USERS (USER_ID),
    CONSTRAINT FK_ITEMS_REQUESTS_ID FOREIGN KEY (REQUEST_ID) REFERENCES PUBLIC.REQUESTS (REQUEST_ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.BOOKINGS
(
    BOOKING_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    START_TIME TIMESTAMP WITHOUT TIME ZONE                         NOT NULL,
    END_TIME   TIMESTAMP WITHOUT TIME ZONE                         NOT NULL,
    ITEM_ID    BIGINT                                              NOT NULL,
    BOOKER_ID  BIGINT                                              NOT NULL,
    STATUS_ID  INTEGER                                             NOT NULL,
    CONSTRAINT FK_BOOKING_ITEMS_ID FOREIGN KEY (ITEM_ID) REFERENCES PUBLIC.ITEMS (ITEM_ID) ON DELETE CASCADE,
    CONSTRAINT FK_BOOKING_USERS_ID FOREIGN KEY (BOOKER_ID) REFERENCES PUBLIC.USERS (USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PUBLIC.COMMENTS
(
    COMMENT_ID BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    TEXT       VARCHAR(1000)                                       NOT NULL,
    ITEM_ID    BIGINT                                              NOT NULL,
    AUTHOR_ID  BIGINT                                              NOT NULL,
    CONSTRAINT FK_COMMENTS_ITEMS_ID FOREIGN KEY (ITEM_ID) REFERENCES PUBLIC.ITEMS (ITEM_ID) ON DELETE CASCADE,
    CONSTRAINT FK_COMMENTS_USERS_ID FOREIGN KEY (AUTHOR_ID) REFERENCES PUBLIC.USERS (USER_ID) ON DELETE CASCADE
);


