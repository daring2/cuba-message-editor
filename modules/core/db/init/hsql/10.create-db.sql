-- begin MSG_MESSAGE
create table MSG_MESSAGE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PACK varchar(1000) not null,
    KEY_ varchar(1000) not null,
    LANGUAGE_ varchar(64),
    ACTIVE boolean,
    MESSAGE varchar(255),
    --
    primary key (ID)
)^
-- end MSG_MESSAGE
