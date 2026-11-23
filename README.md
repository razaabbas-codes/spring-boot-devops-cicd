# 🚀 End-to-End DevOps CI/CD & GitOps Pipeline

### Automated Build, Testing, Code Quality Analysis, Containerization, and Kubernetes Deployment

An end-to-end DevOps project implementing a CI/CD pipeline for a Spring Boot Task Manager API using Jenkins, Maven, SonarQube, Docker, Docker Hub, Kubernetes, Helm, and Argo CD.

The project automates the process of building application code, running tests, analyzing code quality, creating Docker images, publishing versioned images, updating deployment configuration, and deploying the application to Kubernetes through a GitOps workflow.

---

## 👨‍💻 Project Information

| Attribute | Details |
|---|---|
| Project Name | Task Manager API — CI/CD & GitOps |
| Project Type | DevOps / Cloud / CI/CD |
| Application | Spring Boot REST API |
| Cloud Infrastructure | AWS EC2 |
| Operating System | Ubuntu Linux |
| CI Tool | Jenkins |
| Build Tool | Maven |
| Code Quality | SonarQube |
| Containerization | Docker |
| Container Registry | Docker Hub |
| Container Orchestration | Kubernetes |
| Kubernetes Distribution | K3s |
| Deployment Packaging | Helm |
| GitOps | Argo CD |
| Programming Language | Java |
| Java Version | Java 21 |

---

## 📌 Overview

This project demonstrates the implementation of an automated CI/CD and GitOps workflow for a Spring Boot application.

The goal was to understand and implement the complete software delivery lifecycle—from committing application code to deploying a containerized application on Kubernetes.

Instead of manually building the application, creating Docker images, and deploying containers, the pipeline automates these processes through Jenkins and Argo CD.

The project also demonstrates the separation between Continuous Integration and Continuous Delivery using GitOps principles.

---

## 🏗️ Architecture

```text
                 Developer
                     |
                     | Code Changes
                     v
                 GitHub
                     |
                     v
                 Jenkins
                     |
         +-----------+-----------+
         |           |           |
         v           v           v
       Maven       Tests     SonarQube
       Build       JUnit     Code Analysis
         |
         v
       Docker
         |
         v
     Docker Hub
         |
         | New Image Published
         v
   Jenkins Updates
    Helm Values
         |
         v
       GitHub
         |
         | Desired State Change
         v
      Argo CD
         |
         v
      Helm Chart
         |
         v
      Kubernetes
        (K3s)
         |
         v
   Spring Boot API
```

---

## 🔄 CI/CD Workflow

### 1. Source Code Management

Application source code and deployment configuration are maintained in GitHub.

Changes are committed and pushed to the repository.

### 2. Jenkins Pipeline

Jenkins executes the CI pipeline.

The pipeline performs the following operations:

- Checks out the source code
- Builds the application using Maven
- Executes unit tests
- Performs SonarQube code analysis
- Builds a Docker image
- Pushes the Docker image to Docker Hub
- Updates the Helm deployment image tag
- Commits and pushes the deployment configuration change to GitHub

### 3. Maven Build

Maven is used to:

- Compile the Java application
- Resolve project dependencies
- Package the application into an executable JAR

### 4. Automated Testing

JUnit tests are executed through Maven.

The pipeline publishes test reports through Jenkins.

### 5. Code Quality Analysis

SonarQube analyzes the source code for:

- Code quality issues
- Bugs
- Code smells
- Maintainability concerns
- Security-related findings

### 6. Docker Containerization

Docker packages the Spring Boot application and Java runtime into a container image.

The image is tagged using the Jenkins build number.

Example:

```text
raza0111/task-manager-api:13
```

The pipeline also publishes the `latest` tag.

### 7. Docker Hub

The generated Docker image is pushed to Docker Hub.

Docker Hub acts as the container image registry from which Kubernetes can pull the application image.

### 8. GitOps Deployment

After publishing the Docker image, Jenkins updates the image tag in the Helm configuration stored in GitHub.

This Git commit represents the desired deployment state.

### 9. Argo CD

Argo CD monitors the Git repository.

When it detects a deployment configuration change, it compares the desired state in Git with the live state in Kubernetes.

It synchronizes the application deployment with the updated configuration.

### 10. Kubernetes Deployment

Helm renders the Kubernetes configuration.

Kubernetes deploys the updated application container and manages the Pod lifecycle.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Application development |
| Spring Boot | REST API framework |
| Maven | Build automation and dependency management |
| Git | Version control |
| GitHub | Source code and deployment configuration |
| Jenkins | CI pipeline automation |
| SonarQube | Code quality analysis |
| Docker | Application containerization |
| Docker Hub | Container image registry |
| Kubernetes | Container orchestration |
| K3s | Lightweight Kubernetes distribution |
| Helm | Kubernetes package management |
| Argo CD | GitOps continuous delivery |
| AWS EC2 | Cloud infrastructure |
| Ubuntu Linux | Server operating system |

---

## 📂 Repository Structure

```text
spring-boot-devops-cicd/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── ...
│   │
│   └── test/
│       └── java/
│           └── ...
│
├── task-manager/
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates/
│       ├── deployment.yaml
│       ├── service.yaml
│       └── ...
│
├── Dockerfile
├── Jenkinsfile
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

### Important Files

| File | Description |
|---|---|
| `Jenkinsfile` | Defines the automated CI pipeline |
| `Dockerfile` | Defines how the application is containerized |
| `pom.xml` | Maven project configuration and dependencies |
| `task-manager/values.yaml` | Helm deployment configuration |
| `task-manager/templates/` | Kubernetes resource templates |
| `src/` | Application source code and tests |

---

## ⚙️ Jenkins Pipeline Stages

The Jenkins pipeline consists of the following stages:

### Stage 1 — Checkout

```groovy
git branch: 'main',
    url: 'https://github.com/razaabbas-codes/spring-boot-devops-cicd.git'
```

Retrieves the source code from GitHub.

---

### Stage 2 — Build

```bash
chmod +x mvnw
./mvnw clean package -DskipTests
```

Compiles and packages the application.

---

### Stage 3 — Unit Tests

```bash
./mvnw test
```

Executes the application's automated tests.

---

### Stage 4 — SonarQube Analysis

The Maven SonarQube scanner analyzes the application source code.

```bash
./mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:5.3.0.6276:sonar
```

---

### Stage 5 — Docker Build and Push

The application is packaged into a Docker image.

```bash
docker build -t raza0111/task-manager-api:${BUILD_NUMBER} .
```

The image is tagged and pushed to Docker Hub.

```bash
docker push raza0111/task-manager-api:${BUILD_NUMBER}
```

---

### Stage 6 — Update Helm Image Tag

Jenkins updates the Docker image tag in the Helm configuration.

The updated configuration is committed and pushed to GitHub.

This allows Argo CD to detect the new desired state.

---

## 🐳 Docker

The application is containerized using Docker.

The Docker image contains:

- Java runtime
- Application JAR
- Required runtime configuration

Example:

```bash
docker build -t task-manager-api .
```

Run the container locally:

```bash
docker run -p 8080:8080 task-manager-api
```

---

## ☸️ Kubernetes

The application is deployed to a Kubernetes cluster running K3s.

Kubernetes manages:

- Application Pods
- Deployment replicas
- Container lifecycle
- Health checks
- Service networking
- Application availability

---

## 📦 Helm

Helm is used to package and configure the Kubernetes deployment.

The Helm values configuration defines:

- Docker image repository
- Image tag
- Replica count
- Service type
- Service ports
- Resource requests and limits
- Liveness probes
- Readiness probes

---

## 🔄 GitOps with Argo CD

Argo CD follows a GitOps deployment model.

Git acts as the source of truth for the desired application deployment state.

The workflow is:

```text
Jenkins
   |
   | Builds Docker Image
   v
Docker Hub
   |
   | New Version Published
   v
Jenkins Updates Helm Values
   |
   v
GitHub Commit
   |
   v
Argo CD Detects Change
   |
   v
Helm Renders Kubernetes Resources
   |
   v
Kubernetes Deployment
```

Argo CD continuously monitors the application configuration and compares it with the live Kubernetes environment.

---

## 🌐 Deployment

The application was deployed on an AWS EC2 instance running Ubuntu Linux.

The environment includes:

- Jenkins
- SonarQube
- Docker
- K3s Kubernetes
- Helm
- Argo CD

The application was exposed through a Kubernetes NodePort service.

### Application Health Endpoint

```text
/actuator/health
```

### Version Endpoint

```text
/api/tasks/version
```

---

## 📊 Project Verification

The deployed application was verified using its health endpoint.

Expected health response:

```json
{
  "groups": [
    "liveness",
    "readiness"
  ],
  "status": "UP"
}
```

The version endpoint was also used to verify that the deployed application version was running.

---

## 🔐 Security Considerations

The project uses Jenkins credentials for authentication with external services.

Sensitive credentials such as:

- GitHub Personal Access Tokens
- Docker Hub credentials
- SSH private keys
- Jenkins credentials

should never be committed to the repository.

Credentials should be stored using Jenkins Credentials Management.

---

## 🧠 Key DevOps Concepts Demonstrated

- Continuous Integration
- Continuous Delivery
- GitOps
- Infrastructure and application automation
- Linux administration
- Git version control
- Maven build lifecycle
- Automated testing
- Code quality analysis
- Docker containerization
- Container registries
- Kubernetes orchestration
- Helm package management
- Argo CD synchronization
- Deployment automation
- Health monitoring
- Troubleshooting
- Cloud infrastructure

---

## 🧪 Challenges Faced

During implementation, several practical issues were encountered and resolved:

- Git push rejection due to remote changes
- GitHub authentication issues
- Jenkins configuration and pipeline execution
- Linux file permissions
- Kubernetes configuration and permissions
- AWS Security Group networking
- Argo CD access configuration
- Docker container lifecycle management
- Updating Helm image tags automatically
- Troubleshooting failed pipeline stages

These challenges helped develop practical understanding of how different DevOps tools interact.

---

## 🎯 Learning Outcomes

Through this project, I gained practical experience with:

- Building an automated CI/CD pipeline
- Understanding the complete application delivery lifecycle
- Working with Linux servers and AWS EC2
- Configuring Jenkins pipelines
- Building and publishing Docker images
- Deploying applications to Kubernetes
- Managing Kubernetes deployments using Helm
- Implementing GitOps using Argo CD
- Troubleshooting deployment and pipeline issues

---

## 🚀 Future Improvements

Potential improvements include:

- Configuring GitHub webhook-based automatic Jenkins triggering
- Implementing SonarQube Quality Gate enforcement
- Adding image vulnerability scanning using Trivy
- Implementing Kubernetes Ingress
- Adding monitoring using Prometheus and Grafana
- Implementing automated rollback strategies
- Improving secret management
- Using separate CI and GitOps repositories
- Implementing automated integration testing
- Improving deployment security

---

## 👨‍💻 Author

**Raza Abbas**

Computer Science Engineering Graduate  
Aspiring DevOps / Cloud Engineer

### Skills

Linux | AWS | Docker | Kubernetes | Jenkins | CI/CD | Terraform | Ansible | Git | GitHub | Helm | Argo CD | Python | Bash

---

## 📌 Note

This project was developed as a hands-on learning and portfolio project to understand and implement an end-to-end DevOps workflow.

The implementation, configuration, troubleshooting, and deployment process are documented as part of my learning journey.

---

⭐ If you find this project useful, feel free to explore the repository.
