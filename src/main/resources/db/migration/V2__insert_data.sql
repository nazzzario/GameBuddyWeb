INSERT INTO users (email, login, password, role, user_status)
VALUES ('admin@gamebuddy.com',
        'admin',
        '$2a$12$eh5JbGhP0lW6ys/nB1.6Yutn.z2ymZGfmO5YUj.DOwR.Eft8b98tC',
        'ROLE_SUPERUSER',
        'ACTIVE');

INSERT INTO genres (genre_name)
VALUES ('Shooter'),
       ('Racing'),
       ('Battle Royal'),
       ('Fighting'),
       ('Survive'),
       ('Horror'),
       ('Other');

INSERT INTO games (game_logo, max_players, name, genre_id)
VALUES ('apex_logo', 3, 'Apex Legends', 3),
       ('nfs', 4, 'Need For Speed', 2),
       ('cod', 4, 'Call Of Duty', 1),
       ('battlefield', 64, 'Battlefield', 1),
       ('mk', 2, 'Mortal Kombat', 4),
       ('rust', 10, 'Rust', 5),
       ('dbd', 5, 'Dead by Daylight', 6),
       ('mafia_logo', 11, 'Mafia', 7);


