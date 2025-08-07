# 🛠 Hanoi Web Service – Streaming API for Tower of Hanoi

This is a **Kotlin + Spring Boot** backend application that computes and streams the solution to the **Tower of Hanoi** puzzle via a REST API. The service streams moves line-by-line in **NDJSON format**, enabling responsive UI rendering as moves are computed.

---

## 🔗 Live API

> https://hanoi-service.onrender.com/api/hanoi/10

Replace `10` with any disk count you want to solve.

---

## 📐 API Design

### Endpoint

```http
GET /api/hanoi/{n}
```
- n: number of disks (Integer)

---

### Response (NDJSON Format)

The API responds with a stream of newline-delimited JSON objects — one per move — using the NDJSON format. This allows the client to begin processing each movement as soon as it is computed, instead of waiting for the entire response.

#### Example Output:
```http
{"start": 1, "end": 3}
{"start": 1, "end": 2}
{"start": 3, "end": 2}
```

This design is especially useful when solving the puzzle with a large number of disks (e.g., 20 or 50), allowing real-time rendering on the client side.

---

## 🧠 How It Works

The service implements the classic Tower of Hanoi recursive algorithm and writes each move as a JSON object to an output stream using `StreamingResponseBody`. This design allows real-time streaming of the solution without waiting for the full computation to finish — making it ideal for use in animated or interactive clients.

### Core Logic

```kotlin
@GetMapping("/{n}")
fun streamMoves(@PathVariable n: Int): ResponseEntity<StreamingResponseBody> { ... }
```
Internally, the recursive function computes and writes each move directly to the response stream:

```kotlin
private fun hanoi(n: Int, start: Int, end: Int, writer: BufferedWriter)
```

## 📦 Tech Stack

- Kotlin  
- Spring Boot (REST Controller)  
- StreamingResponseBody  
- Docker  
- Render.com (Deployment)

---

## 🚀 Deployment

This project is containerized using Docker and deployed on Render, taking advantage of its Git-based continuous deployment and free plan.

### Deployment Highlights

- Custom Dockerfile builds a lightweight runnable image  
- Deploys automatically on push to the `main` branch  
- Public endpoint:  
  `https://hanoi-service.onrender.com/api/hanoi/{n}`

---

## 📁 Project Structure
```
src/
└── main/
├── kotlin/
│ └── com.sosorio.hanoi_service/
│ └── HanoiController.kt
└── resources/
└── application.yml
Dockerfile
README.md
```

---

## 🧪 Running Locally

### Prerequisites

- JDK 17+  
- Gradle  
- Docker (optional)

### Option 1: Run with Gradle

```bash
./gradlew bootRun
```

Visit:
http://localhost:8080/api/hanoi/5

### Option 2: Run with Docker

```bash
docker build -t hanoi-service .
docker run -p 8080:8080 hanoi-service
```
