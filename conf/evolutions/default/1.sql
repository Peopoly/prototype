# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table connection (
  id                        bigint not null,
  producer_id               bigint,
  consumer_id               bigint,
  description               varchar(255),
  constraint pk_connection primary key (id))
;

create table peer (
  id                        bigint not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_peer primary key (id))
;

create sequence connection_seq;

create sequence peer_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists connection;

drop table if exists peer;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists connection_seq;

drop sequence if exists peer_seq;

