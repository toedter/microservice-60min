pipeline {
    agent { docker 'java:openjdk-8-jre-alpine' }
    stages {
        stage('build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}