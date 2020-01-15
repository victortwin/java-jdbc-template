SELECT * FROM bids;
SELECT * FROM items;
SELECT * FROM users;

INSERT INTO users VALUES (1, 'address1', 'VASYA1', 'VASYALogin1', 'VASYAPass1');
INSERT INTO users VALUES (2, 'address2', 'VASYA2', 'VASYALogin2', 'VASYAPass2');

INSERT INTO items VALUES (1, 1,false,'description1','2004-12-31', 100,'2004-12-31','title1',1);
INSERT INTO items VALUES (2, 2,false,'description2','2004-12-31', 100,'2004-12-31','title2',1);
INSERT INTO items VALUES (3, 3,false,'description3','2004-12-31', 100,'2004-12-31','title3',2);

INSERT INTO bids VALUES (1, '2004-12-31', 10, 1, 1);
INSERT INTO bids VALUES (2, '2004-12-31', 20, 2, 1);
INSERT INTO bids VALUES (3, '2004-12-31', 30, 3, 2);


