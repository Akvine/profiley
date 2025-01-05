--liquibase formatted sql logicalFilePath:db/changelog/database-changelog.sql

--changeset akvine:PROFILEY-1-1
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'USER_ENTITY'
CREATE TABLE USER_ENTITY (
    ID                  BIGINT NOT NULL PRIMARY KEY,
    UUID                VARCHAR(255)         NOT NULL,
    EMAIL               VARCHAR(255) NOT NULL,
    HASH                VARCHAR(255) NOT NULL,
    CREATED_DATE        TIMESTAMP    NOT NULL,
    UPDATED_DATE        TIMESTAMP,
    IS_DELETED          BOOLEAN      NOT NULL,
    DELETED_DATE        TIMESTAMP
);
CREATE SEQUENCE SEQ_USER_ENTITY START WITH 1 INCREMENT BY 1000;
CREATE UNIQUE INDEX USER_ID_INDX ON USER_ENTITY (ID);
CREATE INDEX USER_EMAIL_IS_DELETED_INDX ON USER_ENTITY (EMAIL, IS_DELETED);
--rollback not required

--changeset akvine:PROFILEY-1-2
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'DOMAIN_ENTITY'
CREATE TABLE DOMAIN_ENTITY (
    ID BIGINT                            NOT NULL PRIMARY KEY,
    UUID                        VARCHAR(255)         NOT NULL,
    NAME            VARCHAR(255)         NOT NULL,
    CREATED_DATE    TIMESTAMP            NOT NULL,
    UPDATED_DATE    TIMESTAMP,
    IS_DELETED      BOOLEAN              NOT NULL,
    DELETED_DATE    TIMESTAMP,
    USER_ID         BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USER_ENTITY(ID) ON DELETE SET NULL
);
CREATE SEQUENCE SEQ_DOMAIN_ENTITY START WITH 1 INCREMENT BY 1000;
CREATE UNIQUE INDEX DOMAIN_ID_INDX ON DOMAIN_ENTITY (ID);
CREATE INDEX DOMAIN_NAME_IS_DELETED_INDX ON DOMAIN_ENTITY (NAME, IS_DELETED);
--rollback not required

--changeset akvine:PROFILEY-1-3
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'DICTIONARY_ENTITY'
CREATE TABLE DICTIONARY_ENTITY (
    ID BIGINT                                        NOT NULL PRIMARY KEY,
    UUID                        VARCHAR(255)         NOT NULL,
    WORDS                       TEXT                 NOT NULL,
    SEPARATOR                   VARCHAR(64)          NOT NULL,
    LOCALE                      VARCHAR(255)         NOT NULL,
    CREATED_DATE                TIMESTAMP            NOT NULL,
    UPDATED_DATE                TIMESTAMP,
    DOMAIN_ID                   BIGINT               NOT NULL,
    FOREIGN KEY (DOMAIN_ID)     REFERENCES DOMAIN_ENTITY(ID)
);
CREATE SEQUENCE SEQ_DICTIONARY_ENTITY START WITH 1 INCREMENT BY 1000;
CREATE UNIQUE INDEX DICTIONARY_ID_INDX ON DICTIONARY_ENTITY (ID);
CREATE UNIQUE INDEX DICTIONARY_UUID_INDX ON DICTIONARY_ENTITY (UUID);
--rollback not required

--changeset akvine:PROFILEY-1-4
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'RULE_ENTITY'
CREATE TABLE RULE_ENTITY (
    ID                  BIGINT          NOT NULL PRIMARY KEY,
    DOMAIN_ID           BIGINT          NOT NULL,
    UUID                VARCHAR(255)    NOT NULL,
    ALIAS               VARCHAR(255),
    PATTERN             VARCHAR(255)    NOT NULL,
    CREATED_DATE        TIMESTAMP       NOT NULL,
    UPDATED_DATE        TIMESTAMP,
    IS_DELETED          BOOLEAN         NOT NULL,
    DELETED_DATE        TIMESTAMP,
    FOREIGN KEY (DOMAIN_ID) REFERENCES DOMAIN_ENTITY(ID)
);
CREATE SEQUENCE SEQ_RULE_ENTITY START WITH 1 INCREMENT BY 1000;
CREATE UNIQUE INDEX RULE_ID_INDX ON RULE_ENTITY (ID);
CREATE UNIQUE INDEX RULE_UUID_INDX ON RULE_ENTITY (UUID);
--rollback not required

--changeset akvine:PROFILEY-1-5
--preconditions onFail:MARK_RAN onError:HALT onUpdateSQL:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'SPRING_SESSION' and table_schema = 'public';
CREATE TABLE SPRING_SESSION
(
    PRIMARY_ID            VARCHAR(36)    NOT NULL,
    SESSION_ID            VARCHAR(36),
    CREATION_TIME         NUMERIC(19, 0) NOT NULL,
    LAST_ACCESS_TIME      NUMERIC(19, 0) NOT NULL,
    MAX_INACTIVE_INTERVAL NUMERIC(10, 0) NOT NULL,
    EXPIRY_TIME           NUMERIC(19, 0) NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);
CREATE INDEX SPRING_SESSION_INDX ON SPRING_SESSION(LAST_ACCESS_TIME);
--rollback not required

--changeset akvine:PROFILEY-1-6
--preconditions onFail:MARK_RAN onError:HALT onUpdateSQL:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where upper(table_name) = 'SPRING_SESSION_ATTRIBUTES' and table_schema = 'public';
CREATE TABLE SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID VARCHAR(36),
    ATTRIBUTE_NAME     VARCHAR(200),
    ATTRIBUTE_BYTES    BYTEA,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
);
CREATE INDEX SPRING_SESSION_ATTRIBUTES_INDX on SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID);
--rollback not required