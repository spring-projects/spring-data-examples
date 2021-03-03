#!/usr/bin/env groovy
pipeline {
    agent any	
	
	environment {
        // Puede ser nexus3 o nexus2
        NEXUS_VERSION = "nexus2"
        // Puede ser http o https
        NEXUS_PROTOCOL = "http"
        // Dónde se ejecuta tu Nexus
        NEXUS_URL = "localhost:9084"
        // Repositorio donde subiremos el artefacto
        NEXUS_REPOSITORY = "springs-data-examples-web"
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
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    pom = readMavenPom file: "web/example/pom.xml";
                    // Find built artifact under target folder
                    filesByGlob = findFiles(glob: "web/example/target/*.${pom.packaging}");
                    // Print some info from the artifact found
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    // Extract the path from the File found
                    artifactPath = filesByGlob[0].path;
                    // Assign to a boolean response verifying If the artifact name exists
                    artifactExists = fileExists artifactPath;

                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                        /*nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                // Artifact generated such as .jar, .ear and .war files.
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],

                                // Lets upload the pom.xml file for additional information for Transitive dependencies
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );*/
						
						freeStyleJob('NexusArtifactUploaderJob') {
							steps {
								nexusArtifactUploader {
									nexusVersion(NEXUS_VERSION)
									protocol(NEXUS_PROTOCOL)
									nexusUrl(NEXUS_URL)
									groupId(pom.groupId)
									version(pom.version)
									repository(NEXUS_REPOSITORY)
									credentialsId(NEXUS_CREDENTIAL_ID)
									artifact {
										artifactId(pom.artifactId)
										type('jar')
										classifier('debug')
										file(artifactPath)
									}
								}
							}
						}		
						

                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
    }
}
