package com.ooftf.maven

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar

class ReleasePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        boolean isAndroid = project.plugins.hasPlugin('com.android.library')
        PublishExtension extension = project.extensions.create('publish', PublishExtension)
        project.apply([plugin: 'maven-publish'])
        project.task('generateSourcesJar', type: Jar) {
            if(isAndroid){
                from project.android.sourceSets.main.java.srcDirs
                classifier 'sources'
            }

        }
        project.afterEvaluate {
            extension.initDefault(project)
            extension.validate()
            project.publishing {
                publications {
                    release(MavenPublication) {
                        if (isAndroid) {
                            from project.components.release
                        } else {
                            from project.components.find()
                        }
                        groupId = extension.groupId
                        artifactId = extension.artifactId
                        version = extension.version
                        if (isAndroid) {
                            artifact project.tasks.generateSourcesJar
                        }
                    }
                }
                repositories {
                    maven {
                        url = extension.url
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
