# Frontend part - Angular Project

This frontend project is developed using Angular and serves as the UI for managing books and users. It interfaces with two backend services: `book-service` and `user-service`, both developed using Java Spring.

## Table of Contents

1. [Features](#features)
2. [Prerequisites](#prerequisites)
3. [Getting Started](#getting-started)
4. [Install Dependencies](#install-dependencies)
5. [Project Structure](#project-structure)
6. [Testing](#testing)

## Features

- **Book Management**: View, add, update, and delete books.
- **User Management**: View, add, update, and delete users.

## Prerequisites

- [Node.js](https://nodejs.org/) and npm (comes bundled with Node.js)
- Angular CLI: Install it globally using `npm install -g @angular/cli`

## Getting Started

1. **Clone the Repository**

   ```bash
   git clone https://github.com/your-username/frontend.git
   cd frontend
   ```

## Install Dependencies

Run the following command:

```bash
npm install
```

The application should be accessible at http://localhost:8083.

## Project Structure
The main folders in the project are:

1. **src/app**: Contains the main components, services, and models for the application.
2. **book**: Components and services related to book management.
3. **user**: Components and services related to user management.
4. **welcome**: The welcome page component.

## Testing

Open frontend directory. Use command below to build application:

```bash
ng build --configuration production
```

After that, you need to go to book-application directory and build new images:

```bash
docker-compose build --no-cache
```

And run new images using docker-compose:

```bash
docker-compose up
```
