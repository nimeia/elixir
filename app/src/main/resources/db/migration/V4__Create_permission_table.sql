create table permission
(
    id          bigint auto_increment,
    name        varchar(50)  null comment '权限名称',
    show_always boolean      null comment '始终显示',
    level       int          null comment '层次',
    type        int          null comment '类型 -1顶部菜单 0页面 1具体操作',
    title       varchar(50)  null comment '菜单显示项',
    path        varchar(255) not null comment '页面路径/资源链接url',
    component   varchar(20)  null comment '前端组件',
    icon        varchar(255) null comment '图标',
    button_type varchar(10)  null comment '按钮权限类型',
    constraint permission_pk
        primary key (id)
) character set 'utf8mb4' comment '权限菜单';

