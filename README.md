## 描述
简化上传到mavenCentral的配置
## 使用
project.gradle
```groovy
 classpath "com.github.ooftf:ooftf-maven:1.0.0"
```
library.gradle
```groovy
 apply plugin: 'ooftf-maven'
 publish{
    version = '1.0.0'
    groupId = 'com.github.ooftf'
 }

```
 local.properties
```
 mavenUsername=ooftf
 mavenPassword=*******
```
##  publish 可配置参数有
* String groupId   默认 com.github.ooftf
* String artifactId 默认 project.name
* String version
* String username  默认 ooftf
* String password
* String url 默认 maven

如果 version 是以SNAPSHOT结尾上传到 snapshot仓库，否则上传到release仓库

### snapshot仓库
        maven {
            url 'https://s01.oss.sonatype.org/content/repositories/snapshots/'
        }
#### release仓库
mavenCentral()

