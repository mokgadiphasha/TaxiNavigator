
-- Taxi Signs Table
CREATE TABLE IF NOT EXISTS "taxi_signs" (
    id SERIAL PRIMARY KEY,
    sign_description VARCHAR(255) NOT NULL
);

-- Taxi Ranks Table
CREATE TABLE IF NOT EXISTS "taxi_ranks" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Taxi Routes Table
CREATE TABLE IF NOT EXISTS "taxi_routes" (
    id SERIAL PRIMARY KEY,
    from_location VARCHAR(255) NOT NULL,
    to_location VARCHAR(255) NOT NULL,
    fare DECIMAL(10,2) NOT NULL,
    taxi_sign_id INT REFERENCES taxi_signs(id),
    rank_id INT REFERENCES taxi_ranks(id)
);