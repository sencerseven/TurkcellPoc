# Turkcell POC

### Features

- Management of menu contents
- Management of product content
- Logging of service requests

Spring security and OAuth2 mechanism has been installed on the system. Tokens are provided by JWT.
The user who authenticate to the system can perform Menu content management, Product content management.
The requests to the services are kept in the records collection of MongoDB.


### Technologies

Turkcell Poc project uses a number of open source technologies to work properly:

- [Java 11]
- [Spring Boot]
- [Multithread]
- [MongoDb]
- [Hibernate]
- [Posgredb]
- [Spring Security with JWT]
- [Redis]

### Installation

Maven command ,To create a jar file, we should clean and install it with maven command
```sh
mvn clean install
```

In the main directory of our project, we should use the following code to run environments(redis,mongodb)
```sh
docker-compose up
```

We run the following command in the root directory of our project so that the docker image is prepared.

By default, the Docker will expose port 8080, so change this within the
Dockerfile if necessary. When ready, simply use the Dockerfile to
build the image.

```sh
docker build -t turkcellmoc .
```

Once done, run the Docker image and map the port to whatever you wish on
your host. In this example, we simply map port 80 of the host to
port 8080 of the Docker (or whatever port was exposed in the Dockerfile)

We can use the following code as the last step to run our project.

```sh
docker run -d --name=turkcell -p 80:8080 turkcellmoc
```

Verify the deployment by navigating to your server address in
your preferred browser.

```sh
127.0.0.1:80
```

#### Request Example

To use Swagger, you can enter at the address below.
```sh
http://localhost:80/swagger-ui.html
```
#### Postman Collection Url

```sh
https://www.getpostman.com/collections/44e8eecafb9279ed96e2

```

#### Authentication - Get Token

Curl request
```sh
curl --location --request POST 'localhost:8080/oauth/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=turkcell' \
--data-urlencode 'password=123' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_secret=turkcell' \
--data-urlencode 'client_id=turkcell'
```
We will do the following http requests with the sample token.

## Menu
Check swagger for another menu related requests.
### Get Menu List

```sh
curl --location --request GET 'localhost:8080/api/menu/' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXNlcnZlciJdLCJ1c2VyX25hbWUiOiJ0dXJrY2VsbCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTYxNzkwMzM0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRUFETUlOIl0sImp0aSI6IktxdW41TVNFR1g3bDlVWTdFQTR2S3VrajVsST0iLCJjbGllbnRfaWQiOiJ0dXJrY2VsbCJ9.DVWA4taw6vnwPCrF5a7e-SWKPKMZosf3BQ7y7mniq3M'
```

### Product
Check swagger for another product related requests.
#### Get Product List

```sh
curl --location --request GET 'localhost:8080/api/product' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXNlcnZlciJdLCJ1c2VyX25hbWUiOiJ0dXJrY2VsbCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTYxNzkwMzM0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRUFETUlOIl0sImp0aSI6IktxdW41TVNFR1g3bDlVWTdFQTR2S3VrajVsST0iLCJjbGllbnRfaWQiOiJ0dXJrY2VsbCJ9.DVWA4taw6vnwPCrF5a7e-SWKPKMZosf3BQ7y7mniq3M'
```

#### Update Product Info
You can use it to update the short numbers field of gsm records in bulk.
The 4 digit short number will be determined automatically.

```sh
curl --location --request PUT 'localhost:8080/api/product/product-info' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyLXNlcnZlciJdLCJ1c2VyX25hbWUiOiJ0dXJrY2VsbCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTYxNzkwMzM0OCwiYXV0aG9yaXRpZXMiOlsiUk9MRUFETUlOIl0sImp0aSI6IktxdW41TVNFR1g3bDlVWTdFQTR2S3VrajVsST0iLCJjbGllbnRfaWQiOiJ0dXJrY2VsbCJ9.DVWA4taw6vnwPCrF5a7e-SWKPKMZosf3BQ7y7mniq3M' \
--data-raw '[
    5325000001,5325000002,5325000003,5325000004,5325000005,5325000006
]'
```




