# LegacyBankingBackend
 
## Project
A banking backend that will be used to centeralize the connection between our assets and the different endpoints we have scattered across the globe. This source code is the connection to our banking assets that will be able in cloud using Amazon AWS techonology. 

## Purpose
Get hands on experiences working with programming in Java, while also acquire knowledge of how java works with microservices, docker, and frameworks such as Spring. 

## Source code used
- Java JDK 17 (compatible with amazoncoretto, openJDK, etc)
- Spring Framework (Boot) /w Maven Package manager

## Database used
- Relational Database: Postgres

## Cloud Infrastructure
- Amazon RDS service: Hosting our banking assets in EC2 instances
- Amazon EC2 service: Use to host this source in the cloud in combination with Dockerization. 

## End Goal

- Host backend in the cloud along with connection to our RDS EC2 instance. The source code will be dockerized and added to a Docker Swarm orchestration to allow for redundancy across increasing influx of interactions with our application. 
- All redudant EC2 instances will each have their own connection to the database, and each their own synchronized copy of the database.
