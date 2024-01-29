create table if not exists tb_agency (
	id bigint not null AUTO_INCREMENT,
	number bigint not null, 
	name varchar (400) not null,
	primary key (id)
);
alter table if exists tb_agency add constraint UK_agency_number unique (number);
create index if not exists IDX_agency_number on tb_agency (number);