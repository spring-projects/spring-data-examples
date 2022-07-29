create sequence person_seq start with 1 increment by 50;
create table person (id bigint not null, name varchar(255), tenant varchar(255) not null, primary key (id));
