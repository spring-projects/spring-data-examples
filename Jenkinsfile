#!/usr/bin/env groovy
pipeline {
    agent any	
	
	environment {
        // Puede ser nexus3 o nexus2
        NEXUS_VERSION = "nexus3"
        // Puede ser http o https
        NEXUS_PROTOCOL = "http"
        // Dónde se ejecuta tu Nexus
        //NEXUS_URL = "172.22.0.2:9084"
        NEXUS_URL = "192.168.1.57:9084"
        // Repositorio donde subiremos el artefacto
        NEXUS_REPOSITORY = "\^/browse/springs-data-examples-web/"
        // Identificación de credencial de Jenkins para autenticarse en Nexus OSS
        NEXUS_CREDENTIAL_ID = "nexusCredenciales"
    }
	
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
		//stage("Quality Gate") {
        //    steps {
        //        timeout(time: 1, unit: 'HOURS') {
        //            // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
        //            // true = set pipeline to UNSTABLE, false = don't
        //            // Requires SonarQube Scanner for Jenkins 2.7+
        //            waitForQualityGate abortPipeline: true
        //        }
        //    }
        //}
		
		stage("nexus") {
            steps {
                script {
					dir("web/example") {
						def pom = readMavenPom file: 'pom.xml';
												
						dir("target") {

							nexusArtifactUploader(
								nexusVersion: NEXUS_VERSION,
								protocol: NEXUS_PROTOCOL,
								nexusUrl: NEXUS_URL,
								groupId: pom.groupId,
								version: pom.version,
								repository: NEXUS_REPOSITORY,
								credentialsId: NEXUS_CREDENTIAL_ID,
								artifacts: [
									// Artefacto generado como archivos .jar, .ear y .war.
									[artifactId: pom.artifactId,
									classifier: '',
									file: 'spring-data-web-example-2.0.0.BUILD-SNAPSHOT.jar',
									type: pom.packaging]
								]
							);
						}
					}
                    
                }
            }
        }
    }
}
