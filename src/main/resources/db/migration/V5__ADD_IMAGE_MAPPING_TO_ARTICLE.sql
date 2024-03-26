CREATE TABLE article_images (
    article_id bigint,
    image_filename varchar(255),
    foreign key (article_id) references article(id)
);