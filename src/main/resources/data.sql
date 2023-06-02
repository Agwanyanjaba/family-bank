CREATE TABLE payments
(
    payment_id   VARCHAR(250) PRIMARY KEY,
    student_id   BIGINT,
    amount       DECIMAL(10, 2),
    payment_description VARCHAR(250),
    payment_method VARCHAR(50),
    payment_channel VARCHAR(100),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO payments (payment_id,student_id, amount,payment_description ,payment_method, payment_channel, payment_date)
VALUES ('9a1a9e2c-ffc3-11ed-be56-0242ac120001', 1, 1000.00, 'Tuition fee','Credit Card', 'Online Banking', CURRENT_TIMESTAMP),
       ('9a1a9e2c-ffc3-11ed-be56-0242ac120002', 2, 1500.00, 'Exam fee','Debit Card', 'Mobile Banking', CURRENT_TIMESTAMP),
       ('9a1a9e2c-ffc3-11ed-be56-0242ac120003',1, 2000.00, 'Tuition fee','Bank Transfer', 'OTC', CURRENT_TIMESTAMP);
