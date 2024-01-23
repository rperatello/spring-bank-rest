create table if not exists tb_customer (
	id bigint not null AUTO_INCREMENT, 
	address varchar(255), 
	document varchar(14) not null, 
	name varchar(255), 
	password varchar(255), 
	primary key (id)
);
alter table if exists tb_customer add constraint UK_customer_document unique (document);