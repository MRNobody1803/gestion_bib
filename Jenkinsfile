pipeline {
    agent any

    options {
        // Garder uniquement les 5 derniers builds
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    tools {
        jdk 'JDK17'  // Nom du JDK configuré dans Jenkins
    }

    stages {

        stage('Compile Java Code') {
            steps {
                echo '⚙️ Compiling Java sources...'
                sh '''
                    mkdir -p bin
                    javac -d bin $(find src/library -name "*.java")
                '''
            }
        }

        stage('Run CKJM Metrics') {
            steps {
                echo '📊 Running CKJM analysis...'
                sh '''
                    # Assure-toi que ckjm-1.8.jar est dans le répertoire Jenkins workspace
                    if [ ! -f ckjm-1.8.jar ]; then
                        echo "Downloading CKJM..."
                        wget https://www.spinellis.gr/sw/ckjm/ckjm-1.8.jar
                    fi

                    # Exécution de CKJM sur les classes compilées
                    java -jar ckjm-1.8.jar bin/library/**/*.class > metrics.txt
                    echo "Metrics generated in metrics.txt"
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQb') {
                    script {
                        def scannerHome = tool 'SonarScanner'
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
            }
        }

        // Optionnel : Activer le Quality Gate
        // stage('Quality Gate') {
        //     steps {
        //         timeout(time: 10, unit: 'MINUTES') {
        //             waitForQualityGate abortPipeline: true
        //         }
        //     }
        // }
    }

    post {
        success {
            echo '✅ Compilation, CKJM et analyse SonarQube réussies !'
            archiveArtifacts artifacts: 'metrics.txt', onlyIfSuccessful: true
        }
        failure {
            echo '❌ Échec lors de la compilation ou de l’analyse.'
        }
    }
}
