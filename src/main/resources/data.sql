INSERT INTO users(email, phone_number, password)
VALUES ('zakatov@example.com', '79160016224', '$2a$10$sUwKJJN8drTgSwXQy6kCe.iItbpoiw3wVFKJpFk9YuE/3/GY9JHZO'),
       ('ivanov@example.com', '79998009990', '$2a$10$yECKmUbTvNBXHcHwITqBJezdQHvhsre4YWccyaRcCq8ly05im4QY2');

INSERT INTO passports(user_id, series, number, issue_date, first_name, last_name,
                      patronimic, department_code, birthday)
VALUES (1, '4123', '123456', '2022-02-14',
        'Игорь', 'Закатов', 'Сергеевич', '123-456', '2003-08-08'),
       (2, '8091', '123456', '2023-07-02',
        'Александр', 'Иванов', 'Владимироваич', '500-216', '2000-11-15');

INSERT INTO operation_categories(name)
VALUES ('Перевод'),
       ('Снятие'),
       ('Пополнение');
