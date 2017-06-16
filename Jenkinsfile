pipeline {
    agent { docker 'java:openjdk-8-jre' }
    stages {
        stage('build') {
            steps {
                sh './gradlew build'
            }
        }
    }
}