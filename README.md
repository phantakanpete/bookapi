# Book API

A Spring Boot REST API for managing books

---

## Database Setup

### 1. Create database
```sql
CREATE DATABASE bookdb;
```

### 2. Create table
```sql
USE bookdb;

CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_date DATE
);

CREATE INDEX idx_author ON book(author);
```

---

## Environment Setup

### 1. Fill in your credentials in `bookapi/src/main/resource/application.properties`
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/bookdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## Running the Server

### 1. Clone the repository
```bash
git clone https://github.com/phantakanpete/bookapi.git
cd bookapi
```

### 2. Run the application
```bash
./mvnw spring-boot:run
```

### 3. Verify server is running
```
http://localhost:8080/books

```

---

## Running Integration Tests

### 1. Run all tests
```bash
./mvnw test
```

### 2. Run specific test class
```bash
./mvnw test -Dtest=BookControllerTest
```

### Expected output
```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## API Endpoints

### GET /books
Returns all books.

**Request**
```
GET http://localhost:8080/books
```

**Response 200**
```json
{
    "status": 200,
    "message": "OK",
    "data": [
        {
            "id": 1,
            "title": "1984",
            "author": "George Orwell",
            "publishedDate": "2492-06-08"
        }
    ]
}
```

---

### GET /books?author={authorName}
Search books by author name.

**Request**
```
GET http://localhost:8080/books?author=George
```

**Response 200**
```json
{
    "status": 200,
    "message": "OK",
    "data": [
        {
            "id": 1,
            "title": "1984",
            "author": "George Orwell",
            "publishedDate": "2492-06-08"
        }
    ]
}
```

**Response 404 — author not found**
```json
{
    "status": 404,
    "message": "NOT_FOUND",
    "data": "No books found for author: unknown"
}
```

---

### POST /books
Add one or multiple books.

**Request**
```
POST http://localhost:8080/books
Content-Type: application/json
```

```json
[
    {
        "title": "1984",
        "author": "George Orwell",
        "publishedDate": "2492-06-08"
    },
    {
        "title": "Brave New World",
        "author": "Aldous Huxley",
        "publishedDate": "2475-01-01"
    }
]
```

**Response 201**
```json
{
    "status": 201,
    "message": "CREATED",
    "data": [
        {
            "id": 1,
            "title": "1984",
            "author": "George Orwell",
            "publishedDate": "1949-06-08"
        },
        {
            "id": 2,
            "title": "Brave New World",
            "author": "Aldous Huxley",
            "publishedDate": "1932-01-01"
        }
    ]
}
```

**Response 400 — validation errors**
```json
{
    "status": 400,
    "message": "BAD_REQUEST",
    "data": [
        "Book[0] title is required",
        "Book[1] author is required",
        "Book[2] publishedDate is invalid"
    ]
}
```
