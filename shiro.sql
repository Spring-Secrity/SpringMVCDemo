-- create database shirospring DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- grant all privileges on `shirospring`.* to `shiro`@`%` identified by '111111';
-- flush privileges;

-- 创建用户表
drop table if exists `users`;
create table `users` (
	`id` bigint(20) not null auto_increment comment 'id',
	`username` varchar(50) default '' comment '用户名',
	`password` varchar(50) default null comment '密码',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
  	`update_time` timestamp comment '编辑时间',
  	primary key (`id`)
)engine=innodb default charset=utf8;
insert into users(username,password,create_time,update_time) values('chench','111111',now(),now());

-- 创建角色表
drop table if exists `roles_permissions`;
create table `roles_permissions` (
	`id` bigint(20) not null auto_increment comment 'id',
	`role_name` varchar(50) default '' comment '角色名称',
	`permission` varchar(50) default '' comment '角色权限',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
  	`update_time` timestamp comment '编辑时间',
  	primary key (`id`)
)engine=innodb default charset=utf8;
insert into roles_permissions(role_name,permission,create_time,update_time) values('admin','admin:*',now(),now());
insert into roles_permissions(role_name,permission,create_time,update_time) values('user','user:*',now(),now());

-- 创建用户角色表
drop table if exists `user_roles`;
create table `user_roles` (
	`id` bigint(20) not null auto_increment comment 'id',
	`username` varchar(50) not null comment '用户名',
	`role_name` varchar(50) not null comment '角色名称',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp comment '编辑时间',
  	primary key (`id`)
)engine=innodb default charset=utf8;
insert into user_roles(username,role_name,create_time,update_time) values('chench','admin',now(),now());
insert into user_roles(username,role_name,create_time,update_time) values('chench','user',now(),now());