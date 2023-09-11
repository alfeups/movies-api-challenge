# Spring Boot Movies REST API with Swagger and Hexagonal Architecture

![Java](https://img.shields.io/badge/Java-17-brightgreen.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.0-brightgreen.svg)
![Swagger](https://img.shields.io/badge/Swagger-3.0.0-brightgreen.svg)
[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-alfeups%2Fmovies-blue.svg)](https://hub.docker.com/repository/docker/alfeups/movies/general)


## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [Running the Application](#running-the-application)
    - [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Docker Image](#docker-image)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17 or higher installed.
- [Maven](https://maven.apache.org/) installed.
- Your favorite Integrated Development Environment (IDE) like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/).

### Installation

1. Clone this repository:

```
git clone https://github.com/alfeups/movies-api.git
```

<br>

2. Usage:

Running the Application
To run the Spring Boot application, use the following Maven command:

```
mvn spring-boot:run
```

<br>

The application will start on <b>port 8080</b> by default. 
You can configure the port in the application.yml file.

3. API Documentation

Swagger UI is integrated to provide API documentation. You can access it by navigating to:

```
http://localhost:8080/swagger-ui.html
```
<br>

<h2> Project Structure </h2>

The project follows the Hexagonal Architecture pattern to separate concerns into different layers:

<li>Adapters: Web and Database - Contains the web controllers and Manages data storage and database interactions.
<li>Core/Domain: Houses the application logic and use cases and defines the core business domain and entities.
<li>Config: Contains Spring Boot configuration classes.

<h2> Docker Image </h2>

A Docker image for this application is available on Docker Hub:

[alfeups/movies](https://hub.docker.com/repository/docker/alfeups/movies/general)

You can pull the image and run it as a Docker container:

```
docker pull alfeups/movies:latest
```

```
docker run -p 8080:8080 alfeups/movies:latest
```

