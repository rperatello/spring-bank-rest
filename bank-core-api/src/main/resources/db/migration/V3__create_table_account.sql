create table if not exists tb_account (
	id bigint not null AUTO_INCREMENT,
	number bigint not null, 
	balance decimal(38, 2) default 0.0,
	is_active boolean not null default false,
	agency_number bigint not null,
	customer_document bigint not null,
	primary key (id)
);
alter table if exists tb_account add constraint FK_tb_account_agency_id FOREIGN key (agency_number) references tb_agency (number);
alter table if exists tb_account add constraint FK_tb_account_customer_id FOREIGN key (customer_document) references tb_customer (document);
alter table if exists tb_account add constraint UK_account_agency_number unique (number, agency_number);
create index if not exists IDX_account_agency_number_customer on tb_account (agency_number, number, customer_document);