--liquibase formatted sql

--changeset eahamada:1
create table instance_flux (
  id bigserial not null primary key,
  type varchar(80) not null,
  code_etat int not null,
  date_prise_en_charge TIMESTAMP NOT NULL,
  date_derniere_modification TIMESTAMP NOT NULL
);

--changeset eahamada:2
CREATE OR REPLACE VIEW FLUX_A_TRAITER AS
SELECT '' as fichier_pdf, '' as  cd_doc, '' as  id_tag, '' as  id_empr
FROM dual
WHERE false
