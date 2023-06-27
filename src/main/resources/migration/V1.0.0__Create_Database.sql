CREATE TABLE IF NOT EXISTS ACCOUNT
(
    ID              VARCHAR(36)        NOT NULL,
    EMAIL           VARCHAR(50)        NOT NULL,
    PASSWORD        VARCHAR(255)       NOT NULL,
    FIRST_NAME      VARCHAR(50)        NOT NULL,
    LAST_NAME       VARCHAR(50)        NOT NULL,
    AVATAR_FILE_ID  VARCHAR(36)                 DEFAULT NULL,
    AVATAR_URL      TEXT                DEFAULT NULL,
    ACTIVATION_CODE VARCHAR(255)        NOT NULL,
    IS_ACTIVATED    ENUM ('YES', 'NO') NOT NULL DEFAULT 'NO',
    IS_DELETED      ENUM ('YES','NO')  NOT NULL DEFAULT 'NO',
    CREATED_TIME    TIMESTAMP(6)       NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    UPDATED_TIME    TIMESTAMP(6)       NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (ID),
    UNIQUE KEY ACCOUNT_UK1 (EMAIL, (IF(IS_DELETED = 'YES', NULL, 1)))
);

CREATE TABLE IF NOT EXISTS DEVICE
(
    ID              VARCHAR(36)        NOT NULL,
    ACCOUNT_ID      VARCHAR(36)        NOT NULL,
    ACCESS_TOKEN    TEXT               NOT NULL,
    REFRESH_TOKEN   TEXT               NOT NULL,
    USER_AGENT      VARCHAR(255)       NOT NULL,
    ACTIVATION_CODE VARCHAR(255)        NOT NULL,
    IS_ACTIVATED    ENUM ('YES', 'NO') NOT NULL DEFAULT 'NO',
    IS_DELETED      ENUM ('YES','NO')  NOT NULL DEFAULT 'NO',
    CREATED_TIME    TIMESTAMP(6)       NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    UPDATED_TIME    TIMESTAMP(6)       NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (ID),
    UNIQUE KEY DEVICE_UK1 (ACCOUNT_ID, USER_AGENT, (IF(IS_DELETED = 'YES', NULL, 1))),
    CONSTRAINT DEVICE_FK_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT (ID)
);

CREATE TABLE IF NOT EXISTS FILE
(
    ID           VARCHAR(36)                NOT NULL,
    OWNER_ID     VARCHAR(36)                NOT NULL,
    NAME         VARCHAR(255)               NOT NULL,
    CONTENT_TYPE VARCHAR(255)               NOT NULL DEFAULT 'application/octet-stream',
    SIZE         BIGINT                     NOT NULL,
    URL          TEXT                       DEFAULT NULL,
    ACCESS       ENUM ('PRIVATE','PUBLIC')  NOT NULL DEFAULT 'PRIVATE',
    IS_DELETED   ENUM ('YES','NO')          NOT NULL DEFAULT 'NO',
    CREATED_TIME TIMESTAMP(6)               NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    UPDATED_TIME TIMESTAMP(6)               NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (ID),
    CONSTRAINT FILE_FK_ACCOUNT FOREIGN KEY (OWNER_ID) REFERENCES ACCOUNT (ID)
);