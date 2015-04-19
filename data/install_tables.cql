
CREATE KEYSPACE simplerpg
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

CREATE TABLE locations(
    name varchar,
    npcs list<varchar>,
    stores list<varchar>,
    places list<varchar>,
    PRIMARY KEY (name)
);

CREATE TABLE stores(
    name varchar,
    items map<varchar, int>,
    PRIMARY KEY(name)
);

CREATE TABLE items(
    name varchar,
    price double,
    strength_mod int,
    magic_mod int,
    stamina_mod int,
    protection_mod int,
    hit_speed double,
    PRIMARY KEY(name)
);