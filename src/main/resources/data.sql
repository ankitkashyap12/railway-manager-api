INSERT INTO Station_Details (station_name, station_code, division, longitude, latitude) VALUES ('London', 'LDS', 'London Division', -0.1278, 51.5074);
INSERT INTO Station_Details (station_name, station_code, division, longitude, latitude) VALUES ('Paris', 'PRS', 'Paris Division', 2.3522, 48.8566);
INSERT INTO Station_Details (station_name, station_code, division, longitude, latitude) VALUES ('Amsterdam', 'AMS', 'Amsterdam Division', 4.9041, 52.3676);
INSERT INTO Station_Details (station_name, station_code, division, longitude, latitude) VALUES ('Munich', 'MUC', 'Munich Division', 11.5820, 48.1351);

INSERT INTO USER_TABLE (user_id,first_Name,last_Name,email) VALUES (1111,'ANKIT','KASHYAP','ankit@xyz.com)');
INSERT INTO USER_TABLE (user_id,first_Name,last_Name,email) VALUES (1123,'SOMEONE','KASHYAP','ankit@xyz.com)');

INSERT INTO Train (train_id,train_name,train_number)  VALUES(4,'London-munich express','1122');
INSERT INTO Train (train_id, train_name, train_number) VALUES (1, 'High Speed Express', 'HSE123');
INSERT INTO Train (train_id, train_name, train_number) VALUES (2, 'InterCity', 'IC456');
INSERT INTO Train (train_id, train_name, train_number) VALUES (3, 'Regional Rail', 'RR789');

-- Adding seats to Train with train_id = 1
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (1, 1, '1A', 'A');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (2, 1, '1B', 'A');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (3, 1, '2A', 'B');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (4, 1, '2B', 'B');

-- Adding seats to Train with train_id = 2
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (5, 2, '1A', 'A');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (6, 2, '1B', 'A');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (7, 2, '2A', 'B');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (8, 2, '2B', 'B');

-- Adding seats to Train with train_id = 2
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (9, 4, '1A', 'A');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (10, 4, '1B', 'A');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (11, 4, '2A', 'B');
INSERT INTO Seat (seat_id, train_id, seat_number, section) VALUES (12, 4, '2B', 'B');