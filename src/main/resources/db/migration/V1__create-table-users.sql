CREATE TABLE users (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    full_name TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    about TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL
);