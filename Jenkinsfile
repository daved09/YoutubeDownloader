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
        stage('sonar'){
            steps {
                script {
                    withCredentials([string(credentialsId: 'youtube_downloader_sonar', variable: 'sonarcloud_token')]){
                        if(BRANCH_NAME == "master"){
                            sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=YoutubeDownloader -Dsonar.host.url=http://daluba.de:9001 -Dsonar.login=$sonarcloud_token'
                        }
                    }
                }
            }
        }
    }
    post {
        success{
            script {
                if(env.BRANCH_NAME == 'release' || env.BRANCH_NAME == 'master'){
                    archiveArtifacts artifacts: 'target/YoutubeDownloader.jar'
                }
            }
        }
    }
}