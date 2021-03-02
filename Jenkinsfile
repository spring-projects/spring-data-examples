#!/usr/bin/env groovy
pipeline {
    agent any
    stages {
        stage('Setup') {
            steps {
                git url:'https://github.com/mirgs/spring-data-examples.git', branch: 'web'
            }
        } 
		
		// Compilamos el proyecto y almacenamos los test unitarios y de integracion
       	stage('Build') {
        	steps {
				withMaven (maven: 'maven-3.6.3') {
					//sh 'mvn clean install -f web/pom.xml'
					sh 'mvn clean install -f web/example/pom.xml'
					//sh 'mvn clean install -f web/projection/pom.xml'
					//sh 'mvn clean install -f web/querydsl/pom.xml'
				}
    		}
			
			post {
                always {
                    junit 'web/example/target/surefire-reports/*.xml, web/projection/target/surefire-reports/*.xml, web/querydsl/target/surefire-reports/*.xml'
                }
            }
        }
		
        // Lanzamos en paralelo la comprobacion de dependencias y los mutation test
        stage('Mutation Test') {
			// Lanzamos los mutation test
			
			steps {				
				withMaven (maven: 'maven-3.6.3') {		
					sh 'mvn org.pitest:pitest-maven:mutationCoverage -f web/pom.xml'				
				}
			}
			
        }
		
        // Analizamos con SonarQube el proyecto y pasamos los informes generados (test, cobertura, mutation)
        stage('SonarQube analysis') {
        	steps {
		    	withSonarQubeEnv('local') {
					withMaven (maven: 'maven-3.6.3') {
						withCredentials([usernamePassword(credentialsId: 'credencialConnectJenkins', passwordVariable: 'contraseña', usernameVariable: 'usuario')]) {
							sh 'mvn sonar:sonar -f web/pom.xml \
							-Dsonar.sourceEncoding=UTF-8 \
							-Dsonar.junit.reportPaths=target/surefire-reports'
						}
					}
				}
			}
		}
		
		// Esperamos hasta que se genere el QG y fallamos o no el job dependiendo del estado del mismo
		stage("Quality Gate") {
            steps {	
				withCredentials([usernamePassword(credentialsId: 'credencialConnectJenkins', passwordVariable: 'contraseña', usernameVariable: 'usuario')]) {
					waitForQualityGate abortPipeline: true
				}
            }
        }
    }
}
