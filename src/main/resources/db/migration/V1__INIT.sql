create table article
(
    is_approved   boolean,
    is_rejected   boolean,
    id            bigserial not null,
    last_modified timestamp(6),
    body          text,
    name          varchar(255),
    preview_text  varchar(255),
    primary key (id)
);
create table article_tag
(
    article_id bigint not null,
    tag_id     bigint not null,
    primary key (article_id, tag_id)
);
create table role
(
    id        bigserial not null,
    role_name text      not null,
    primary key (id)
);
create table tag
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);
create table "user"
(
    id       bigserial not null,
    name     text      not null,
    password text      not null,
    username text      not null unique,
    primary key (id)
);
create table users_roles
(
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id)
);
alter table if exists article_tag
    add constraint article_tag_tag_fk foreign key (tag_id) references tag;
alter table if exists article_tag
    add constraint article_tag_article_fk foreign key (article_id) references article;
alter table if exists users_roles
    add constraint user_roles_role_fk foreign key (role_id) references role;
alter table if exists users_roles
    add constraint user_roles_user_fk foreign key (user_id) references "user";