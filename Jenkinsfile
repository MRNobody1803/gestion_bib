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
                    echo "==================== CKJM Metrics Report ===================="
                    echo ""
                    echo "Classes with HIGH COMPLEXITY (WMC > 10):"
                    awk '$2 > 10 {print "  ⚠️  " $1 " → WMC=" $2}' metrics.txt || echo "  ✅ None found"
                    echo ""
                    echo "Classes with HIGH COUPLING (CBO > 8):"
                    awk '$5 > 8 {print "  ⚠️  " $1 " → CBO=" $5}' metrics.txt || echo "  ✅ None found"
                    echo ""
                    echo "Classes with HIGH RFC (RFC > 20):"
                    awk '$6 > 20 {print "  ⚠️  " $1 " → RFC=" $6}' metrics.txt || echo "  ✅ None found"
                    echo ""
                    echo "Classes with POOR COHESION (LCOM > 5):"
                    awk '$7 > 5 {print "  ⚠️  " $1 " → LCOM=" $7}' metrics.txt || echo "  ✅ None found"
                    echo ""
                    echo "============================================================="
                '''
            }
        }

        stage('SonarQube Analysis') {
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
            echo '✅ ✅ ✅ Pipeline completed successfully! ✅ ✅ ✅'
            echo '📊 Compilation, CKJM metrics, and SonarQube analysis succeeded!'
            archiveArtifacts artifacts: 'gestion_bib/metrics.txt', allowEmptyArchive: false, onlyIfSuccessful: true
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
