# 🔐 API Key Management & Rate Limiting Service

A production-style backend service built with **Spring Boot** that allows users to generate API keys and access protected APIs with **rate limiting, usage quotas, and authentication**.

This project demonstrates backend engineering concepts such as:

- API key authentication
- Rate limiting
- JWT authentication
- Secure key storage
- Concurrency handling
- Docker containerization
- Cloud deployment on AWS

---

# 🚀 Features

✔ User Registration & Login (JWT Authentication)  
✔ Secure API Key Generation  
✔ API Key Revocation & Management  
✔ Rate Limiting (Fixed Window Algorithm)  
✔ Monthly Usage Quotas  
✔ API Usage Logging  
✔ Concurrency Protection with Optimistic Locking  
✔ Structured API Responses  
✔ Dockerized Application  
✔ Cloud Deployment on AWS EC2

---

# 🏗 System Architecture
``bash
# 🔐 API Key Management & Rate Limiting Service

A production-style backend service built with **Spring Boot** that allows users to generate API keys and access protected APIs with **rate limiting, usage quotas, and authentication**.

This project demonstrates backend engineering concepts such as:

- API key authentication
- Rate limiting
- JWT authentication
- Secure key storage
- Concurrency handling
- Docker containerization
- Cloud deployment on AWS

---

# 🚀 Features

✔ User Registration & Login (JWT Authentication)  
✔ Secure API Key Generation  
✔ API Key Revocation & Management  
✔ Rate Limiting (Fixed Window Algorithm)  
✔ Monthly Usage Quotas  
✔ API Usage Logging  
✔ Concurrency Protection with Optimistic Locking  
✔ Structured API Responses  
✔ Dockerized Application  
✔ Cloud Deployment on AWS EC2

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
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)

### Authentication
- JWT (JSON Web Tokens)
- BCrypt Password Hashing
- API Key Authentication

### Database
- PostgreSQL

### DevOps
- Docker
- Docker Compose
- AWS EC2
- GitHub

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
```

---

# ⚙️ Running the Project Locally

### 1️⃣ Clone the repository

```
git clone https://github.com/
<your-username>/apikeymanager.git
```


### 2️⃣ Build the project

```
mvn clean package
```


### 3️⃣ Run using Docker

```
docker-compose up --build
```
The API will be avilable at:
```
http://localhost:8081
```


---

# ☁️ Deployment (AWS EC2)

Steps used for production deployment:

1. Launch EC2 instance (Ubuntu 22.04)
2. Install Docker

```
sudo apt update
sudo apt install docker.io -y
```

3. Install Docker Compose

```
sudo apt install docker-compose -y
```

4. Clone repository

```  
git clone https://github.com/
<your-username>/apikeymanager.git
cd apikeymanager
```
5. Start containers

```  
docker-compose uo --build -d
```

Access API: 
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
POST /api/data

Header: 
X-API-KEY: <your-api-key>
```

---

# 📊 Key Backend Concepts Implemented

- Fixed-window rate limiting
- API key hashing using SHA-256
- Optimistic locking for concurrency control
- Centralized exception handling
- Structured API responses
- Containerized deployment

---

# 👨‍💻 Author

**Umme Hani**

Backend Developer | Java | Spring Boot

GitHub:  
https://github.com/Ummehani18

---

# 📜 License

This project is licensed under the MIT License.


