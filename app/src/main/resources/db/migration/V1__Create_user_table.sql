create table user
(
    id           bigint auto_increment,
    name         varchar(20) not null comment '用户名',
    display_name varchar(20) not null comment '显示名称',
    phone        varchar(15) not null comment '电话号',
    email        varchar(30) not null comment '电子邮件',
    constraint user_pk
        primary key (id)
) comment '用户表';
