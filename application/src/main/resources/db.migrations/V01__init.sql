CREATE TABLE IF NOT EXISTS student (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL
);

INSERT INTO student (id, name, course)
VALUES
    (1, 'John Wick', 'ABC001'),
    (2, 'Skeletor', 'ABC002'),
    (3, 'Donatello', 'ABC003');
