# Animal Management System

This project is a test task for FUIB. 
This API designed to handle the upload and management of animal data from CSV or XML files.
****

## Getting started
To get started with the Animal Management System, follow these steps:

    - Clone repository to your local machine: 
        git clone https://github.com/your-username/animal-management-system.git
    
    - Build the project using Maven;
    - Start your Apache Tomcat server
    - Go to the http://localhost:8080/files/uploads
****

## How to use?
- **Uploading file** - http://localhost:8080/files/uploads - start point. Page to upload file.
- **Error page** - http://localhost:8080/files/error - page informing about the occurrence of an error.
- **Page with applied filters** - http://localhost:8080/files/animals?type=cat&sex=male&category=3rd
- **Page with applied sorting** -http://localhost:8080/files/animals?sortBy=weight
****

## Environment
- Java 17
- Spring MVC
- Maven
- Apache Tomcat 9.0.87
- Thymeleaf