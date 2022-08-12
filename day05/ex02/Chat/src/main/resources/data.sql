INSERT INTO chat.user (login, password)
VALUES
    ('Bob', '000000'),
    ('Billy', '1200000'),
    ('Logan', '000000123'),
    ('Gachi', '000000'),
    ('Andrew', '000000');

INSERT INTO chat.room (name, owner)
VALUES
    ('Random', 1),
    ('General', 2),
    ('dungeon', 3),
    ('Lols', 4),
    ('NMA', 5);

INSERT INTO chat.message (author, room, text_message, timestamp)
VALUES
    (1, 1, 'kek', '1970-01-01 00:00:01'),
    (2, 3, 'lmao', '1970-01-01 00:00:01'),
    (5, 3, '300 bucks', '1970-01-01 00:00:02'),
    (4, 4, 'RIPPERONI', '1970-01-01 00:00:04'),
    (5, 5, 'NMA', '1970-01-01 00:00:05');