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
				//bat(script: "mvn org.pitest:pitest-maven:mutationCoverage -f web/pom.xml", returnStatus: true)
				withMaven (maven: 'maven-3.6.3') {
					sh 'mvn clean pitest -f web/pom.xml'				
				}
				step([$class: 'PitPublisher', 
                     mutationStatsFile: 'build/reports/pitest/**/mutations.xml', 
                     minimumKillRatio: 50.00, 
                     killRatioMustImprove: false
                ])
			}
			
        }
		
        // Analizamos con SonarQube el proyecto y pasamos los informes generados (test, cobertura, mutation)
        /*stage('SonarQube analysis') {
        	steps {
		    	withSonarQubeEnv(credentialsId: 'aee1ab08-f0d6-4abe-9861-89e3c97916ce', installationName: 'local') {
		    		sh 'mvn sonar:sonar -f web/pom.xml \
		    		-Dsonar.sourceEncoding=UTF-8 \
		    		-Dsonar.junit.reportPaths=target/surefire-reports'
				}
			}
		}*/
		
		// Esperamos hasta que se genere el QG y fallamos o no el job dependiendo del estado del mismo
		/*stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    // Requires SonarQube Scanner for Jenkins 2.7+
                    waitForQualityGate abortPipeline: true
                }
            }
        }*/
    }
}