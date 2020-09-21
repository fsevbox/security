#mutual-tls

Run `server` by `mvn spring-boot:run`
* it will start an embedded tomcat on port 8443
* exposes a /ping endpoint which returns "pong" 

Run `client` by `mvn spring-boot:run`
* it will send a single http request to server's /ping endpoint and print out the response