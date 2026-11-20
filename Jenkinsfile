pipeline {
    agent any

    tools {
        jdk 'Java21'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/razaabbas-codes/spring-boot-devops-cicd.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './mvnw test'
            }
        }

	stage('SonarQube Analysis') {
         steps {
        withSonarQubeEnv('SonarQube') {
            sh '''
                ./mvnw org.sonarsource.scanner.maven:sonar-maven-plugin:5.3.0.6276:sonar \
                -Dsonar.projectKey=task-manager-api \
                -Dsonar.projectName="Task Manager API"
            '''
        }
    }
}
        stage('Docker Build and Push') {
            steps {
                withCredentials([usernamePassword(                   credentialsId: 'b8a6d383-c509-45ca-9369-4a9eda586369',
                    usernameVariable: 'DOCKERHUB_USERNAME',
                    passwordVariable: 'DOCKERHUB_TOKEN'
                )]) {
                    sh '''
                        echo "$DOCKERHUB_TOKEN" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
                        docker build -t raza0111/task-manager-api:${BUILD_NUMBER} .
                        docker tag raza0111/task-manager-api:${BUILD_NUMBER} raza0111/task-manager-api:latest
                        docker push raza0111/task-manager-api:${BUILD_NUMBER}
                        docker push raza0111/task-manager-api:latest
                        docker logout
                    '''
                }
            }
        }
    }  

	stage('Update Helm Image Tag') {
    steps {
        withCredentials([usernamePassword(
            credentialsId: 'github-gitops',
            usernameVariable: 'GITHUB_USERNAME',
            passwordVariable: 'GITHUB_TOKEN'
        )]) {
            sh '''
                git config user.name "$GITHUB_USERNAME"
                git config user.email "$GITHUB_USERNAME@users.noreply.github.com"

                sed -i "s/tag: \\"latest\\"/tag: \\"${BUILD_NUMBER}\\"/" task-manager/values.yaml

                git add task-manager/values.yaml
                git commit -m "Update image tag to ${BUILD_NUMBER}"
                
                git push https://${GITHUB_USERNAME}:${GITHUB_TOKEN}@github.com/razaabbas-codes/spring-boot-devops-cicd.git HEAD:main
            '''
        }
    }
}

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'CI Pipeline completed successfully!'
        }
        failure {
            echo 'CI Pipeline failed. Check the logs.'
        }
    }
}
