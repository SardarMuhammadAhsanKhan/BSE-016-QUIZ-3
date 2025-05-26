pipeline {
    agent any

    // Let users specify the Roman numeral when they kick off the build
    parameters {
        string(name: 'ROMAN', defaultValue: 'XIV', description: 'Roman numeral to convert')
    }

    stages {
        stage('Checkout') {
            steps {
                // clone/pull your Jenkins-configured repo
                checkout scm
            }
        }

        stage('Convert Roman → Integer') {
            steps {
                // Run the Python script and redirect output to a file
                // On a Windows agent, use 'bat'
                bat """
                    echo Converting %ROMAN% ...
                    python Converting_Roman_to_Integer.py %ROMAN% > conversion_result.txt
                    type conversion_result.txt
                """
            }
        }
    }

    post {
        always {
            // Archive the result so you can download it from the Jenkins UI
            archiveArtifacts artifacts: 'conversion_result.txt', fingerprint: true
        }
        success {
            echo "✅ Conversion succeeded: see conversion_result.txt"
        }
        failure {
            echo "🚨 Conversion failed—check the console output for errors."
        }
    }
}

