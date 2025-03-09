-- Inserción del usuario único
INSERT INTO users (id, username, password, active) 
VALUES (1, 'usuario', 'password', true);

-- Inserción de la categoría "ninguna" asociada al usuario con id 1
INSERT INTO categories (id, name, user_id) 
VALUES (1, 'ninguna', 1);

-- Inserción de 10 notas asociadas al usuario 1 y a la categoría "ninguna"
INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (1, 'Nota 1', 'Texto de la nota 1', true, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (2, 'Nota 2', 'Texto de la nota 2', true, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (3, 'Nota 3', 'Texto de la nota 3', true, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (4, 'Nota 4', 'Texto de la nota 4', false, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (5, 'Nota 5', 'Texto de la nota 5', false, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (6, 'Nota 6', 'Texto de la nota 6', false, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (7, 'Nota 7', 'Texto de la nota 7', false, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (8, 'Nota 8', 'Texto de la nota 8', false, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (9, 'Nota 9', 'Texto de la nota 9', false, 1, 1);

INSERT INTO notes (id, title, text, archived, user_id, category_id) 
VALUES (10, 'Nota 10', 'Texto de la nota 10', false, 1, 1);

