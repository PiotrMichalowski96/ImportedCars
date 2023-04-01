# Imported cars service
[![<PiotrMichalowski96>](https://circleci.com/gh/PiotrMichalowski96/ImportedCars.svg?style=svg)](https://circleci.com/gh/PiotrMichalowski96/ImportedCars)

[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=PiotrMichalowski96_ImportedCars)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=PiotrMichalowski96_ImportedCars&metric=bugs)](https://sonarcloud.io/dashboard?id=PiotrMichalowski96_ImportedCars)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=PiotrMichalowski96_ImportedCars&metric=coverage)](https://sonarcloud.io/dashboard?id=PiotrMichalowski96_ImportedCars)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=PiotrMichalowski96_ImportedCars&metric=ncloc)](https://sonarcloud.io/dashboard?id=PiotrMichalowski96_ImportedCars)

Camel route service that allows to generate reports with recently registered cars in Poland.
## Table of contents
* [Overview](#Overview)
* [Technologies](#Technologies)

## Overview
Application is calling real CEPIK API in order to retrieve information about new registered cars in Poland.

Apache Camel framework is used for routing and aggregating data. It consumes input json message with parameters.
As an output it generates report with information about recently registered cars.
## Technologies
### Application
- Java 11
- Spring Boot
- Apache Camel

### Testing
- JUnit 5
