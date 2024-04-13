INSERT INTO users(first_name, last_name, email, phone_number, password)
VALUES ('Игорь', 'Закатов', 'zakatov@example.com', '+7 916 001-62-24', '$2a$10$sUwKJJN8drTgSwXQy6kCe.iItbpoiw3wVFKJpFk9YuE/3/GY9JHZO');

INSERT INTO passports(user_id, number, series, issue_date,
                      issue_place, first_name, last_name,
                      patronimic, department_code, birthday)
VALUES (1, '4123', '123456', '2022-02-14', 'Москвоская обл. г. Домодедово',
        'Игорь', 'Закатов', 'Сергеевич', '123-456', '2003-08-08');
