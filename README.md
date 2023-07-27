# Database Structure
```SQL
create table measurement
(
    id          INTEGER,
    board_name  TEXT,
    uid         TEXT,
    timestamp   INTEGER,
    temperature REAL,
    humidity    REAL,
    pressure    REAL,
    luminance   INTEGER,
    moisture_a  INTEGER,
    moisture_b  INTEGER,
    moisture_c  INTEGER,
    voltage     REAL,
    PRIMARY KEY (id)
);
```