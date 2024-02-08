create table if not exists tb_account_transaction_notification (
	id bigint not null AUTO_INCREMENT,
	transaction_id bigint null,
	customer_document varchar(14) not null,
	agency_number bigint not null,
	account_number bigint not null,
	amount decimal(38, 2) default 0.0,
	transaction_type varchar (100) not null,
	transaction_datetime TIMESTAMP not null,
	sent_at TIMESTAMP null,
	status varchar (100) not null,
	primary key (id)
);
alter table if exists tb_account_transaction_notification add constraint FK_tb_atn_transaction_id FOREIGN key (transaction_id) references tb_accounts (id);
alter table if exists tb_account_transaction_notification add constraint FK_tb_atn_account_number_agency FOREIGN key (agency_number, account_number) references tb_accounts (agency_number, number);
alter table if exists tb_account_transaction_notification add constraint FK_tb_atn_customer_document FOREIGN key (customer_document) references tb_customers (document);
create index if not exists IDX_atn_status on tb_account_transaction_notification (status);