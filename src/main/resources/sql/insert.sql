INSERT INTO article (name, body, preview_text)
VALUES
    ('Introduction to Machine Learning', 'Machine learning is a subset of artificial intelligence that focuses on the development of algorithms...', 'Learn the basics of machine learning in this introductory article.'),
    ('Data Visualization Techniques', 'Data visualization is the graphical representation of information and data. By using visual elements like charts, graphs, and maps, data visualization tools provide an accessible way...', 'Explore various data visualization techniques to make sense of complex data.'),
    ('Web Development Best Practices', 'Web development encompasses many different skills and disciplines in the production and maintenance of websites. The different areas of web design include web graphic design; interface design; authoring...', 'Discover the best practices for modern web development.');
INSERT INTO article (name, body, preview_text, is_approved, is_rejected)
VALUES
    ('Approved article name', 'Really good and long body of article with lots of text', 'Just small preview', true, false),
    ('NOTAPPROVED article second name', 'Really second better worse good and long body of article with lots of text', 'Just small second preview', true, false);

INSERT INTO tag (name)
VALUES
    ('Machine Learning'),
    ('Data Visualization'),
    ('Web Development'),
    ('Artificial Intelligence'),
    ('Frontend'),
    ('Backend');

INSERT INTO article_tag (article_id, tag_id)
VALUES
    (1, 1),
    (1, 4),
    (2, 2),
    (3, 3),
    (3, 5),
    (4, 2),
    (4, 6),
    (4, 5),
    (5, 1)
