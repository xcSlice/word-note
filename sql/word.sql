create table user(
    id bigint not null comment '用户id',
    username varchar(20) not null comment '用户名',
    password varchar(64) not null comment '用户密码',
    role varchar(20) default 'USER' comment '角色 USER ADMIN SUPER',
    is_delete tinyint default 0 comment '逻辑删除',
    gmt_create datetime comment '创建日期',
    gmt_modified datetime comment '修改日期',
    primary key (id)
)comment '用户表';