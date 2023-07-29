# Endpoints
API enpoints accepts JSON format (`application/json`)
* `/measurement` 
  * `GET` get list of all measurements (filtering to be added)
  * `POST` add a mesaurement. Message structure
```json
{
  "readings": {
    "pressure": 981.65,
    "temperature": 29.5,
    "voltage": 3.14,
    "humidity": 47.58,
    "moisture_a": 42.9,
    "moisture_b": 80,
    "moisture_c": 10.99,
    "luminance": 0.47
  },
  "nickname": "top-shelf",
  "model": "grow",
  "uid": "e6614864d3652e34",
  "timestamp": "2023-07-29T09:55:20Z"
}
```

# Use with Docker
Build
```
docker image build -t grow_api .
```

Run container
```
docker container run -p 8083:8080 -d grow_api
```

It will copy empty database, but you can also bind existing db using following syntax
```
source="$(pwd)/data"
dest=/app/data

docker container run --mount type=bind,source=$source,target=$dest -p 8083:8080 -d grow_api
```

Note: you can also use published image
```
docker container run --mount type=bind,source=$source,target=$dest -p 8083:8080 -d plpabla/grow_api:latest
```

# Database Structure
```SQL
create table measurement
(
  id          INTEGER
    primary key autoincrement,
  board_name  TEXT,
  uid         TEXT,
  timestamp   INTEGER,
  temperature REAL,
  humidity    REAL,
  pressure    REAL,
  luminance   REAL,
  moisture_a  REAL,
  moisture_b  REAL,
  moisture_c  REAL,
  voltage     REAL
);

```
