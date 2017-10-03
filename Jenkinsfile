#!groovy
pipeline {
    agent {
        docker {
            image 'java:openjdk-8'
            args  '--network=docker_cd-tools-network'
        }
    }
    environment {
        SONARQUBE_SERVER_URL = "${env.SONARQUBE_SERVER}"
        ARTIFACTORY_SERVER = "${env.ARTIFACTORY_SERVER}"
        npm_config_cache = "npm-cache"
    }
    stages {
        stage('Build + Unit Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Integration Test') {
            steps {
                sh './gradlew integrationTest'
            }
        }

        stage('Quality') {
            steps {
                sh './gradlew -Dsonar.host.url=${SONARQUBE_SERVER_URL} sonarqube'
            }
        }

        stage('Publish to Artifactory') {
            steps {
                sh './gradlew artifactoryPublish'
            }
        }
    }
}