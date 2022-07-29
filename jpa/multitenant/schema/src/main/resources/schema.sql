create schema if not exists pivotal;
create schema if not exists vmware;

create sequence pivotal.person_seq start with 1 increment by 50;
create table pivotal.person (id bigint not null, name varchar(255), primary key (id));

create sequence vmware.person_seq start with 1 increment by 50;
create table vmware.person (id bigint not null, name varchar(255), primary key (id));
