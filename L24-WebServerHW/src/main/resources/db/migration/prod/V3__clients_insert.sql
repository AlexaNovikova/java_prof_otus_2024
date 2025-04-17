Insert into address (street) values
('street1'),
('street2'),
('street3');

Insert into client (name, address_id) values
('client1', 1),
('client2', 2),
('client3', 3);

Insert into phone (number, client_id) values
('111111111', 1),
('222222222', 2),
('333333333', 3),
('444444444', 1),
('555555555', 2),
('666666666', 3);