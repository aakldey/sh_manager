# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table info (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     integer,
  constraint pk_info primary key (id))
;

create table slider (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     integer,
  constraint pk_slider primary key (id))
;

create table switch (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     tinyint(1) default 0,
  constraint pk_switch primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table info;

drop table slider;

drop table switch;

SET FOREIGN_KEY_CHECKS=1;

