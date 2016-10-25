--liquibase formatted sql

--changeset eahamada:1
create table instance_flux (
  id bigserial not null primary key,
  type varchar(80) not null,
  code_etat int not null,
  date_prise_en_charge TIMESTAMP NOT NULL,
  date_derniere_modification TIMESTAMP NOT NULL
);
