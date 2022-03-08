pipeline {
    agent any
        tools {
            maven 'maven'
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
        stage('archive'){
            steps {
                if(!env.CHANGE_ID){
                    archiveArtifacts artifacts: 'target/YoutubeDownloader.jar'
                }
            }
        }
    }
}