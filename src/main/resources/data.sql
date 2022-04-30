INSERT INTO USERS (name, email, password)
VALUES ('User0', 'user0@yandex.ru', '{noop}password0'),
       ('User1', 'user1@yandex.ru', '{noop}password1'),
       ('User2', 'user2@yandex.ru', '{noop}password2'),
       ('User3', 'user3@yandex.ru', '{noop}password3'),
       ('User4', 'user4@yandex.ru', '{noop}password4'),
       ('User5', 'user5@yandex.ru', '{noop}password5'),
       ('User6', 'user6@yandex.ru', '{noop}password6'),
       ('User7', 'user7@yandex.ru', '{noop}password7'),
       ('User8', 'user8@yandex.ru', '{noop}password8'),
       ('User9', 'user9@yandex.ru', '{noop}password9'),
       ('User10', 'user10@yandex.ru', '{noop}password10'),
       ('User11 без голосования', 'user11@yandex.ru', '{noop}password11'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6),
       ('USER', 7),
       ('USER', 8),
       ('USER', 9),
       ('USER', 10),
       ('USER', 11),
       ('USER', 12),
       ('ADMIN', 13);

INSERT INTO restaurant (name, address, contacts)
VALUES ('Ваcильки', 'г. Минск ул. Тимирязева 67', '8-029-7634349'),
       ('Пицца Темпо', 'г. Минск ул. Багратиона 81', '8-029-5882922'),
       ('Новый свет', 'г. Минск ул. Варшавская 81', '8-044-7324144'),
       ('Ресторан без меню', 'без адреса', 'без контактов');

INSERT INTO dish (name, description, weight, price, restaurant_id)
VALUES ('Мачанка с драниками',
        'драники, куриное филе, ветчина, морковь, лук, шампиньоны, сливочный соус с укропом', 290, 67, 1),
       ('Салат с курицей и помидорами',
        'листья салата, морковь, куриное филе, помидоры, огурцы, заправка для салата', 150, 32, 1),
       ('Куриный суп с рисом', 'картофель, куриное филе, лук, морковь, рис, зелень', 250, 31, 1),
       ('Салат с ветчиной и кукурузой',
        'листья салата, морковь, огурцы, ветчина, консервированная кукуруза, подаются с заправкой для салата', 145, 34,
        1),
       ('Биточки с грибным соусом',
        'биточки из свинины, подаются со сливочным соусом с шампиньонами, помидорами и зеленью, картофельное пюре',
        335, 75, 1),
       ('Еда не в меню', 'без описания', 290, 105, 1),
       ('Пицца Народная',
        'соус из протертых томатов, Моцарелла, ветчина, шампиньоны, маринованные огурцы, приправа к пицце, масло чесночное',
        290, 67, 2);

INSERT INTO menu (menu_date, restaurant_id)
VALUES (current_date - 1, 1),
       (current_date, 1),
       (current_date + 1, 1),
       (current_date + 2, 1),
       (current_date + 3, 1),
       (current_date + 4, 1);

INSERT INTO vote (date, user_id, restaurant_id)
VALUES (current_date - 1, 1, 1),
       (current_date - 1, 2, 1),
       (current_date - 1, 3, 1),
       (current_date - 1, 4, 2),
       (current_date - 1, 5, 2),
       (current_date - 1, 6, 2),
       (current_date - 1, 7, 2),
       (current_date - 1, 8, 2),
       (current_date - 1, 9, 2),
       (current_date - 1, 10, 2),
       (current_date - 1, 11, 2),
       (current_date, 1, 2),
       (current_date, 2, 2),
       (current_date, 3, 2),
       (current_date, 4, 1),
       (current_date, 5, 1),
       (current_date, 6, 1),
       (current_date, 7, 1),
       (current_date, 8, 1),
       (current_date, 9, 1),
       (current_date, 10, 1),
       (current_date, 11, 1);

INSERT INTO menu_dish (dish_id, menu_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (3, 2),
       (4, 2),
       (5, 2),
       (1, 6);

