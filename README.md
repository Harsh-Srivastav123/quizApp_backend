
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
  - Profile is verified by email - verifaction.
  - Upload and manage profile images using Cloudinary.
  - Get user status & rank.

  -**Session Management:**

  - Create a session to conduct private quiz sessions.
  - Invite other users to participate in the session.
  - Each user is personally identified with a unique sessionUserId sent through email.
  - A session report is created and saved for the user who created the session.


## Features

QuizMate offers a wide range of features to meet your quiz and question management needs:

### Question Management:
- **View Questions:** Users can view a list of questions stored in the system, including the question text, options, and correct answer.
- **Create Questions:** Authenticated users can create new questions, specifying the question text, options, correct answer, and optional details like difficulty level and topic.
- **Update Questions:** Users with the necessary permissions can edit existing questions to modify any details or correct errors.
- **Delete Questions:** Authorized users can delete questions that are no longer needed.

### Topic Filtering:
- **Filter by Topics:** Questions can be filtered based on predefined topics, allowing users to focus on specific areas of interest.
- **Filter by Difficulty:** Questions can also be filtered based on their difficulty level, helping users find questions that match their skill level.

### User Profiles:
- **Create and Manage Profiles:** Users can create their profiles, providing information such as name, email, and profile picture.
- **Email Verification:** During account creation, users are required to verify their email address, adding an extra layer of security.
- **Upload Profile Images:** Users can upload and manage their profile pictures using Cloudinary, enhancing their profile customization.
- **Get User Status & Rank:** Users can view their status and rank based on their quiz performance, providing a sense of achievement and progress tracking.

### Session Management:
- **Create Private Quiz Sessions:** Users can create private quiz sessions, setting a specific time frame for the session to be active.
- **Invite Others:** Users can invite others to participate in the session by sharing session credentials through email.
- **Unique SessionUserID:** Each user participating in the session is personally identified with a unique sessionUserID, ensuring accurate tracking and identification.
- **Session Reports:** A session report is generated and saved for the user who created the session, providing detailed insights into the session's performance and outcomes.

### Google Vertex AI Integration:
- **Hint Generation:** Google Vertex AI, specifically the Gemini Pro model, is used to generate hints for the questions. These hints can provide additional context or clues to help users answer questions correctly, enhancing the quiz experience.

### Security:
- **API Security:** All APIs, except for those related to questions, user creation, and user authentication, are secured through Spring Security.
- **JWT Token:** Secured APIs require a valid JWT token to access, ensuring that only authenticated and authorized users can perform actions such as viewing questions, creating sessions, and managing profiles.


## Technologies Used

- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- Google Vertex AI (Gemini-pro model)
- Spring Security
- JWT authentication
- MySQL Database
- Cloudinary (For user profile image management)
- AWS for Deloyment
- Github Actions for CI/CD pipeline
- Docker for containerisation

Add any other technologies or libraries you've used in your project.

## Getting Started

### Prerequisites

Before you begin, make sure you have the following installed:

- Java Development Kit (JDK)
- MySQL Database
- Apache Maven
- Cloudinary API Key and Secret (for profile image management)
- You can also access from deployment server.http://13.126.195.3:8080/

       


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

http://13.126.195.3:8080/swagger-ui/index.html

 ```shell
   http://localhost:8080/swagger-ui.html
