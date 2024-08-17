CREATE TABLE pending_users (
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    full_name TEXT NOT NULL,
    login TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    login_code TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);