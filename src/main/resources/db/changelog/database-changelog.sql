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
    PATTERN             VARCHAR(1024)    NOT NULL,
    VALIDATOR_TYPE      VARCHAR(255),
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

--changeset akvine:PROFILEY-1-7
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from DOMAIN_ENTITY where user_id is null;
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-1, 'STUB_UUID', 'email', '06-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-2, 'STUB_UUID', 'pan', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-3, 'STUB_UUID', 'ipv4', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-4, 'STUB_UUID', 'url', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-5, 'STUB_UUID', 'ipv6', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-6, 'STUB_UUID', 'uuid', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-7, 'STUB_UUID', 'date', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-8, 'STUB_UUID', 'time', '07-01-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-9, 'STUB_UUID', 'OGRN', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-10, 'STUB_UUID', 'OGRNIP', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-11, 'STUB_UUID', 'OKVED', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-12, 'STUB_UUID', 'OKOPF', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-13, 'STUB_UUID', 'SNILS', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-14, 'STUB_UUID', 'OSAGO', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-15, 'STUB_UUID', 'IMEI', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-16, 'STUB_UUID', 'IMSI', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-17, 'STUB_UUID', 'VIN', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-18, 'STUB_UUID', 'INN', '01-14-2025 00:00', false, null);
INSERT INTO DOMAIN_ENTITY (ID, UUID, NAME, CREATED_DATE, IS_DELETED, USER_ID) VALUES (-19, 'STUB_UUID', 'Car number (Russia)', '01-20-2025 00:00', false, null);

--changeset akvine:PROFILEY-1-8
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from RULE_ENTITY re join DOMAIN_ENTITY de on re.domain_id = de.id where user_id is null;
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-1, '6b0a1aac-f0d4-42ca-a53d-99c16c194f38', -1, '\b(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,})\b', '06-01-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-2, '6b0a1aac-f0d4-42ca-a53d-99c16c194f39', -2, '\b\d{13,19}\b', '06-01-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-3, '6b0a1aac-f0d4-42ca-a53d-99c16c194f37', -3, '\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b', '06-01-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-4, '6b0a1aac-f0d4-42ca-a53d-99c16c194f36', -4, '\b(http / https): /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#()?&//=]*)/\b', '06-01-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-5, '6b0a1aac-f0d4-42ca-a53d-99c16c194f35', -5, '\b(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))\b', '06-01-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-6, '6b0a1aac-f0d4-42ca-a53d-99c16c194f34', -6, '\b[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\b', '06-01-2025 00:00', false, 'uuid with dashes');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-7, '6b0a1aac-f0d4-42ca-a53d-99c16c194f33', -7, '\b((0[1-9]|[12]\d|3[01])-(0[1-9]|1[0-2])-[12]\d{3})\b', '06-01-2025 00:00', false, 'dd-mm-YYYY');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-8, '6b0a1aac-f0d4-42ca-a53d-99c16c194f32', -7, '\b([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))\b', '06-01-2025 00:00', false, 'YYYY-mm-dd');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-9, '6b0a1aac-f0d4-42ca-a53d-99c16c194f31', -8, '\b(1[0-2]|0?[1-9]):[0-5][0-9] (AM|PM)\b', '06-01-2025 00:00', false, 'HH:mm AM/PM format');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-10, '6b0a1aac-f0d4-42ca-a53d-99c16c194f30', -8, '\b(0[0-9]|1[0-9]|2[1-4]):(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9])\b', '06-01-2025 00:00', false, 'hh:mm:ss format');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, VALIDATOR_TYPE) VALUES (-11, '6b0a1aac-f0d4-42ca-a53d-99c16c194f29', -9, '\b\d{13}(\d{2})?\b', '01-14-2025 00:00', false, 'OGRN');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, VALIDATOR_TYPE) VALUES (-12, '6b0a1aac-f0d4-42ca-a53d-99c16c194f28', -10, '\b\d{15}\b', '01-14-2025 00:00', false, 'OGRNIP');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-13, '6b0a1aac-f0d4-42ca-a53d-99c16c194f27', -11, '\b\d{2}(\.\d{1,2}){0,2}\b', '01-14-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-14, '6b0a1aac-f0d4-42ca-a53d-99c16c194f26', -12, '\b\d{5}\b', '01-14-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, VALIDATOR_TYPE) VALUES (-15, '6b0a1aac-f0d4-42ca-a53d-99c16c194f25', -13, '\b\d{3}-\d{3}-\d{3} \d{2}\b', '01-14-2025 00:00', false, 'SNILS');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-16, '6b0a1aac-f0d4-42ca-a53d-99c16c194f24', -14, '\b[A-Z]{3} \d{10}\b', '01-14-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, VALIDATOR_TYPE) VALUES (-17, '6b0a1aac-f0d4-42ca-a53d-99c16c194f23', -15, '\b\d{15}\b', '01-14-2025 00:00', false, 'IMEI');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED) VALUES (-18, '6b0a1aac-f0d4-42ca-a53d-99c16c194f22', -16, '\b\d{15}\b|\b\d{14}\b|\b\d{13}\b', '01-14-2025 00:00', false);
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS, VALIDATOR_TYPE) VALUES (-19, '6b0a1aac-f0d4-42ca-a53d-99c16c194f21', -17, '\b[A-HJ-NPR-Z0-9]{17}\b', '01-14-2025 00:00', false, 'ISO 3779', 'VIN');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, VALIDATOR_TYPE) VALUES (-20, '6b0a1aac-f0d4-42ca-a53d-99c16c194f20', -18, '\b\d{10}\b|\b\d{12}\b', '01-14-2025 00:00', false, 'INN');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-21, '6b0a1aac-f0d4-42ca-a53d-99c16c194f19', -19, '\b[АВЕКМНОРСТУХ]{2}\d{3}(?<!000)\d{2,3}\b', '01-20-2025 00:00', false, 'Cars and taxi');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-22, '6b0a1aac-f0d4-42ca-a53d-99c16c194f18', -19, '\b[АВЕКМНОРСТУХ]{2}\d{4}(?<!0000)\d{2,3}\b', '01-20-2025 00:00', false, 'Trailers');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-23, '6b0a1aac-f0d4-42ca-a53d-99c16c194f17', -19, '\b\d{4}(?<!0000)[АВЕКМНОРСТУХ]{2}\d{2,3}\b', '01-20-2025 00:00', false, 'Motorcycles and agricultural machinery');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-24, '6b0a1aac-f0d4-42ca-a53d-99c16c194f16', -19, '\b[АВЕКМНОРСТУХ]{2}\d{3}(?<!000)[АВЕКМНОРСТУХ]\d{2,3}\b', '01-20-2025 00:00', false, 'Transit transport');
INSERT INTO RULE_ENTITY (ID, UUID, DOMAIN_ID, PATTERN, CREATED_DATE, IS_DELETED, ALIAS) VALUES (-25, '6b0a1aac-f0d4-42ca-a53d-99c16c194f15', -19, '\bТ[АВЕКМНОРСТУХ]{2}\d{3}(?<!000)\d{2,3}\b', '01-20-2025 00:00', false, 'Outbound transport');

--changeset akvine:PROFILEY-1-9
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from DICTIONARY_ENTITY de where de.locale is null;
ALTER TABLE DICTIONARY_ENTITY ALTER COLUMN LOCALE DROP NOT NULL;

--changeset akvine:PROFILEY-1-10
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.columns WHERE table_name = 'USER_ENTITY' AND column_name = 'IS_DISABLED_SYSTEM_DOMAINS';
ALTER TABLE USER_ENTITY
ADD COLUMN IS_DISABLED_SYSTEM_DOMAINS BOOLEAN NOT NULL DEFAULT FALSE;

--changeset akvine:PROFILEY-1-11
--preconditions onFail:MARK_RAN onError:HALT onUpdateSql:FAIL
--precondition-sql-check expectedResult:0 select count(*) from information_schema.columns WHERE table_name = 'USER_ENTITY' AND column_name = 'IS_DISABLED_SYSTEM_RULES';
ALTER TABLE USER_ENTITY
ADD COLUMN IS_DISABLED_SYSTEM_RULES BOOLEAN NOT NULL DEFAULT FALSE;