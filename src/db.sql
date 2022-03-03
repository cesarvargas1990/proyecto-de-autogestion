drop table if exists convenio;
drop table if exists departamentos;
drop table if exists jhi_authority;
drop table if exists jhi_user;
drop table if exists jhi_user_authority;
drop table if exists municipio;
drop table if exists programas;
drop table if exists transacciones_nomina;
drop table if exists jhi_user_location;
----------------------------------------
CREATE TABLE convenio(
    id serial primary key,
    name varchar(255),
    identificacion varchar(255)
);
INSERT INTO convenio(name,identificacion)
VALUES
('DPS - Departamento para la Prosperidad Social','0');
---------------------------------------------------
CREATE TABLE jhi_authority
(
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT jhi_authority_pkey PRIMARY KEY (name)
);

INSERT INTO jhi_authority(name)
 VALUES 
 ('ROLE_ADMIN'),
 ('ROLE_USER');
------------------------------------------------------


CREATE TABLE jhi_user
(
    id serial primary key,
    login character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(60) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(50) COLLATE pg_catalog."default",
    last_name character varying(50) COLLATE pg_catalog."default",
    email character varying(191) COLLATE pg_catalog."default",
    image_url character varying(256) COLLATE pg_catalog."default",
    activated boolean NOT NULL,
    lang_key character varying(10) COLLATE pg_catalog."default",
    activation_key character varying(20) COLLATE pg_catalog."default",
    reset_key character varying(20) COLLATE pg_catalog."default",
    created_by character varying(50) COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp without time zone,
    reset_date timestamp without time zone,
    last_modified_by character varying(50) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    document_type character varying COLLATE pg_catalog."default",
    celphone character varying COLLATE pg_catalog."default",
    fk_convenio integer,
    fk_programa integer,
    fk_departamento integer,
    fk_municipio integer,
    first_time boolean,
  
    CONSTRAINT ux_user_email UNIQUE (email),
    CONSTRAINT ux_user_login UNIQUE (login)
);

INSERT INTO jhi_user(login, password_hash, first_name,last_name,email,image_url,activated,lang_key,activation_key,reset_key,created_by,created_date,reset_date,last_modified_by,last_modified_date,document_type,celphone,fk_convenio,fk_programa,fk_departamento,fk_municipio,first_time) 
VALUES
('1107512551','$2a$10$kPkZWPLuhk7d1QMb/nCY2uHfei3RABqPC11gGcwIUd.R5h9zGcAIu', 'Gabriel','Salazar','gabrielsameth@gmail.com',null,TRUE,'es',null,null,'system',null,null,1107512551,CURRENT_TIMESTAMP,'CC - Cédula de ciudadanía','3165489169',1,1,5,187,FALSE),
('1005897121','$2a$10$kPkZWPLuhk7d1QMb/nCY2uHfei3RABqPC11gGcwIUd.R5h9zGcAIu', 'Kevin','Aristizabal','karistizabal307@gmail.com',null,TRUE,'es',null,null,'system',null,null,1107512551,CURRENT_TIMESTAMP,'CC - Cédula de ciudadanía','316123456',1,1,5,187,FALSE),
('1234195763','$2a$10$kPkZWPLuhk7d1QMb/nCY2uHfei3RABqPC11gGcwIUd.R5h9zGcAIu', 'Joan','Caicedo','jaca.caicedo@gmail.com',null,TRUE,'es',null,null,'system',null,null,1107512551,CURRENT_TIMESTAMP,'CC - Cédula de ciudadanía','311234567',1,1,5,187,FALSE),
('1234567810','$2a$10$kPkZWPLuhk7d1QMb/nCY2uHfei3RABqPC11gGcwIUd.R5h9zGcAIu', 'Pepito','Perez','gabriel.salazar@supergiros.com.co',null,TRUE,'es',null,null,'system',null,null,1107512551,CURRENT_TIMESTAMP,'CC - Cédula de ciudadanía','3100000000',1,1,5,187,FALSE);
-------------------------------------------------------------------------------------------------


CREATE TABLE jhi_user_authority
(
    user_id bigint NOT NULL,
    authority_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name),
    CONSTRAINT fk_authority_name FOREIGN KEY (authority_name)
        REFERENCES public.jhi_authority (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public.jhi_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
INSERT INTO jhi_user_authority(user_id,authority_name)
VALUES
(1,'ROLE_ADMIN'),
(1,'ROLE_USER'),
(2,'ROLE_ADMIN'),
(2,'ROLE_USER'),
(3,'ROLE_ADMIN'),
(3,'ROLE_USER'),
(4,'ROLE_USER');

-------------------------------------------------------------
CREATE TABLE programas(
    id serial primary key,
    name varchar(255),
    identificacion varchar(255),
    fk_convenio integer
    
);
INSERT INTO programas(name,identificacion,fk_convenio)
VALUES
('Ingreso solidario','10',1),
('Devolución IVA','20',1),
('Colombia Mayor','30',1),
('Jóvenes Transformadores','40',1);

--------------------------------------------------------------
CREATE TABLE jhi_user_location(
    id serial primary key,
    user_id bigint,
    departamento_name varchar(255),
    municipio_name varchar(255)
    
);
INSERT INTO jhi_user_location(user_id,departamento_name,municipio_name)
VALUES
(1,'24','1033'),
(1,'24','1034'),
(1,'24','1035'),
(2,'24','1033'),
(3,'24','1033'),
(3,'24','1035'),
(4,'24','1033'),
(4,'24','1034'),
(4,'24','1035'),
(4,'9','428');

------------------------------------------------------------------------------------------------

CREATE TABLE municipio(
    id serial primary key,
    name varchar(255),
    cod_dane integer,
    fk_departamento integer
    
);

-----------------------------------------------------------------
CREATE TABLE departamentos(
    id serial primary key,
    name varchar(255),
    cod_dane integer
    
);

---------------------------------------------------------------------


CREATE TABLE transacciones_nomina
(
    id bigint NOT NULL,
    tipo_documento_benef character varying(255) COLLATE pg_catalog."default",
    numero_documento_benef integer,
    nombre_uno character varying(255) COLLATE pg_catalog."default",
    nombre_dos character varying(255) COLLATE pg_catalog."default",
    apellido_uno character varying(255) COLLATE pg_catalog."default",
    apellido_dos character varying(255) COLLATE pg_catalog."default",
    nombre_uno_pago character varying(255) COLLATE pg_catalog."default",
    nombre_dos_pago character varying(255) COLLATE pg_catalog."default",
    apellido_uno_pago character varying(255) COLLATE pg_catalog."default",
    apellido_dos_pago character varying(255) COLLATE pg_catalog."default",
    fecha_pago date,
    hora_pago character varying(255) COLLATE pg_catalog."default",
    pin_pago character varying(255),
    f_k_departamento_de_pago character varying(255),
    f_k_municipio_de_pago character varying(255),
    f_k_departamento character varying(255),
    f_k_municipio character varying(255),
    f_k_id_convenio character varying(255),
    f_k_id_programa character varying(255),
    fecha_de_pago date,
    valor_giro character varying(255),
    estado character varying(255) COLLATE pg_catalog."default",
    periodo_pago character varying(255) COLLATE pg_catalog."default",
    motivo_anulacion character varying(255) COLLATE pg_catalog."default",
    fecha_vigencia date,
    fecha_cargue date,
    nota character varying(255) COLLATE pg_catalog."default",
    red_pagadora character varying(255) COLLATE pg_catalog."default",
    observacion_control character varying(255) COLLATE pg_catalog."default",
    solicitud_autorizacion character varying(255) COLLATE pg_catalog."default"
);