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
                    cd gestion_bib

                    # Run CKJM with BCEL in classpath
                    echo "Running metrics analysis..."
                    java -cp ckjm-2.4.jar:bcel-6.5.0.jar:bin gr.spinellis.ckjm.MetricsFilter bin/library/**/*.class > metrics.txt

                    # Verify metrics were generated
                    if [ ! -s metrics.txt ]; then
                        echo "❌ Metrics file is empty"
                        exit 1
                    fi

                    echo "✅ Metrics successfully generated → metrics.txt"
                    echo "📈 Summary of analyzed classes:"
                    awk '{print "  - " $1}' metrics.txt | head -10
                '''
            }
        }

        stage('Analyze Metrics') {
            steps {
                echo '🔍 Analyzing code quality metrics...'
                sh '''
                    cd gestion_bib

                    # Make script executable
                    chmod +x analyze-metrics1.sh
                    chmod +x analyze-metrics-table.sh

                    # Run analysis script
                    ./analyze-metrics1.sh
                    ./analyze-metrics-table.sh
                '''
            }
        }


        /* stage('SonarQube Analysis') {
            steps {
                echo '🔎 Running SonarQube analysis...'
                script {
                    dir('gestion_bib') {
                        withSonarQubeEnv('SonarQb') {
                            def scannerHome = tool 'SonarScanner'
                            sh "${scannerHome}/bin/sonar-scanner"
                        }
                    }
                }
            }
        } */

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
            echo '✅ ✅ ✅ Pipeline completed successfully! ✅ ✅ ✅'
            echo '📊 Compilation, CKJM metrics succeeded!'
            archiveArtifacts artifacts: 'gestion_bib/metrics.txt, gestion_bib/metrics-table-report.txt', allowEmptyArchive: false, onlyIfSuccessful: true
        }
        failure {
            echo '❌ ❌ ❌ Pipeline failed! ❌ ❌ ❌'
            echo '🔍 Check the logs above for details.'
        }
        always {
            echo '🧹 Cleaning up workspace...'
        }
    }
}