pipeline {
    agent { docker 'java:openjdk-8-jre-alpine' }
    stages {
        stage('build') {
            steps {
                ash './gradlew build'
            }
        }
    }
}