create table if not exists genres
(
    id         bigint generated by default as identity
        constraint genres_pkey primary key,
    genre_name varchar(255) not null
        constraint uk_genres_genre_name unique
);

create table if not exists games
(
    id          bigint generated by default as identity
        constraint games_pkey primary key,
    game_logo   varchar(255),
    max_players integer,
    name        varchar(255) not null
        constraint uk_games_game_name unique,
    genre_id    bigint       not null
        constraint fk__games_genres references genres
);
create table if not exists users
(
    id          bigint generated by default as identity
        constraint users_pkey primary key,
    email       varchar(255) not null
        constraint uk_users_email unique,
    login       varchar(255) not null
        constraint uk_users_login unique,
    password    varchar(255) not null,
    role        varchar(255),
    user_status varchar(255)
);

create table if not exists lobbies
(
    id             bigint generated by default as identity
        constraint lobbies_pkey primary key,
    amount_players integer not null,
    description    varchar(255),
    lobby_name     varchar(255),
    game_id        bigint
        constraint fk_lobbies_games references games,
    owner_id       bigint
        constraint fk_lobbies_users references users
);

create table if not exists lobbies_joined_users
(
    lobby_id        bigint not null
        constraint fk_lobbies_joined_users_lobbies references lobbies,
    joined_users_id bigint not null
        constraint fk_lobbies_joined_users_users references users,
    constraint lobbies_joined_users_pkey primary key (lobby_id, joined_users_id)
);


