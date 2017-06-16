pipeline {
    agent { docker 'java:openjdk-8' }
    stages {
        stage('build') {
            environment {
                npm_config_cache = 'npm-cache'
            }
            steps {
                sh './gradlew build'
            }
        }
    }
}