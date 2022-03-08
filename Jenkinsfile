pipeline {
    agent any
        tools {
            maven 'maven 3.8.4'
            jdk 'openjdk-11'
        }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Build'){
            steps {
                sh 'mvn clean install'
            }
        }
    }
}