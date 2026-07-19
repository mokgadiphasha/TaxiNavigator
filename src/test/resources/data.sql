INSERT INTO taxi_signs (sign_description)
VALUES ('Hold up two fingers please');
INSERT INTO taxi_signs (sign_description)
VALUES ('Hold up three fingers please');


INSERT INTO taxi_ranks (location_name, location_address)
VALUES ('Sesame Corner', '145 Trent street Johannesburg');
INSERT INTO taxi_ranks (location_name, location_address)
VALUES ('Flowerina Store', '111 Rosebank Johannesburg');
INSERT INTO taxi_ranks (location_name, location_address)
VALUES ('Sunward Park', '900 Boksburg Johannesburg');
INSERT INTO taxi_ranks (location_name, location_address)
VALUES ('East Rand Mall', '780 Naledi street Boksburg');
INSERT INTO taxi_ranks (location_name, location_address)
VALUES ('West Central Road', '245 West Central Road Dawn Park');
INSERT INTO taxi_ranks (location_name, location_address)
VALUES ('MTN Taxi Rank', '355 Plain Street Johannesburg');


INSERT INTO taxi_routes
(from_location, to_location, fare, taxi_sign_id, from_location_rank_id, to_location_rank_id)
VALUES
    ('MTN Taxi Rank', 'Flowerina Store', 150.00, 1, 6, 2);
INSERT INTO taxi_routes
(from_location, to_location, fare, taxi_sign_id, from_location_rank_id, to_location_rank_id)
VALUES
    ('East Rand Mall', 'West Central Road', 50.00, 2, 4, 5);
INSERT INTO taxi_routes
(from_location, to_location, fare, taxi_sign_id, from_location_rank_id, to_location_rank_id)
VALUES
    ('East Rand Mall', 'MTN Taxi Rank', 90.00, 2, 4, 6);