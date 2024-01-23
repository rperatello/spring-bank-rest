create table if not exists tb_account (
	id bigint not null AUTO_INCREMENT,
	number bigint not null, 
	balance decimal(38, 2) default 0.0,
	is_active boolean not null default false,
	agency_id bigint not null,
	customer_id bigint not null,
	primary key (id)
);
alter table if exists tb_account add constraint FK_tb_account_agency_id FOREIGN key (agency_id) references tb_agency (id);
alter table if exists tb_account add constraint FK_tb_account_customer_id FOREIGN key (customer_id) references tb_customer (id);