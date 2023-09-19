
# QuizApp - Spring Boot Project

QuizApp is a web application built with Spring Boot that allows users to access a list of questions, perform CRUD (Create, Read, Update, Delete) operations on questions, filter questions by topics, create user profiles, take quizzes, and store quiz responses in a MySQL database. It also integrates with Cloudinary for user profile image management.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Swagger API Documentation](#swagger-api-documentation)


## Project Overview

QuizApp is designed to provide the following key features:

- **Question Management:**
  - View a list of questions.
  - Create new questions.
  - Update existing questions.
  - Delete questions.

- **Topic Filtering:**
  - Filter questions by topics.

- **User Profiles:**
  - Create and manage user profiles.
  - Upload and manage profile images using Cloudinary.

- **Quiz Taking:**
  - Allow users to take quizzes.
  - Store quiz responses in a MySQL database.

## Features

QuizApp offers a wide range of features to meet your quiz and question management needs:

### 1. Question Management

Easily manage your questions:
- View a comprehensive list of questions.
- Create new questions with detailed descriptions.
- Add list of questions.
- Update existing questions to keep your content up-to-date.
- Delete questions that are no longer relevant.

### 2. Topic Filtering

Efficiently find questions by topic:
- Use the topic filter to narrow down questions by category.
- Quickly access questions related to specific subjects.

### 3. User Profiles

Enhance user engagement with profiles:
- Create user profiles to personalize the quiz experience.
- Upload profile images using Cloudinary for a unique touch.

### 4. Quiz Taking

Enable users to test their knowledge:
- Let users take quizzes based on available questions.
- Store quiz responses in a MySQL database for analysis and review.

## Technologies Used

- Java
- Spring Boot (MVC)
- Spring Data JPA (Hibernate)
- MySQL Database
- Cloudinary (For user profile image management)

Add any other technologies or libraries you've used in your project.

## Getting Started

### Prerequisites

Before you begin, make sure you have the following installed:

- Java Development Kit (JDK)
- MySQL Database
- Apache Maven
- Cloudinary API Key and Secret (for profile image management)

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/yourusername/quizApp_backend.git
2. Configure application.properties:
   ```shell
     # configure according to your requirement 
     server.port=8080
     spring.datasource.url=jdbc:mysql://localhost:3306/spring_quiz
     spring.datasource.username="username"
     spring.datasource.password="password"
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.generate-ddl=true
     spring.jpa.show.sql=true
     spring.jpa.format.sql=true
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
     spring.servlet.multipart.max-file-size=10MB
     spring.servlet.multipart.max-request-size=10MB
3. Configure Cloudinary Configuration
   ```shell
     config.put("cloud_name","");
     config.put("api_key","");
     config.put("api_secret","");
     config.put("sceure",true); 
     


### Usage
Provide instructions on how to use and interact with your QuizApp. Include examples of common tasks or use cases.

### Configuration
Explain how to configure your application, including setting up the database and Cloudinary credentials.

### Swagger API Documentation
QuizApp provides Swagger for easy API documentation and testing. You can access the Swagger UI to interact with the APIs as follows:
 ```shell
   http://localhost:8080/swagger-ui.html

