
CREATE SEQUENCE user_id_seq START 1;

CREATE TABLE users (
    id INTEGER PRIMARY KEY DEFAULT nextval('user_id_seq'),
    login VARCHAR(255),
    password_hash VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL
);

CREATE SEQUENCE route_id_seq START 1;
CREATE SEQUENCE location_id_seq START 1;
CREATE SEQUENCE coordinates_id_seq START 1;

CREATE TABLE Location (
    location_id INTEGER PRIMARY KEY DEFAULT nextval('location_id_seq'),
    x BIGINT NOT NULL,
    y REAL NOT NULL,
    z REAL NOT NULL,
    name TEXT NOT NULL
);

CREATE TABLE Coordinates (
    coordinates_id INTEGER PRIMARY KEY DEFAULT nextval('coordinates_id_seq'),
    x REAL NOT NULL,
    y REAL NOT NULL
);

CREATE TABLE Route (
    id INTEGER PRIMARY KEY DEFAULT nextval('route_id_seq'),
    name TEXT NOT NULL CHECK (name <> ''),
    coordinate INTEGER REFERENCES Coordinates(coordinates_id),
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    from_location_id INTEGER REFERENCES Location(location_id),
    to_location_id INTEGER REFERENCES Location(location_id),
    distance DOUBLE PRECISION NOT NULL CHECK (distance > 1),
    owner_login VARCHAR(255)
);

ALTER SEQUENCE location_id_seq OWNED BY Location.location_id;
ALTER SEQUENCE coordinates_id_seq OWNED BY Coordinates.coordinates_id;
ALTER SEQUENCE route_id_seq OWNED BY Route.id;
