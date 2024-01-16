CREATE SCHEMA IF NOT EXISTS TVAdManagement;
use TVAdManagement;

CREATE TABLE IF NOT EXISTS contact_person
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    lastname VARCHAR(100)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS customer
(
    customer_id       INT PRIMARY KEY AUTO_INCREMENT,
    bank_details      VARCHAR(255) NOT NULL UNIQUE, -- Якщо замовник зареєстрований, то може увійти за ЄДРПОУ
    email             VARCHAR(255) NOT NULL UNIQUE, -- та електронною адресою як логін
    phone             VARCHAR(20)  NOT NULL,
    password          VARCHAR(255) NOT NULL UNIQUE,
    contact_person_id INT          NOT NULL,
    is_admin          BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (contact_person_id) REFERENCES contact_person (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = INNODB;


CREATE TABLE IF NOT EXISTS program
(
    program_id   INT PRIMARY KEY AUTO_INCREMENT,
    program_name VARCHAR(255) NOT NULL,
    rating       DECIMAL(5,2) NOT NULL,
    rate      DECIMAL(10,2)       NOT NULL, -- Вартість секунди реклами в передачі
    ad_limit_per_day INT NOT NULL,
    CHECK (ad_limit_per_day BETWEEN 0 AND 10),
    CHECK (rating BETWEEN 0 AND 100),
    CHECK (rate BETWEEN 200 AND 10000)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS commercial
(
    commercial_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id   INT  NOT NULL,
    program_id    INT  NOT NULL,
    air_date      DATE NOT NULL,
    duration      INT NOT NULL,
    CHECK (duration BETWEEN 5 AND 60),
    FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
        ON DELETE CASCADE,
    FOREIGN KEY (program_id) REFERENCES program (program_id)
        ON DELETE CASCADE
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS agent
(
    agent_id          INT PRIMARY KEY AUTO_INCREMENT,
    agent_name        VARCHAR(35) NOT NULL,
    agent_surname     VARCHAR(50) NOT NULL,
    commission_rate   DECIMAL(5, 2) NOT NULL, -- Відсоток від реклами
    total_revenue     DECIMAL(10, 2) DEFAULT 0, -- New column for total revenue
    CHECK (commission_rate BETWEEN 0 AND 25)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS contract
(
    contract_id     INT PRIMARY KEY AUTO_INCREMENT,
    agent_id        INT NOT NULL,
    commercial_id   INT NOT NULL UNIQUE,
    contract_amount DECIMAL, -- вартість реклами
    CHECK (contract_amount > 0),
    FOREIGN KEY (agent_id) REFERENCES agent (agent_id)
        ON DELETE CASCADE,
    FOREIGN KEY (commercial_id) REFERENCES commercial (commercial_id)
        ON DELETE CASCADE
) ENGINE = INNODB;

drop database TVAdManagement;


