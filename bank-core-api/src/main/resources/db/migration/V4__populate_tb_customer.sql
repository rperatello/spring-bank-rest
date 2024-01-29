insert into tb_customer (address, document, name, password) values ('MY HOME, 154', '12345678995', 'CLIENTE 1', 'pf-pass');
insert into tb_customer (address, document, name, password) values ('MY HOME, 154', '12345678000101', 'CLIENTE 2', 'pj-pass');
insert into tb_agency (number, name) values (1 ,'AGÊNCIA CENTRAL');
insert into tb_account (number, balance, is_active, agency_number, customer_document) values (110, 100.0, true, 1, '12345678995');
insert into tb_account (number, balance, is_active, agency_number, customer_document) values (115, 21.0, true, 1, '12345678995');
insert into tb_account (number, balance, is_active, agency_number, customer_document) values (112, 0.0, true, 1, '12345678000101');



