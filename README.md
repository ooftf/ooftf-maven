# ooftf-maven
![Maven Central](https://img.shields.io/maven-central/v/com.github.ooftf/ooftf-maven)
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
 maven.username=ooftf
 maven.password=*******
 signing.keyId=xxxxxxxx
 signing.password=xxxxxxxx
```
签名文件放到 C:\Users\signing.gpg
##  publish 可配置参数有
* String groupId   默认 com.github.ooftf
* String artifactId 默认 project.name
* String version
* String username  默认 ooftf
* String password
* String url  默认 maven
* String signingKeyId
* String signingPassword
* String signingSecretKeyRingFile 默认 C:\Users\signing.gpg

## 在local.properties可配置参数
* maven.username
* maven.password
* signing.keyId
* signing.password
* signing.file
## be careful
* publish 配置优先于 local.properties
* 如果 version 是以 SNAPSHOT 结尾上传到 snapshot仓库，否则上传到release仓库
* SNAPSHOT 不签名
### snapshot仓库
        maven {
            url 'https://s01.oss.sonatype.org/content/repositories/snapshots/'
        }
#### release仓库
mavenCentral()

### 上传maven参考文档
[Android库发布到Maven Central全攻略](https://xiaozhuanlan.com/topic/6174835029) 导出签名文件，应该选择Backup secret key

