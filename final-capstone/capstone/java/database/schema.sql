BEGIN TRANSACTION;

DROP TABLE IF EXISTS reading_activity;
DROP TABLE IF EXISTS user_book;
DROP TABLE IF EXISTS user_prize;
DROP TABLE IF EXISTS prize;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS family;

CREATE TABLE family (
    family_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    email_address VARCHAR(80)
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    family_id INT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(200) NOT NULL,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    CONSTRAINT fk_family FOREIGN KEY (family_id) REFERENCES family (family_id)
);

CREATE TABLE book (
    book_isbn VARCHAR(50) PRIMARY KEY,
    title VARCHAR(150),
    author VARCHAR(50),
    num_pages INT,
    publisher VARCHAR(150),
    cover_url VARCHAR(300)
);

CREATE TABLE user_book (
    user_id INT NOT NULL,
    book_isbn VARCHAR(50) NOT NULL,
    finished BOOLEAN,
    date_finished DATE,
    PRIMARY KEY (user_id, book_isbn),
    CONSTRAINT fk_user_book_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_user_book_isbn FOREIGN KEY (book_isbn) REFERENCES book (book_isbn)
);

CREATE TABLE reading_activity (
    activity_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    book_isbn VARCHAR(50) NOT NULL,
    minutes_read INT NOT NULL,
    format VARCHAR(50),
    notes VARCHAR(100),
    CONSTRAINT fk_read_user FOREIGN KEY (username) REFERENCES users (username),
    CONSTRAINT fk_read_book FOREIGN KEY (book_isbn) REFERENCES book (book_isbn)
);

CREATE TABLE prize (
    prize_id SERIAL PRIMARY KEY,
    user_id INT,
    family_id INT NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(250),
    milestone INT,
    user_group VARCHAR(50),
    start_date DATE DEFAULT CURRENT_DATE,
    end_date DATE DEFAULT '9999-12-31',
    CONSTRAINT fk_prize_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_prize_family_id FOREIGN KEY (family_id) REFERENCES family (family_id)
);

CREATE TABLE user_prize (
    user_prize_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    prize_id INT NOT NULL,
    won_date DATE,
    CONSTRAINT fk_user_prize_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_user_prize_prize_id FOREIGN KEY (prize_id) REFERENCES prize (prize_id)
);

COMMIT TRANSACTION;
