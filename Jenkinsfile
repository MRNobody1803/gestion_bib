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
                    cd gestion_bib
                    mkdir -p bin
                    javac -d bin $(find src/library -name "*.java")
                '''
            }
        }

        stage('Run CKJM Metrics') {
            steps {
                echo '📊 Running CKJM analysis...'
                sh '''
                    set -e

                    # Clean previous metrics if any
                    rm -f metrics.txt

                    # Download CKJM JAR if missing
                    if [ ! -f ckjm-1.9.jar ]; then
                        echo "Downloading CKJM (Java Metrics Tool)..."
                        curl -L -o ckjm-1.9.jar https://github.com/dspinellis/ckjm/releases/download/1.9/ckjm-1.9.jar
                    fi

                    # Verify jar file integrity (basic check)
                    if ! file ckjm-1.9.jar | grep -q 'Java archive'; then
                        echo "❌ Download failed or invalid JAR file content."
                        ls -lh ckjm-1.9.jar
                        exit 1
                    fi

                    # Run CKJM
                    echo "Running metrics analysis..."
                    java -jar ckjm-1.9.jar out/production/Gestion-Mg/**/*.class > metrics.txt

                    echo "✅ Metrics successfully generated → metrics.txt"
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
