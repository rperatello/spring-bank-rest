create table if not exists tb_transactions (
	id bigint not null AUTO_INCREMENT,
	payer_account_id bigint null,
	payee_account_id bigint not null,
	amount decimal(38, 2) default 0.0,
	transaction_method varchar (100) not null, 
	datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	status varchar (100) not null,
	request text,
	primary key (id)
);
alter table if exists tb_transactions add constraint FK_tb_transactions_payer_ac_id FOREIGN key (payer_account_id) references tb_accounts (id);
alter table if exists tb_transactions add constraint FK_tb_transactions_payee_ac_id FOREIGN key (payee_account_id) references tb_accounts (id);