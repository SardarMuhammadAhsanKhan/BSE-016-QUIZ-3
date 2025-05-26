pipeline {
  agent any

  // Automatically poll GitHub every 5 minutes (optional)
  triggers {
    pollSCM('H/5 * * * *')
  }

  stages {
    stage('Checkout') {
      steps {
        // Grab your Java source from the branch
        checkout scm
      }
    }

    stage('Compile') {
      steps {
        // Compile HelloWorld.java
        sh 'javac HelloWorld.java'
      }
    }

    stage('Run') {
      steps {
        // Run the class and capture output
        sh 'java HelloWorld > java_output.txt'
      }
    }
  }

  post {
    always {
      // Archive the output so you can inspect it
      archiveArtifacts artifacts: 'java_output.txt', fingerprint: true
    }
    success {
      echo '✅ Java build and execution succeeded.'
    }
    failure {
      echo '❌ Java build or execution failed; check Console Output.'
    }
  }
}
