bayes-dota
==========

This is the [task](TASK.md).

Any additional information about your solution goes here.

Local testing and development
----
This application is using Postgres DB for storing log data. You can start a Docker Postgres container at localhost 
(port 5432) by launching the [docker compose](src/main/resources/docker/docker-compose.yml) file in the main resources 
for local development or the  [docker compose](src/test/resources/docker/docker-compose.yml) file in the test resources
for testing. 

Please note that there is a predefined docker compose maven plugin in the pom.xml which starts the Postgres container 
for testing before the integration test phase. If the local `5432` port is already in use this will cause the tests failing 
as the container will not be able to start.

For local development please start the application with "dev" profile by adding `-Dspring.profiles.active=dev` VM option.

Changes in pom
----
- Changed the predefined h2 database dependency to postgres as I prefer postgres and according to the task specification it was an option.
- Changed the `java.version` property to 11 from 1.8.
- Deleted the JUnit exclusion from `spring-boot-starter-test` as I needed the org.junit.runner.RunWith annotation for the integration test (maybe it was not the best option).
- Configured `docker-compose-maven-plugin` for integration testing.

Important notes
----
I know this is just a coding challenge, please handle the following notes on issues and improvements as a collection of
"_what if it would be a production code_" thought experiments.

Known issues & limitations
----
###Hibernate auto DDL generation
In production environment auto schema generation should not be used as it may have unforeseen side effects. In a production ready code 
a migration tool like Flyway would be useful. A simple Flyway configuration doesn't involve too much overhaed and makes the deployment process less stressful.

###ID generation
 As different auto ID generation strategies could cause issues (like incremental sequences in Postgres, DB locks, etc) I preferred to use custom 
 ID generations. For the log entities I used a Hibernate built-in feature to generate a UUID, but this was not an option for the Match entity as 
 it's value is predefined (Long). For the match entity I created a custom  [ID generator](src/main/java/gg/bayes/challenge/repository/MatchIdGenerator.java) 
 based on a random UUID and the current time in milliseconds.Both of these options have problems in terms of scaling.
 - The Hibernate UUID generation is based on the IP address of the running machine and a timestamp. In this case we need to make sure that none of the running 
 instances are sharing the same IP, otherwise it could cause constraint violations.
 - My custom generator is based on a random UUID and a timestamp. Theoritically, 2 different service instances could generate the same UUID (if they are running on different JVMs)
 at the exact same time. This could also cause constraint violation.
 
###Idempotency
As the processed logfile does not have any unique ID we can't decide if it has been already processed before or not. 
It means that we can process and save the same match data under different ID over and over and over again. In a nutshell,
the service is not idempotent.

###File size
As we are processing a whole text file as a String it could cause some issues if the text is too long.
We need to take care of the limitations of the String class, the character quantity could not be more than the max int value,
and the String could not be bigger than half of the heap size.

###Unprotected endpoints
The endpoints do not have any sort of authorization/authentication mechanism.

###Indices
Currently, there are no indices in the DB which could cause performance issues over time. The queries are fast enough 
now, but just because we are working on a mini dataset.

Further improvements
----
###Usage of Kafka
As I see most of the problems are caused because of the way how we consume the log entries. It affects the ID generation, harms idempotency
and involves filesize limitations. On the long term I would implement a functionality on the sender side to preprocess 
the log file, map it to a model object (possible use of a model contract library), enrich them with unique IDs and send them 
one by one on a Kafka topic. This service would only be responsible for consuming the messages from Kafka and persist them.

###API service separation
This service is responsible for processing, persisting and serving the log entries on Rest endpoints. Basically a bottleneck
and could cause problems if we want to scale it up.
Maybe it would be better to separate the API capabilities to a different service.

###DB migration tool
As I mentioned earlier it would be better to use some sort of DB migration tool, like Flyway. This way it would be much easier to prevent deployment issues
and implement DB changes and improvements.

###Swagger enrichment
I didn't have time for it, but a more detailed explanation on the endpoints would be nice and useful for the calling clients.

###Containerization
Would be nice to add a dockerfile, use a maven docker plugin or include a CI containerization possibility.

