create table if not exists tb_users (
	id bigint NOT NULL AUTO_INCREMENT,
	user_name varchar(255) NOT NULL,
	full_name varchar(255) NULL,
	password varchar(255) NULL,
	account_non_expired boolean NULL,
	account_non_locked boolean NULL,
	credentials_non_expired boolean NULL,
	enabled boolean NULL,
	PRIMARY KEY (id)
);
create index if not exists IDX_user_user_name on tb_users (user_name);