# Student Management

A full-stack Spring Boot application for registering and deleting students.

## Requirements

- Java 21
- Maven
- MySQL

## Setup

1. Create the database:

   ```sql
   CREATE DATABASE student_management;
   ```

2. Set `DB_USERNAME` and `DB_PASSWORD` environment variables for your MySQL account. The application defaults to `root` with an empty password for local development.

3. Run the application:

   ```powershell
   mvn spring-boot:run
   ```

4. Open `http://localhost:8082`.

## Tests and coverage

Run the unit tests and generate the JaCoCo report with:

```powershell
mvn clean verify
```

The HTML coverage report is generated at `target/site/jacoco/index.html`.

## Docker

Build the image after packaging the application:

```powershell
mvn clean package
docker build -t student-management:local .
```

Run it with your MySQL connection settings:

```powershell
docker run --rm -p 8080:8080 `
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/student_management" `
  -e SPRING_DATASOURCE_USERNAME="root" `
  -e SPRING_DATASOURCE_PASSWORD="<your-password>" `
  student-management:local
```

## CI/CD

Pushing to `main` runs Java 21 compilation, tests and JaCoCo, waits for the SonarQube quality gate, pushes versioned and `latest` Docker Hub images, then deploys the versioned image to Ubuntu over SSH.

Configure these GitHub Actions secrets before enabling deployment:

- `SONAR_HOST_URL`, `SONAR_TOKEN`
- `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`
- `SERVER_HOST`, `SERVER_USER`, `SERVER_SSH_KEY`
- `DB_HOST`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`

The Ubuntu host must have Docker installed and network access to the MySQL server. The SonarQube server must be reachable by GitHub-hosted runners. The deployment exposes the application on port `8080`.
