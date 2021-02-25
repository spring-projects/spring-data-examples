#!/usr/bin/env groovy
pipeline {
    agent any
    stages {
        stage('Setup') {
            steps {
                git url:'https://github.com/mirgs/spring-data-examples.git', branch: 'master'
            }
        } 
		
		// Compilamos el proyecto y almacenamos los test unitarios y de integracion
       	stage('Build') {
        	steps {
				withMaven (maven: 'maven-3.6.3') {
					sh 'mvn clean install -f web/pom.xml'
					sh 'mvn clean install -f elasticsearch/pom.xml'
					sh 'mvn clean install -f mongodb/pom.xml'
					sh 'mvn clean install -f rest/pom.xml'
				}
    		}
			
			post {
                always {
                    junit 'web/example/target/surefire-reports/*.xml, web/projection/target/surefire-reports/*.xml, web/querydsl/target/surefire-reports/*.xml'
					junit 'elasticsearch/example/target/surefire-reports/*.xml, elasticsearch/reactive/target/surefire-reports/*.xml, elasticsearch/rest/target/surefire-reports/*.xml'
                    junit 'mongodb/example/target/surefire-reports/*.xml, mongodb/projection/target/surefire-reports/*.xml, mongodb/querydsl/target/surefire-reports/*.xml'
                    junit 'rest/example/target/surefire-reports/*.xml, rest/headers/target/surefire-reports/*.xml, rest/multi-store/target/surefire-reports/*.xml, rest/projections/target/surefire-reports/*.xml, rest/security/target/surefire-reports/*.xml, rest/starbucks/target/surefire-reports/*.xml, rest/uri-customization/target/surefire-reports/*.xml'
                }
            }
        }
		
        // Lanzamos en paralelo la comprobacion de dependencias y los mutation test
        stage('Mutation Test') {
			// Lanzamos los mutation test
			
			steps {				
				withMaven (maven: 'maven-3.6.3') {		
					sh 'mvn org.pitest:pitest-maven:mutationCoverage -f web/pom.xml'	
					sh 'mvn org.pitest:pitest-maven:mutationCoverage -f elasticsearch/pom.xml'
					sh 'mvn org.pitest:pitest-maven:mutationCoverage -f mongodb/pom.xml'
					sh 'mvn org.pitest:pitest-maven:mutationCoverage -f rest/pom.xml'				
				}
			}
			
        }
		
        // Analizamos con SonarQube el proyecto y pasamos los informes generados (test, cobertura, mutation)
        stage('SonarQube analysis') {
        	steps {
		    	withSonarQubeEnv(credentialsId: 'aee1ab08-f0d6-4abe-9861-89e3c97916ce', installationName: 'local') {
					withMaven (maven: 'maven-3.6.3') {
						sh 'mvn sonar:sonar -f web/pom.xml \
						-Dsonar.sourceEncoding=UTF-8 \
						-Dsonar.junit.reportPaths=target/surefire-reports\
						-Dsonar.login=admin \
						-Dsonar.password=sinensia1'
						sh 'mvn sonar:sonar -f elasticsearch/pom.xml \
						-Dsonar.sourceEncoding=UTF-8 \
						-Dsonar.junit.reportPaths=target/surefire-reports\
						-Dsonar.login=admin \
						-Dsonar.password=sinensia1'
						sh 'mvn sonar:sonar -f mongodb/pom.xml \
						-Dsonar.sourceEncoding=UTF-8 \
						-Dsonar.junit.reportPaths=target/surefire-reports\
						-Dsonar.login=admin \
						-Dsonar.password=sinensia1'
						sh 'mvn sonar:sonar -f rest/pom.xml \
						-Dsonar.sourceEncoding=UTF-8 \
						-Dsonar.junit.reportPaths=target/surefire-reports\
						-Dsonar.login=admin \
						-Dsonar.password=sinensia1'
					}
				}
			}
		}
		
		// Esperamos hasta que se genere el QG y fallamos o no el job dependiendo del estado del mismo
		//stage("Quality Gate") {
        //    steps {
        //        timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    // Requires SonarQube Scanner for Jenkins 2.7+
        //            waitForQualityGate abortPipeline: true
        //        }
        //    }
        //}
    }
}