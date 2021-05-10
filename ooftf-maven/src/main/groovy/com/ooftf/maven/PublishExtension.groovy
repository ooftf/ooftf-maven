package com.ooftf.maven

import groovy.transform.PackageScope
import org.gradle.api.Project

/**
 * A gradle extension which will be used to configure the plugin.
 *
 * Most of the properties will be used to setup the `bintray-Extension` in BintrayConfiguration.
 * See also: https://github.com/bintray/gradle-bintray-plugin#plugin-dsl
 *
 * Some properties are mandatory and have to be validated before any action on it happen.
 * The other ones are all optional or provide a default value.
 *
 * Optional doesn't mean they aren't needed but that they will handled correctly by the plugin!
 */
class PublishExtension {

    String groupId
    String artifactId
    String version
    String username
    String password
    String url
    String signingKeyId
    String signingPassword
    String signingSecretKeyRingFile

    /**
     * Validate all mandatory properties for this extension.
     *
     * Will throw a Exception if not setup correctly.
     */
    @PackageScope
    void validate() {
        String extensionError = "";

        if (groupId == null) {
            extensionError += "Missing groupId. "
        }
        if (artifactId == null) {
            extensionError += "Missing artifactId. "
        }
        if (version == null) {
            extensionError += "Missing version. "
        }
        if (username == null) {
            extensionError += "Missing username. "
        }
        if (password == null) {
            password += "Missing password. "
        }
        if (extensionError) {
            String prefix = "Have you created the publish closure? "
            throw new IllegalStateException(prefix + extensionError)
        }
    }

    void initDefault(Project project) {
        Properties properties = new Properties()
        InputStream inputStream = project.rootProject.file('local.properties').newDataInputStream();
        properties.load(inputStream)
        def appUser = properties.getProperty('maven.username')
        def appKey = properties.getProperty('maven.password')
        def appKeyId = properties.getProperty('signing.keyId')
        def appPassword = properties.getProperty('signing.password')
        def appFile = properties.getProperty('signing.file')



        File pcPropertiesFile = new File('C:\\Users\\local.properties')
        if (pcPropertiesFile.exists()) {
            Properties pcProperties = new Properties()
            InputStream pcInputStream = pcPropertiesFile.newDataInputStream();
            pcProperties.load(pcInputStream)
            def pcUser = pcProperties.getProperty('maven.username')
            def pcKey = pcProperties.getProperty('maven.password')
            def pcKeyId = pcProperties.getProperty('signing.keyId')
            def pcPassword = pcProperties.getProperty('signing.password')
            def pcFile = pcProperties.getProperty('signing.file')

            if (appUser == null || appUser.empty) {
                appUser = pcUser
            }
            if (appKey == null || appKey.empty) {
                appKey = pcKey
            }
            if (appKeyId == null || appKeyId.empty) {
                appKeyId = pcKeyId
            }
            if (appPassword == null || appPassword.empty) {
                appPassword = pcPassword
            }
            if (appFile == null || appFile.empty) {
                appFile = pcFile
            }
        }


        if (appKeyId == null || appKeyId.empty) {
            appKeyId = '2A4D54DC'
        }
        if (appFile == null || appFile.empty) {
            appFile = 'C:\\Users\\signing.gpg'
        }
        if (signingKeyId == null || signingKeyId.empty) {
            signingKeyId = appKeyId
        }
        if (signingSecretKeyRingFile == null || signingSecretKeyRingFile.empty) {
            signingSecretKeyRingFile = appFile
        }
        if (signingPassword == null || signingPassword.empty) {
            signingPassword = appPassword
        }
        if (appUser == null || appUser.empty) {
            appUser = 'ooftf'
        }
        if (username == null || username.empty) {
            username = appUser
        }
        if (password == null || password.empty) {
            password = appKey
        }
        if (groupId == null || groupId.empty) {
            groupId = "com.github.ooftf"
        }
        if (artifactId == null || artifactId.empty) {
            artifactId = project.name
        }

        if (url == null || url.empty) {
            if (version.endsWith("SNAPSHOT")) {
                url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            } else {
                url = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            }
        }
    }

}
