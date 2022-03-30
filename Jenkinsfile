pipeline {
    agent{label 'master'}

    environment {
        imagename = "supercontainerapps/sg-aug-monolito"
        registryCredential = 'super-container-apps'
        dockerImage = ''
    }

    stages {
        stage('Maven compilation'){
            tools{
                jdk 'Java11'
                maven 'maven3.8.5'
            }
            steps{
                script{
                    if (fileExists("./settings.xml")){
                        sh ("mvn -s settings.xml package -Pdev  -DskipTest=true -Dmaven.test.skip=true")
                    } else{
                        sh ("mvn package  -DskipTest=true -Dmaven.test.skip=true")

                    }
                }
            }
        }
        stage('SonarQube analysis') {
            tools {
               jdk 'Java11'
               nodejs 'NodeJsV16.13.1'
            }
          steps {
             script {
                    def scannerHome = tool 'Sonarqube4.5';
                    withSonarQubeEnv('sonarqube9.0.1') {
                        sh """${tool('Sonarqube4.5')}/bin/sonar-scanner"""
                    }
                }
            }
        }

        stage('Build Docker image'){
                steps{
                    sh "docker build -t ${imagename}:${currentBuild.number}-test ."
                }
            }
        stage("Pushing docker image to registry"){
            steps{
                withCredentials([usernamePassword(credentialsId: 'super-container-apps', passwordVariable: 'DOCKER_PASS',       usernameVariable: 'DOCKER_USER')]) {
                // push images to defined registry.
                //dockerImage.push("${currentBuild.number}-test")
                sh "docker login --username=$DOCKER_USER --password='$DOCKER_PASS'"
                sh "docker push ${imagename}:${currentBuild.number}-test"
                sh "docker logout"
                }                  
            }
        }
        stage('Remove Unused docker image') {
            steps{
                sh "docker rmi $imagename:${currentBuild.number}-test"
            }
        }
    }
}
