# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table device_group (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_device_group primary key (id))
;

create table info (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  signature                 varchar(255),
  device_group_id           bigint,
  value                     integer,
  constraint pk_info primary key (id))
;

create table slider (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  range_start               integer,
  range_end                 integer,
  device_group_id           bigint,
  value                     integer,
  constraint pk_slider primary key (id))
;

create table switch (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  device_group_id           bigint,
  value                     tinyint(1) default 0,
  constraint pk_switch primary key (id))
;

alter table info add constraint fk_info_deviceGroup_1 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_info_deviceGroup_1 on info (device_group_id);
alter table slider add constraint fk_slider_deviceGroup_2 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_slider_deviceGroup_2 on slider (device_group_id);
alter table switch add constraint fk_switch_deviceGroup_3 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_switch_deviceGroup_3 on switch (device_group_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table device_group;

drop table info;

drop table slider;

drop table switch;

SET FOREIGN_KEY_CHECKS=1;

