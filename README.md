# 🔐 API Key Management & Rate Limiting Service

A production-style backend service built with **Java & Spring Boot** that enables developers to generate API keys and securely access protected APIs with **rate limiting, usage quotas, and authentication**.

This project demonstrates real-world backend engineering practices including **API security, request throttling, concurrency handling, containerization, CI/CD, and cloud deployment**.

---

# 🚀 Key Features

* 🔑 **User Authentication** – Secure user registration and login using **JWT**
* 🔐 **API Key Management** – Generate and revoke API keys for controlled access
* ⏱ **Rate Limiting** – Fixed-window algorithm to prevent API abuse
* 📊 **Usage Tracking** – Monitor API usage with request logging
* 📅 **Monthly Quotas** – Limit total requests per API key
* ⚡ **Concurrency Protection** – Optimistic locking prevents race conditions
* 📦 **Containerized Deployment** – Docker & Docker Compose setup
* ☁️ **Cloud Deployment** – Hosted on AWS EC2
* 📑 **Structured API Responses** – Standardized response and error format
* 🛠 **CI/CD Automation** – Deployment pipeline using GitHub Actions
* 📘 **API Documentation** – Swagger / OpenAPI integration

---

# 🏗 System Architecture

```
Client
   │
   ▼
Spring Boot API
   │
   ├── JWT Authentication
   ├── API Key Validation
   ├── Rate Limiting Engine
   ├── Usage Logging
   │
   ▼
PostgreSQL Database
```

---

# 🧰 Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)

### Authentication & Security

* JWT (JSON Web Tokens)
* BCrypt Password Hashing
* API Key Hashing (SHA-256)

### Database

* PostgreSQL

### DevOps & Deployment

* Docker
* Docker Compose
* GitHub Actions CI/CD
* AWS EC2

---

# 📂 Project Structure

```
src/main/java/com/hdev/apikeymanager

├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── exception
├── config
```

---

# ⚙️ Running the Project Locally

### 1️⃣ Clone Repository

```
git clone https://github.com/<your-username>/apikeymanager.git
cd apikeymanager
```

### 2️⃣ Build Project

```
mvn clean package
```

### 3️⃣ Run with Docker

```
docker-compose up --build
```

Application runs at:

```
http://localhost:8081
```

Swagger API docs:

```
http://localhost:8081/swagger-ui/index.html
```

---

# ☁️ Cloud Deployment (AWS EC2)

Deployment steps used:

1. Launch EC2 instance (Ubuntu 22.04)
2. Install Docker & Docker Compose
3. Clone repository
4. Start containers

```
docker-compose up --build -d
```

API accessible at:

```
http://<EC2_PUBLIC_IP>:8081
```

---

# 🔑 Example API Flow

### Register User

```
POST /api/auth/register
```

### Login

```
POST /api/auth/login
```

### Create API Key

```
POST /api/keys
```

### Access Protected API

```
GET /api/data

Header:
X-API-KEY: <your-api-key>
```

---

# 📊 Backend Concepts Demonstrated

* Fixed-window rate limiting
* API key hashing (SHA-256)
* Optimistic locking for concurrency control
* Centralized exception handling
* Structured API responses
* Containerized backend deployment
* CI/CD automation

---

# 👨‍💻 Author

**Umme Hani**

Backend Developer | Java | Spring Boot

GitHub:
https://github.com/Ummehani18

---

# 📜 License

MIT License
