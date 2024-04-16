INSERT INTO users(email, phone_number, password)
VALUES ('zakatov@example.com', '+7 916 001-62-24', '$2a$10$sUwKJJN8drTgSwXQy6kCe.iItbpoiw3wVFKJpFk9YuE/3/GY9JHZO');

INSERT INTO passports(user_id, number, series, issue_date, first_name, last_name,
                      patronimic, department_code, birthday)
VALUES (1, '4123', '123456', '2022-02-14',
        'Игорь', 'Закатов', 'Сергеевич', '123-456', '2003-08-08');
