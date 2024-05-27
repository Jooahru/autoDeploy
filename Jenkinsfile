pipeline{
    agent any

    environment {
        dockerHubRegistry = 'torpedokim/helloworld' // dockerHub에 repository 명
        dockerHubRegistryCredential = 'torpedokim' // Jenkins에서 생성한 dockerhub-credential-ID값
        githubCredential = 'forJenkins' // Jenkins에서 생성한 github-credential-ID값
    }
// 1. git repository 가 체크되는지 확인, 제대로 연동이 안될 경우, 이 단계(stage) 에서 fail 발생
    stages {
        stage('check out application git branch'){
            steps {
                checkout scm
            }
            post {
                failure {
                    echo 'repository checkout failure'
                }
                success {
                    echo 'repository checkout success'
                }
            }
        }
// 2. gradle 빌드 (springboot web 생성을 위한 선행과정)

        stage('build gradle') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build'
                sh 'ls -al ./build'
            }
            post {
                success {
                    echo 'gradle build success'
                }
                failure {
                    echo 'gradle build failed'
                }
            }
        }
// 3. Dockefile build
        stage('docker image build'){
            steps{
                sh "docker build . -t ${dockerHubRegistry}:${currentBuild.number}"
                sh "docker build . -t ${dockerHubRegistry}:latest"
            }
            post {
                    failure {
                      echo 'Docker image build failure !'
                    }
                    success {
                      echo 'Docker image build success !'
                    }
            }
        }
 // 4. 빌드된 이미지 push
        stage('Docker Image Push') {
            steps {
                withDockerRegistry([ credentialsId: dockerHubRegistryCredential, url: "" ]) {
                    sh "docker push ${dockerHubRegistry}:${currentBuild.number}"
                    sh "docker push ${dockerHubRegistry}:latest"

                    sleep 10 /* Wait uploading */
                }
            }
            post {
                    failure {
                      echo 'Docker Image Push failure !'
                      sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                      sh "docker rmi ${dockerHubRegistry}:latest"
                    }
                    success {
                      echo 'Docker image push success !'
                      sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                      sh "docker rmi ${dockerHubRegistry}:latest"
                    }
            }
        }
 // 5. 쿠버네티스 배포 작업
        stage('K8S Manifest Update') {
            steps {
                sh "ls"
                sh 'mkdir -p gitOpsRepo'
                dir("gitOpsRepo")
                {
                    git branch: "main",
                    credentialsId: githubCredential,
                    url: 'https://github.com/Jooahru/k8s-manifests.git'
                    sh "git config --global user.email dndlf333@naver.com"
                    sh "git config --global user.name WOOIL KIM"
                    sh "sed -i 's/helloWorld:.*\$/helloWorld:${currentBuild.number}/' deployment.yaml"
                    sh "git add deployment.yaml"
                    sh "git commit -m '[UPDATE] k8s ${currentBuild.number} image versioning'"
                    
                    withCredentials([gitUsernamePassword(credentialsId: githubCredential,
                                     gitToolName: 'git-tool')]) {
                        sh "git remote set-url origin https://github.com/Jooahru/k8s-manifests.git"
                        sh "git push -u origin main"
                    }
                }
            }
            post {
                    failure {
                      echo 'K8S Manifest Update failure !'
                    }
                    success {
                      echo 'K8S Manifest Update success !'
                    }
            }
        }

    }
}