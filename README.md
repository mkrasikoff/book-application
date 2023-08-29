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

## Port Conventions
We use specific ranges of ports for different types of services:

| Service Type   | Port Range |
|----------------|------------|
| Frontend       | `808x`     |
| Backend (APIs) | `818x`     |
| Databases      | `828x`     |
| Debug          | `838x`     |

## Service Ports

Here is the mapping of each service to its accessible port:

| Service Name                   | External Port | Internal Port |
|--------------------------------|---------------|---------------|
| Frontend                       | 8080          | 80            |
| Book Service API               | 8181          | 8080          |
| User Service API               | 8182          | 8080          |
| PostgreSQL for Book Service    | 8281          | 5432          |
| PostgreSQL for User Service    | 8282          | 5432          |

## Networks
- `book-network`: For book service and its database.
- `user-network`: For user service and its database.
- `book-user-network`: For services that interact between books and users.

- You can access the frontend at http://localhost:8080.

2. Stopping services:

   ```bash
   docker-compose down
   ```