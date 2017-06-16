pipeline {
    agent { docker 'java:openjdk-8' }
    stages {
        stage('build') {
            steps {
                sh "sudo chown -R $USER:$GROUP ~/.npm"
                sh './gradlew build'
            }
        }
    }
}