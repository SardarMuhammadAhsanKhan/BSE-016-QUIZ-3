pipeline {
    agent any

    tools {
        jdk 'JDK-17'
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull your repository
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                // Compile with javac
                bat 'javac BloodDonationSystem.java'
            }
        }

        stage('Run') {
            steps {
                // Run your main class
                bat 'java BloodDonationSystem'
            }
        }
    }

    post {
        always {
            // Archive the compiled .class files
            archiveArtifacts artifacts: '*.class', fingerprint: true
        }
        success {
            echo '✅ Build and run succeeded under JDK 17 on Windows!'
        }
        failure {
            echo '🚨 Build or run failed—check the console output for errors.'
        }
    }
}
