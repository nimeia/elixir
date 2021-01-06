alter table user
    add password varchar(255) null comment 'user password';

alter table user
    add create_by varchar(20) null comment 'the user create by who';

alter table user
    add create_time datetime not null comment 'the user create date';

alter table user
    add enabled boolean not null comment 'the user lock flag';

alter table user
    add locked boolean not null comment 'the user lock flag';

alter table user
    add lock_time datetime comment 'the locked time of user';

alter table user
    add short_lock_time int comment 'the auto unlock time';

alter table user
    add login_fail_times int not null comment 'the login error times';

alter table user
    add valid_time datetime not null comment 'the user valid date';

