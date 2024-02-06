create table if not exists tb_agencies (
	id bigint not null AUTO_INCREMENT,
	number bigint not null, 
	name varchar (400) not null,
	primary key (id)
);
alter table if exists tb_agencies add constraint UK_agency_number unique (number);
create index if not exists IDX_agency_number on tb_agencies (number);