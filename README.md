# Project Booking Managment System

### Install Instruction
Use Jar  

- download from: https://github.com/fisharman/ProjectorMgmt/releases  
- then start the service in command line with
```
java -jar ProjectorMgmt_v1.jar
```

OR

Build it yourself
- Clone repo
- Build using included build.gradle and /gradle/wrapper

Then send HTTP requests to localhost:8080 endpoints listed below

### API Endpoints

Specification: http://jsonapi.org/format/

#### GET /projectors

Response Envelope

```JSON
{
    "data" : [{
        "type": "projector",
        "id": "1"
        "attributes": {
            "name": "P1"
        }
    }]
}
```

Curl test command
```Shell
curl -X GET \
  http://localhost:8080/projectors
```


#### POST /reservation


Request Envelope
```JSON
{  
    "data": {
        "type": "reservation",
        "attributes": {
            "date": "2018-05-01",
            "startTime": "12:10",
            "endTime": "13:00"
        }
    }
}
```


Response Envelope (201)  
***note: time in 24 hour format. UTC would be a better implementation...***
```JSON
{
    "data": {
        "type": "reservation_success",
        "id": "11",
        "name": "P1",
        "date": "2018-05-03",
        "startTime": "12:10",
        "endTime": "13:00"
    }
}
```

Reservation Full (500)
```JSON
{
    "error": "Reservation Full"
}
```

***note: feature to return alternate availability not implemented***

Curl Test Command
```Shell
curl -X POST \
  http://localhost:8080/reservation \
  -H 'Content-Type: application/json' \
  -d '{ "data": { "type": "reservation", "attributes":
  { "date": "2018-05-03", "startTime": "12:10", "endTime": "13:00" } } }'
```

#### GET /reservation/:id

Response Envelope
```
{
    "data": {
        "name": "P2",
        "date": "2018-05-03",
        "startTime": "12:10",
        "endTime": "13:00"
    }
}
```

Curl Test Command
```Shell
curl -X GET \
  http://localhost:8080/reservation/1 \
```

#### DELETE /reservation/:id

Response:
Success No-Content (204)
