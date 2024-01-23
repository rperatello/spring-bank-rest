insert into tb_customer (address, document, name, password) values ('MY HOME, 154', '12345678995', 'CLIENTE 1', 'pf-pass');
insert into tb_customer (address, document, name, password) values ('MY HOME, 154', '12345678000101', 'CLIENTE 1', 'pj-pass');
insert into tb_agency (number, name) values (1 ,'AGÊNCIA CENTRAL');
insert into tb_account (number, balance, is_active, agency_id, customer_id) values (110, 100.0, true, (select id from tb_agency where number = 1), (select id from tb_customer where document = '12345678995'));
insert into tb_account (number, balance, is_active, agency_id, customer_id) values (115, 21.0, true, (select id from tb_agency where number = 1), (select id from tb_customer where document = '12345678995'));
insert into tb_account (number, balance, is_active, agency_id, customer_id) values (110, 0.0, true, (select id from tb_agency where number = 1), (select id from tb_customer where document = '12345678000101'));

