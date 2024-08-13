CREATE TABLE login_codes (
    id UUID PRIMARY KEY NOT NULL,
    user_id UUID NOT NULL,
    code TEXT not null,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);