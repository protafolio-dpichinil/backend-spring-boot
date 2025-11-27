-- file: src/main/resources/schema.sql
CREATE TABLE IF NOT EXISTS users (
    usr_id INT AUTO_INCREMENT PRIMARY KEY,
    usr_username VARCHAR(255) NOT NULL,
    usr_password VARCHAR(255) NOT NULL,
    usr_active BOOLEAN NOT NULL);