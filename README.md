## Projects

- application
- library
- myspringboot

`myspringboot` demonstrates SpringBoot app with a basic controller-service-datasource layer. There are two example datasource layers: Mocked, and network. The purpose of this project is to demonstrate different ways to write Controller, Service and Datasource tests.

`library` is a dummy submodule within this project, to test that it can be used as an external library. This is demonstrated in `application` HomeController, which displays the message in `/hello` endpoint.

`application` is another SpringBoot app with controller-service-datasource that includes an actual database. This project requires the postgres to be run in docker, via `docker-compose up`. The `db.migrations` folder contains Flyway SQL scripts that will automatically run to inject initial data.

### Setup

- Run `docker-compose up` to spin up a Postgres DB, which is used by `application`.
- The SpringBoot app relies on `application.yml` for the DB config (running on port `1234`).
- Run `DemoApplication.kt` to start the server.

## Tutorial Reference

Thenewboston tutorial series:

* https://www.youtube.com/watch?v=TJcshrJOnsE&list=PL6gx4Cwl9DGDPsneZWaOFg0H2wsundyGr

Code with Arho:

* https://www.arhohuttunen.com/spring-boot-unit-testing/
* https://www.youtube.com/watch?v=Ae5ukd136pc (Part 1)

Reactive programming with Spring Boot / Webflux

* https://www.youtube.com/watch?v=IK26KdGRl48&list=PLnXn1AViWyL70R5GuXt_nIDZytYBnvBdd&index=1
* https://www.youtube.com/watch?v=qwF6v6FN_Uc&t=145s (Getting started with R2DBC)

R2DBC / Database

* https://thomasandolf.medium.com/r2dbc-getting-started-d0afcfc05be2
* https://wkrzywiec.medium.com/database-in-a-docker-container-how-to-start-and-whats-it-about-5e3ceea77e50

Misc

* https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.testing.spring-boot-applications
* https://mister11.github.io/posts/testing_spring_webflux_application/
* https://howtodoinjava.com/spring-webflux/webfluxtest-with-webtestclient/

## Todos

- [ ] Fix multi-module config (doesn't work for new devs?)
  - https://github.com/mrclrchtr/gradle-kotlin-spring
  - https://github.com/emmapatterson/multi-module-spring-boot
- [ ] Setup database layer
- [ ] Test database layer
- [ ] Containerize app and database
- [ ] Test Webflux web layer
- [x] Setup multiple module projects (https://spring.io/guides/gs/multi-module/)
- [ ] Dev and prod configurations
- [ ] Deploy via CI
- [ ] Environment variables in local / CI