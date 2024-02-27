create table article_image
(
    id            bigserial not null,
    image bytea,
    article_id bigint,
    primary key (id)
);