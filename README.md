
# QuizMate - Spring Boot Project


QuizMate, powered by Spring Boot, redefines the quiz experience with its dynamic and user-centric design. Offering seamless CRUD operations on questions and easy topic filtering, users can effortlessly navigate and create quizzes tailored to their interests. Personalized profiles, enhanced with Cloudinary-managed images, further elevate user engagement, creating a vibrant and interactive platform.

Security is paramount in QuizApp, with robust features such as Spring Security and JWT authentication ensuring the safety of user data. Unique to QuizMate is the ability to generate custom question papers, providing a personalized touch to quiz sessions. The application also offers email verification during account creation, adding an extra layer of security and verification. Additionally, the session creation feature allows users to conduct private quiz sessions, sharing session credentials through email. This feature enables users to invite others to participate in quizzes, with session details and progress tracking stored for a seamless and enjoyable quizzing experience.

QuizMate is deployed on AWS EC2 using Docker containers, leveraging the scalability and reliability of AWS infrastructure. The database is hosted on AWS RDS, ensuring efficient data management and accessibility. Additionally, QuizMate employs a comprehensive CI/CD pipeline, powered by GitHub Actions, to streamline development process. This integration ensures rapid iteration and delivery of new features, maintaining QuizMate's position as the preferred platform for interactive and secure quizzing.

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
  - Filter questions by difficulty.

- **User Profiles:**
  - Create and manage user profiles.
  - Upload and manage profile images using Cloudinary.

- **Quiz Taking:**
  - Allow users to take quizzes.
  - Store quiz responses in a MySQL database.
  - Quiz Api are secured and only authenticated user can use this.

## Features

QuizApp offers a wide range of features to meet your quiz and question management needs:

### 1. Question Management

Easily manage your questions:
- View a comprehensive list of questions.
- Create new questions with detailed descriptions.
- Add list of questions.
- Update existing questions to keep your content up-to-date.
- Delete questions that are no longer relevant.
- Generate  Balanced Question paper.

### 2. Topic Filtering

Efficiently find questions by topic:
- Use the topic filter to narrow down questions by category.
- Quickly access questions related to specific subjects.
- Filter question according to their difficulty.

### 3. User Profiles

Enhance user engagement with profiles:
- Create user profiles to personalize the quiz experience.
- Upload profile images using Cloudinary for a unique touch.
- Only authenticated can allow to use this.

### 4. Quiz Taking

Enable users to test their knowledge:
- Let users take quizzes based on available questions.
- Store quiz responses in a MySQL database for analysis and review.
- Keep track record of their progress & also get rank among all user.

## Technologies Used

- Java
- Spring Boot (MVC)
- Spring Data JPA (Hibernate)
- Spring Security
- JWT authentication
- MySQL Database
- Cloudinary (For user profile image management)
- AWS for Deloyment 

Add any other technologies or libraries you've used in your project.

## Getting Started

### Prerequisites

Before you begin, make sure you have the following installed:

- Java Development Kit (JDK)
- MySQL Database
- Apache Maven
- Cloudinary API Key and Secret (for profile image management)
- You can also access from deployment server.http://52.66.242.197:8080/

       


### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/yourusername/quizApp_backend.git
2. Configure application.properties:
   ```shell
     # configure according to your requirement 
     server.port=8080
     spring.datasource.url=url
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

http://52.66.242.197:8080/swagger-ui/index.html

 ```shell
   http://localhost:8080/swagger-ui.html
