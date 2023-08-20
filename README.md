# Book Application

This repository contains the components for the Book Application, including services for book and user management, and the frontend UI.

## Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Getting Started](#getting-started)
    - [Book Service](#book-service)
    - [User Service](#user-service)
    - [Frontend](#frontend)

## Overview

- **Book Service**: Java application developed using Spring Boot for managing books.
- **User Service**: Java application developed using Spring Boot for managing users.
- **Frontend**: User interface developed using Angular for the entire application.

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Node.js](https://nodejs.org/) and npm (comes bundled with Node.js, needed for frontend only)
- Angular CLI (For frontend development): Install it globally using `npm install -g @angular/cli`

## Getting Started

To run the entire application stack (book-service, user-service, and frontend) with their respective databases, use docker-compose.

1. Clone the Repository (if you haven’t already):
    
   ```bash
   git clone https://github.com/your-username/book-application.git
   cd book-application
   ```
   
2. Build and start the services:

   ```bash
   docker-compose build
   docker-compose up
   ```

This will start:

- The book-service on port 8092.
- The user-service on port 8091.
- The frontend on port 8083.
- PostgreSQL databases for both the book-service and the user-service on ports 5441 and 5440 respectively.

You can access the frontend at http://localhost:8083.

3. Stopping services:

   ```bash
   docker-compose down
   ```