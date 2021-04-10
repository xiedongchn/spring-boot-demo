-- ddl start
CREATE DATABASE IF NOT EXISTS test default charset utf8 collate utf8_general_ci;

use test;
create table IF NOT EXISTS test.user (
     id   bigint(32)  not null comment '主键' auto_increment primary key,
     name varchar(10) not null comment '姓名',
     idNo varchar(20) null comment '身份证',
     age tinyint(3) default 0 comment '年龄'
) comment '用户测试表';
-- ddl end

-- dml start
insert into test.user(name, idNo, age) values
('张三', '333',  FLOOR(RAND() * 100)), ('李四', '444',  FLOOR(RAND() * 100)), ('王五', '555',  FLOOR(RAND() * 100));
-- dml stop

-- set global time_zone='+8:00';
-- ALTER USER 'root'@'localhost' IDENTIFIED BY '654321';