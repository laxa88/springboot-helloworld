CREATE TABLE IF NOT EXISTS email (
    id SERIAL NOT NULL PRIMARY KEY,
    email varchar(200) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS student (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL,
    email_id SERIAL REFERENCES email(id)
);

INSERT INTO email (id, email)
VALUES
    (1, 'test1@example.com'),
    (2, 'test2@example.com'),
    (3, 'test3@example.com');

INSERT INTO student (id, name, course, email_id)
VALUES
    (1, 'John Wick', 'ABC001', 1),
    (2, 'Skeletor', 'ABC002', 2),
    (3, 'Donatello', 'ABC003', 3);
