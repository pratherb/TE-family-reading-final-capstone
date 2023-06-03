BEGIN TRANSACTION;

DROP TABLE IF EXISTS reading_activity;
DROP TABLE IF EXISTS user_book;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS family;

CREATE TABLE family (
    family_id SERIAL,
    name varchar(50),
    email_address varchar(80),
    CONSTRAINT pk_family PRIMARY KEY (family_id)
);

CREATE TABLE users (
	user_id SERIAL,
	family_id int,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name varchar(50),
	last_name varchar(50),
	CONSTRAINT pk_user PRIMARY KEY (user_id),
	CONSTRAINT fk_family FOREIGN KEY (family_id) REFERENCES family (family_id)
);

CREATE TABLE book (
    --ISBN changed from int to varchar.
    -- Long ISBNs can exceed the limit for how big an int can be.
    book_isbn varchar(50) UNIQUE,
    title varchar(50),
    author varchar(50),
    num_pages int,
    CONSTRAINT pk_book PRIMARY KEY (book_isbn)
);

CREATE TABLE user_book (
    user_id int NOT NULL,
    book_isbn int NOT NULL,
    finished boolean,
    date_finished date,
    CONSTRAINT pk_book_user PRIMARY KEY (user_id, book_isbn),
    CONSTRAINT fk_user_book_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    -- Removing this constraint for now. Might cause issues when a user wants to remove a book,
    -- while it exists in another user's list.
    -- -Vince
    --CONSTRAINT fk_user_book_isbn FOREIGN KEY (book_isbn) REFERENCES book (book_isbn)
);

CREATE TABLE reading_activity (
    activity_id SERIAL,
    user_id int NOT NULL,
    book_isbn int NOT NULL,
    minutes_read int NOT NULL,
    format varchar(50),
    notes varchar(100),
    CONSTRAINT pk_read PRIMARY KEY (activity_id),
    CONSTRAINT fk_read_user FOREIGN KEY (user_id) REFERENCES users (user_id),
    -- Removing this constraint for similar reasons.
    --CONSTRAINT fk_read_book FOREIGN KEY (book_isbn) REFERENCES book (book_isbn)
);

COMMIT TRANSACTION;
