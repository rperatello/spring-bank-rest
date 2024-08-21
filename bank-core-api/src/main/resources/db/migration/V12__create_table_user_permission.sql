﻿CREATE TABLE IF NOT EXISTS tb_user_permission (
  id_user bigint NOT NULL,
  id_permission bigint NOT NULL,
  PRIMARY KEY (id_user,id_permission),
  CONSTRAINT fk_user_permission FOREIGN KEY (id_user) REFERENCES tb_users (id),
  CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission) REFERENCES tb_permission (id)
);
create index if not exists IDX_user_permission_permission on tb_user_permission (id_permission);