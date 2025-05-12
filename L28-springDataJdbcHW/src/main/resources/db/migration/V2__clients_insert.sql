

Insert into client (name) values
('client1'),
('client2'),
('client3');

Insert into address (client_id, street) values
(1, 'street1'),
(2, 'street2'),
(3, 'street3');

Insert into phone (number, client_id) values
('111111111', 1),
('222222222', 2),
('333333333', 3),
('444444444', 1),
('555555555', 2),
('666666666', 3);