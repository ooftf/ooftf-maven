package com.ooftf.maven

import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication

class ReleasePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        PublishExtension extension = project.extensions.create('publish', PublishExtension)
        project.apply([plugin: 'maven-publish'])
        project.task('generateSourcesJar',type: Jar){
            from project.android.sourceSets.main.java.srcDirs
            classifier 'sources'
        }
        project.afterEvaluate {
            extension.initDefault(project)
            extension.validate()
            project.publishing {
                publications {
                    release(MavenPublication) {
                        from project.components.release
                        groupId = extension.groupId
                        artifactId = extension.artifactId
                        version = extension.version
                        artifact project.tasks.generateSourcesJar
                    }
                }
                repositories {
                    maven {
                        name = 'snapshotRepository'
                        url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                        credentials {
                            username = extension.username
                            password = extension.password
                        }
                    }
                    maven {
                        name = 'repository'
                        url = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                        credentials {
                            username = extension.username
                            password = extension.password
                        }
                    }
                }
            }
        }
    }
}
