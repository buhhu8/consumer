create table testXml
(
    id                                       uuid      not null,
    client_name          varchar(255),
    amount  numeric(19, 2),
    create_date                              timestamp not null,
    update_date                            timestamp,
    primary key (id)
);