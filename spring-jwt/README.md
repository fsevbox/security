#spring-jwt

Spring boot security with JWT

##### 1. signup
```
curl --location --request POST 'localhost:8080/signup' \
--header 'Content-Type: application/json' \
--data-raw '{"username" : "tom", "password": "123"}'
```

##### 2. authenticate
```
curl --location --request POST 'localhost:8080/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{"username" : "tom", "password": "123"}'
```

##### 3. call secured endpoint
```
curl --location --request GET 'localhost:8080/ping' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0b20iLCJleHAiOjE2MDAzNDYyMzQsImlhdCI6MTYwMDM0NjE3NH0.A086Dgp9siISFhXD15FploQEqm1mRRnOTh44h3ZXEvvYmo1dDrV-l48piQhvpfGB4xs5wLfg311fq1lqQBgi0A'
```