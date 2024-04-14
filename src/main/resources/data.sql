insert into products (name, price, description, stock_quantity, created_at, updated_at) values ('후드티', 50000, '매일 입기 좋은 후드티', 5, '2024-03-01 12:00:00', '2024-03-01 12:00:00');
insert into products (name, price, description, stock_quantity, created_at, updated_at) values ('와이드 슬랙스', 38000, '편한 슬랙스', 10, '2024-03-02 12:00:00', '2024-03-02 12:00:00');
insert into products (name, price, description, stock_quantity, created_at, updated_at) values ('캡 모자', 20000, '챙이 넓으 모자', 3, '2024-04-01 12:00:00', '2024-04-01 12:00:00');
insert into products (name, price, description, stock_quantity, created_at, updated_at) values ('백팩', 60000, '아주 큰 백팩', 100, '2024-04-02 12:00:00', '2024-04-02 12:00:00');
insert into products (name, price, description, stock_quantity, created_at, updated_at) values ('후드집업', 25000, '기모 후드집업', 10, '2024-04-03 12:00:00', '2024-04-03 12:00:00');
insert into products (name, price, description, stock_quantity, created_at, updated_at) values ('반팔', 15000, '오버핏 반팔', 50, '2024-04-03 13:00:00', '2024-04-03 13:00:00');

insert into product_stocks (product_id, stock_quantity, created_at, updated_at) values (1, 5, '2024-03-01 12:00:00', '2024-03-01 12:00:00');
insert into product_stocks (product_id, stock_quantity, created_at, updated_at) values (2, 10, '2024-03-02 12:00:00', '2024-03-02 12:00:00');
insert into product_stocks (product_id, stock_quantity, created_at, updated_at) values (3, 3, '2024-04-01 12:00:00', '2024-04-01 12:00:00');
insert into product_stocks (product_id, stock_quantity, created_at, updated_at) values (4, 100, '2024-04-02 12:00:00', '2024-04-02 12:00:00');
insert into product_stocks (product_id, stock_quantity, created_at, updated_at) values (5, 10, '2024-04-03 12:00:00', '2024-04-03 12:00:00');
insert into product_stocks (product_id, stock_quantity, created_at, updated_at) values (6, 50, '2024-04-03 13:00:00', '2024-04-03 13:00:00');

insert into users (name, address, phone_number, point, created_at, updated_at) values ('홍길동', '서울시 송파구', '01012345678', 5000, '2024-04-08 12:00:00', '2024-04-08 12:00:00');

insert into orders (user_id, pay_amount, receiver_name, address, phone_number, order_status, ordered_at, created_at, updated_at) values (1, 126000, '홍길동', '서울시 송파구', '01012345678', 'PAID', '2024-04-09 20:00:00', '2024-04-09 20:00:00', '2024-04-09 20:00:00');
insert into orders (user_id, pay_amount, receiver_name, address, phone_number, order_status, ordered_at, created_at, updated_at) values (1, 70000, '김철수', '서울시 관악구', '01056781234', 'PAID', '2024-04-09 20:30:00', '2024-04-09 20:30:00', '2024-04-09 20:30:00');
insert into orders (user_id, pay_amount, receiver_name, address, phone_number, order_status, ordered_at, created_at, updated_at) values (1, 105000, '김짱구', '서울시 강남구', '01012341234', 'PAID', '2024-04-09 20:35:00', '2024-04-09 20:35:00', '2024-04-09 20:35:00');

insert into order_items (order_id, product_id, product_name, unit_price, total_price, quantity, created_at, updated_at) values (1, 1, '후드티', 50000, 50000, 1, '2024-04-09 20:00:00', '2024-04-09 20:00:00');
insert into order_items (order_id, product_id, product_name, unit_price, total_price, quantity, created_at, updated_at) values (1, 2, '와이드 슬랙스', 38000, 76000, 2, '2024-04-09 20:00:00', '2024-04-09 20:00:00');
insert into order_items (order_id, product_id, product_name, unit_price, total_price, quantity, created_at, updated_at) values (2, 3, '캡 모자', 20000, 20000, 1, '2024-04-09 20:30:00', '2024-04-09 20:30:00');
insert into order_items (order_id, product_id, product_name, unit_price, total_price, quantity, created_at, updated_at) values (2, 5, '후드집업', 25000, 50000, 2, '2024-04-09 20:30:00', '2024-04-09 20:30:00');
insert into order_items (order_id, product_id, product_name, unit_price, total_price, quantity, created_at, updated_at) values (3, 4, '백팩', 60000, 60000, 1, '2024-04-09 20:35:00', '2024-04-09 20:35:00');
insert into order_items (order_id, product_id, product_name, unit_price, total_price, quantity, created_at, updated_at) values (3, 6, '반팔', 15000, 45000, 3, '2024-04-09 20:35:00', '2024-04-09 20:35:00');

insert into payments (order_id, pay_amount, payment_method, created_at, updated_at) values (1, 126000, 'CARD', '2024-04-09 20:00:00', '2024-04-09 20:00:00');
insert into payments (order_id, pay_amount, payment_method, created_at, updated_at) values (2, 70000, 'CARD', '2024-04-09 20:30:00', '2024-04-09 20:30:00');
insert into payments (order_id, pay_amount, payment_method, created_at, updated_at) values (3, 105000, 'CARD', '2024-04-09 20:35:00', '2024-04-09 20:35:00');