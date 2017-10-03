#!groovy
pipeline {
    agent {
        docker {
            image 'java:openjdk-8'
            args  '--network=docker_cd-tools-network'
        }
    }
    stages {
        stage('build + tests') {
            environment {
                npm_config_cache = 'npm-cache'
            }
            steps {
                sh './gradlew test'
            }
        }
        stage('integ tests') {
            steps {
                sh './gradlew integrationTest'
            }
        }
        stage('sonarqube') {
            steps {
                withEnv(["SONARQUBE_SERVER_URL=${SONARQUBE_SERVER}"]) {
                    sh './gradlew -Dsonar.host.url=${SONARQUBE_SERVER_URL} sonarqube'
                }
            }
        }
        stage('Publish to Artifactory') {
            steps {
                withEnv(["ARTIFACTORY_SERVER_URL=${env.ARTIFACTORY_SERVER}"]) {
                    sh './gradlew artifactoryPublish'
                }
            }
        }
    }
}