INSERT INTO contact_person (name, surname)
VALUES ('AdminName', 'AdminSurname');
INSERT INTO customer (bank_details, phone, email, password, contact_person_id, is_admin)
VALUES ('admin', 'adminPhone', 'admin@email.com', '13I3eYStTKBw01Yhz8qByW8tCe4o1yosaR0R8/AuKYA=', 1, TRUE);


INSERT INTO agent (agent_name, agent_surname, commission_rate)
VALUES ('Микола', 'Парасюк', 10.5),
       ('Ангеліна', 'Мовчан', 15.0),
       ('Володимир', 'Зеленський', 8.75),
       ('Олексій', 'Притула', 12.25),
       ('Єлизавета', 'Кулій', 18.0);

INSERT INTO program (program_name, rating, rate, ad_limit_per_day)
VALUES
    ('Світ навиворіт', 80.5, 870.0, 3),
    ('Реальна містика', 75.0, 325.0, 2),
    ('Хто зверху', 90.0, 1000.0, 5),
    ('Говорить Україна', 95.0, 1500.0, 1),
    ('Кохана, ми вбиваємо дітей', 85.5, 900.0, 4);


SELECT * FROM agent;
SELECT * FROM contact_person;
SELECT * FROM commercial;
SELECT * FROM contract;
SELECT  * FROM program;
SELECT * FROM customer;








