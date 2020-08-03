# Set up DB
1. Create schema with name: workdb
2. Config mySQL at "application.properties" file
3. Run SpringApplication at file DemoApplication.java  

# API
1. Method → GET : URL → http://localhost:8080/api/v1/works
2. Method → POST : URL → http://localhost:8080/api/v1/works
  2.1: Body JSON Format : {
                            "name": "work_01",
                            "startingDate": "2020-08-01",
                            "endingDate": "2020-08-10",
                            "status": "2"
                          }
3. Method -> PUT: http://localhost:8080/api/v1/works/1
  3.1: Body JSON Format: {
                            "name": "work_02",
                            "startingDate": "2020-08-01",
                            "endingDate": "2020-08-10",
                            "status": "2"
                          }
4. Get By work ID -> GET URL -> http://localhost:8080/api/v1/works/1
5. Method -> DELETE : URL -> http://localhost:8080/api/v1/works/1
