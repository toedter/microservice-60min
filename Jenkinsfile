pipeline {
    agent { docker 'java:openjdk-8' }
    stages {
        stage('build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}