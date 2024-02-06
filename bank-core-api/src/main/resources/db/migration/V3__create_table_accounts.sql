create table if not exists tb_accounts (
	id bigint not null AUTO_INCREMENT,
	number bigint not null, 
	balance decimal(38, 2) default 0.0,
	is_active boolean not null default false,
	agency_number bigint not null,
	customer_document bigint not null,
	primary key (id)
);
alter table if exists tb_accounts add constraint FK_tb_account_agency_id FOREIGN key (agency_number) references tb_agencies (number);
alter table if exists tb_accounts add constraint FK_tb_account_customer_id FOREIGN key (customer_document) references tb_customers (document);
alter table if exists tb_accounts add constraint UK_account_agency_number unique (number, agency_number);
create index if not exists IDX_account_agency_number_customer on tb_accounts (agency_number, number, customer_document);