CREATE TABLE Users (
        id INTEGER IDENTITY,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL
);

CREATE TABLE Tokens (
        id INTEGER IDENTITY,
        userId INTEGER NOT NULL,
        token VARCHAR(255) NOT NULL,
        expirationDate TIMESTAMP DEFAULT NOW
);