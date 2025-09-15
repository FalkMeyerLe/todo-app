# Token-Based Todo App

A monorepo containing two Spring Boot microservices built with Kotlin that work together to support a basic token-authenticated todo application.

## Architecture

### Services

1. **user-service** (Port 8080)
   - Handles user registration and login
   - Issues JWT tokens for authentication
   - Uses H2 in-memory database for user storage
   - Endpoints:
     - `POST /user/register` - Register a new user
     - `POST /user/login` - Login and get JWT token
     - `POST /auth/token` - Validate JWT token

2. **todo-service** (Port 8081)
   - Manages user-specific todo notes
   - Validates JWT tokens issued by user-service
   - Uses H2 in-memory database for todo storage
   - Endpoints:
     - `GET /todos` - Get all todos for authenticated user
     - `POST /todos` - Create new todo

### Technology Stack

- **Language**: Kotlin
- **Framework**: Spring Boot 3.3.4
- **Security**: Spring Security + JWT
- **Database**: H2 (in-memory)
- **Build Tool**: Gradle with Kotlin DSL
- **Authentication**: JWT tokens with shared secret

## Getting Started

### Prerequisites

- Java 19 or higher
- Gradle (or use the included wrapper)

### Building the Project

```bash
./gradlew build
```

### Running the Services

#### Start user-service (Terminal 1):
```bash
./gradlew :user-service:bootRun
```

#### Start todo-service (Terminal 2):
```bash
./gradlew :todo-service:bootRun
```

Both services will start with their respective databases initialized.

### Testing the API

#### 1. Register a new user
```bash
curl -X POST http://localhost:8080/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

#### 2. Login to get JWT token
```bash
curl -X POST http://localhost:8080/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

Save the returned token for subsequent requests.

#### 3. Create a todo (replace YOUR_TOKEN with actual token)
```bash
curl -X POST http://localhost:8081/todos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "title": "Apply to Europace",
    "description": "Apply for a job as a developer at Europace to return to working at Hypoport with motivation and enjoyment"
  }'
```

#### 4. Get all todos
```bash
curl -X GET http://localhost:8081/todos \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## Project Structure

```
├── build.gradle.kts                 # Root build configuration
├── settings.gradle.kts              # Gradle settings
├── user-service/                    # User authentication service
│   ├── build.gradle.kts
│   └── src/main/kotlin/com/todoapp/userservice/
│       ├── UserServiceApplication.kt
│       ├── config/WebSecurityConfig.kt
│       ├── controller/
│       │   ├── UserController.kt
│       │   └── TokenController.kt
│       ├── dto/UserDto.kt
│       ├── entity/User.kt
│       ├── handler/
│       │   ├── LoginUserHandler.kt
│       │   └── RegisterUserHandler.kt
│       ├── repository/UserRepository.kt
│       ├── security/
│       │   ├── AuthTokenFilter.kt
│       │   └── JwtUtils.kt
│       └── service/UserDetailsServiceImpl.kt
└── todo-service/                    # Todo management service
    ├── build.gradle.kts
    └── src/main/kotlin/com/todoapp/todoservice/
        ├── TodoServiceApplication.kt
        ├── config/WebSecurityConfig.kt
        ├── controller/TodoController.kt
        ├── dto/TodoDto.kt
        ├── entity/Todo.kt
        ├── handler/
        │   ├── CreateTodoHandler.kt
        │   └── GetTodosByUsernameHandler.kt
        ├── mapper/TodosMapper.kt
        ├── repository/TodoRepository.kt
        └── security/
            ├── AuthTokenFilter.kt
            └── JwtUtils.kt
        
```

## Features

- ✅ User registration with validation
- ✅ User login with JWT token generation
- ✅ Token-based authentication across services
- ✅ User-specific todo management
- ✅ Input validation
- ✅ H2 database integration
- ✅ Monorepo structure
## Configuration

### JWT Configuration

Both services share the same JWT secret and expiration settings in their `application.properties`:

```properties
jwt.secret=TestChallenge?SecretKeyF0r%EP2025$
jwt.expiration=86400000
```

### Database Configuration

Each service has its own H2 database:

- user-service: `jdbc:h2:mem:userdb`
- todo-service: `jdbc:h2:mem:tododb`

H2 console is available at `/h2-console` for each service.

## Development


### Security

- JWT tokens are signed with HMAC SHA-256
- Cross-service authentication via shared JWT secret
- Token validation on all protected endpoints

## Production Considerations

- Replace H2 with a persistent database (PostgreSQL, MySQL)
- Passwords are encrypted using BCrypt
- Use environment variables for JWT secret and database credentials
- Implement proper logging and monitoring
- Add API documentation with Swagger
- Implement proper error handling and validation
- Add unit and integration tests
