pipeline {
    agent any

    options {
        // Keep only the last 5 builds in Jenkins
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    tools {
        jdk 'JDK17'  // Make sure this name matches the JDK name in Jenkins Global Tools
    }

    stages {

        stage('Compile Java') {
            steps {
                echo 'Compiling Java sources...'
                sh '''
                    mkdir -p out/production/gestion_bib
                    javac -d out/production/gestion_bib $(find src -name "*.java")
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQb') {  // Must match your SonarQube server name in Jenkins → Manage Jenkins → System
                    script {
                            def scannerHome = tool 'SonarScanner'
                            sh "${scannerHome}/bin/sonar-scanner"
                            }
                }
            }
        }

        stage('Quality Gate') {
           steps {
                // Wait for SonarQube quality gate result (fail the build if it doesn’t pass)
              timeout(time: 5, unit: 'MINUTES') {
                  waitForQualityGate abortPipeline: true
               }
            }
        }
    }

    post {
        success {
            echo '✅ Build and SonarQube analysis succeeded!'
        }
        failure {
            echo '❌ Build or SonarQube analysis failed.'
        }
    }
}
