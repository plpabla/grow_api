# Endpoints
API enpoints accepts JSON format (`application/json`)
* `/measurement` 
  * `GET` get list of all measurements (filtering to be added)
  * `POST` add a mesaurement. Message structure
```json
{
  "nickname": "weather-test", 
  "model": "grow",
  "uid": "e6614c775b8c4035", 
  "timestamp": "2022-09-04T10:40:24Z", 
  "readings": {
    "temperature": 27.57,   
    "humidity": 49.33, 
    "pressure": 996.22, 
    "light": 0.41, 
    "moisture_1": 0.0, 
    "moisture_2": 0.0, 
    "moisture_3": 0.0, 
    "voltage": 4.954
  }
}
```

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