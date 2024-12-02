MERGE INTO PUBLIC.GENRES
 KEY (NAME)
VALUES (1, 'Comedy'),
       (2, 'Drama'),
       (3, 'Cartoon'),
       (4, 'Thriller'),
       (5, 'Documentary'),
       (6, 'Action');
MERGE INTO PUBLIC.RATING 
 KEY(NAME) 
VALUES (1, 'G'),
       (2, 'PG'), 
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');
