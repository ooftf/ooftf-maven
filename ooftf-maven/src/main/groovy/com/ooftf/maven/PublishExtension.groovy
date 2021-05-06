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
            password += "Missing userOrg. "
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
        def user = properties.getProperty('mavenUsername')
        def key = properties.getProperty('mavenPassword')

        if (user == null || user.empty) {
            user = 'ooftf'
        }
        if (username == null || username.empty) {
            username = user
        }
        if (password == null || password.empty) {
            password = key
        }
        if (groupId == null || groupId.empty) {
            groupId = "com.github.ooftf"
        }
        if (artifactId == null || artifactId.empty) {
            artifactId = project.name
        }

    }

}
