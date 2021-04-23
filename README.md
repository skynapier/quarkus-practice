# time-converter project

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/time-converter-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Postgresql DB
configuration file in docker-compose.yml

``` 
docker-compose -f docker-compose.yaml up
docker exec -it pg_container bash
psql --host=pg_container --dbname=test_db --username=root
docker-compose -f docker-compose.yaml down
```


## Provided examples

### The Whole API Outlook
![](examples/api-outlook.png)


1.  Get All Record from 'resources/timezone.csv'
![](examples/get-all.png)
    Responses:
    ![](examples/get-all-responses.png)
    
2. Get Record by id
   Responses:
   ![](examples/get-by-id.png)
   
3. API Time Converter
   Responses:
   ![](examples/api-time-converter.png)
    