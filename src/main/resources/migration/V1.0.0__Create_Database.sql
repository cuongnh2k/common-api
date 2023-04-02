-- wcp_stg.WCP_JOB_POSTS definition

CREATE TABLE `WCP_JOB_POSTS`
(
    `ID`               bigint(20) NOT NULL AUTO_INCREMENT,
    `ORG_ID`           char(36) COLLATE utf8mb4_bin    NOT NULL,
    `JOB_TYPE_ID`      bigint(20) NOT NULL,
    `TITLE`            varchar(80) COLLATE utf8mb4_bin NOT NULL,
    `SLUG`             varchar(80) COLLATE utf8mb4_bin NOT NULL,
    `META_TITLE`       varchar(80) COLLATE utf8mb4_bin          DEFAULT NULL,
    `PUBLISHED_AT`     timestamp                       NOT NULL DEFAULT current_timestamp(),
    `LAST_UPDATED_AT`  timestamp                       NOT NULL DEFAULT current_timestamp(),
    `IS_DELETED`       enum('YES','NO') COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO',
    `CREATED_AT`       timestamp                       NOT NULL DEFAULT current_timestamp(),
    `UPDATED_AT`       timestamp                       NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp (),
    PRIMARY KEY (`ID`),
    UNIQUE KEY `CBM_PRODUCT_UK2` (`CDN_OWNER_ID`) USING BTREE,
    KEY                `WCP_JOB_POSTS_FK_JOB_TYPE_IDX` (`JOB_TYPE_ID`) USING BTREE,
    CONSTRAINT `WCP_JOB_POSTS_FK_JOB_TYPE` FOREIGN KEY (`JOB_TYPE_ID`) REFERENCES `WCP_JOB_TYPE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `WCP_JOB_POSTS_FK_ORG_INFO` FOREIGN KEY (`ORG_ID`) REFERENCES `ORG_INFO` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=589 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;