# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table device (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     integer,
  device_group_id           bigint,
  constraint pk_device primary key (id))
;

create table device_group (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_device_group primary key (id))
;

create table info (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     integer,
  device_group_id           bigint,
  signature                 varchar(255),
  constraint pk_info primary key (id))
;

create table slider (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     integer,
  device_group_id           bigint,
  range_start               integer,
  range_end                 integer,
  constraint pk_slider primary key (id))
;

create table switch (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  pin_number                integer,
  value                     integer,
  device_group_id           bigint,
  constraint pk_switch primary key (id))
;

create table task (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_task primary key (id))
;

create table task_action (
  id                        bigint auto_increment not null,
  task_id                   bigint,
  constraint pk_task_action primary key (id))
;

create table task_condition (
  id                        bigint auto_increment not null,
  value                     integer,
  condition_type            integer,
  task_id                   bigint,
  constraint ck_task_condition_condition_type check (condition_type in (0,1,2,3,4,5)),
  constraint pk_task_condition primary key (id))
;

alter table device add constraint fk_device_deviceGroup_1 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_device_deviceGroup_1 on device (device_group_id);
alter table info add constraint fk_info_deviceGroup_2 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_info_deviceGroup_2 on info (device_group_id);
alter table slider add constraint fk_slider_deviceGroup_3 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_slider_deviceGroup_3 on slider (device_group_id);
alter table switch add constraint fk_switch_deviceGroup_4 foreign key (device_group_id) references device_group (id) on delete restrict on update restrict;
create index ix_switch_deviceGroup_4 on switch (device_group_id);
alter table task_action add constraint fk_task_action_task_5 foreign key (task_id) references task (id) on delete restrict on update restrict;
create index ix_task_action_task_5 on task_action (task_id);
alter table task_condition add constraint fk_task_condition_task_6 foreign key (task_id) references task (id) on delete restrict on update restrict;
create index ix_task_condition_task_6 on task_condition (task_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table device;

drop table device_group;

drop table info;

drop table slider;

drop table switch;

drop table task;

drop table task_action;

drop table task_condition;

SET FOREIGN_KEY_CHECKS=1;

