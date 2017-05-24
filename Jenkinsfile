node {
   def mvnHome
   // def gitHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git branch: 'master', url: 'https://github.com/burakovsky/DemoSpringBootApp.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'maven'
      // gitHome = tool 'git'
   }
   stage('NPM build') {
      sh '''ls -al
      git rev-parse HEAD > src/main/resources/static/commit.sha1
      cd src/main/resources/static
      npm install'''
   }
   stage('Maven build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -DskipTests clean install -B"
      } else {
         bat(/"${mvnHome}\bin\mvn" -DskipTests clean install/)
      }
   }
   stage('Packaging') {
       sh '''mv target/*.jar build_sources/demo_app.jar
       cd build_sources/
       docker build -t demo_app-${BUILD_NUMBER} .'''
   }
   withCredentials([string(credentialsId: 'dockerhub_creds', variable: 'dockerhub_creds')]) {
   stage('Uploading'){
      sh '''cd build_sources/
      docker login ${dockerhub_creds}
      docker tag demo_app-${BUILD_NUMBER} burakovsky/hdemo:${BUILD_NUMBER}
      docker push burakovsky/hdemo:${BUILD_NUMBER}
      kubectl run demo-${BUILD_NUMBER} --image=burakovsky/hdemo:${BUILD_NUMBER} --expose=true --port 8080
      kubectl expose deployment demo-${BUILD_NUMBER} --type=NodePort --name=demo-service-${BUILD_NUMBER}'''
      sh "curl 'http://10.0.0.226:3000/api/datasources' -X POST -H 'Content-Type: application/json;charset=UTF-8' --user admin:admin --data-binary '{"name":"test-${BUILD_NUMBER}","type":"prometheus","typeLogoUrl":'public/app/plugins/datasource/prometheus/img/prometheus_logo.svg',"url":'http://10.0.0.70:80',"access":"proxy","isDefault":false,"database":"","user":"","password":"","basicAuth":false,"isDefault":true,"jsonData":{} }'"
      
    }    
  }
}
