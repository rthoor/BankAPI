INSERT INTO account (number, beneficiary_name, balance, is_active, pin_code_hash) VALUES
(1000000, 'Arthur', 500, true, '$2a$10$lo/YBKIbnC6LXA0hEyO.2OO5R0YekacHjjMSxJlYANxbRTRfCM5Wm'), /*1234*/
(1000001, 'Partner', 1500, true, '$2a$10$GayoF9FQqKOiPnuPlq5XG.MmrYRRxHtL1d.NaO3pqypeX65cw8Lla'), /*1111*/
(1000002, 'Partner', 100, true, '$2a$10$GayoF9FQqKOiPnuPlq5XG.MmrYRRxHtL1d.NaO3pqypeX65cw8Lla'); /*1111*/

alter sequence number_sequence restart with 1000003;

INSERT INTO transaction_history (id, balance_before, balance_after, amount, date_time, is_successful, transaction_type, sender_number, receiver_number) VALUES
('4e39219c-2657-4a86-973d-124007950b1c', 0, 2000, 2000, '2023-08-18 16:58:17.982', true, 'DEPOSIT', null, 1000001),
('1e33ca85-ad41-44b9-b007-62b2644bd94f', 2000, 1500, 500, '2023-08-18 17:23:55.876', true, 'TRANSFER', 1000001, 1000000),
('b1468bfd-5918-4ee7-bd52-bae132b0285a', 0, 100, 100, '2023-08-19 11:58:17.982', true, 'DEPOSIT', null, 1000002);