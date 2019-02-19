import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "2.1.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("com.palantir.docker") version "0.20.1"
}

group = "software.engineering.task"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.json", "json", "20140107")
    compile("com.google.code.gson:gson:2.8.5")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    register("unpack", Copy::class) {
        dependsOn(tasks.named<BootJar>("bootJar"));

        from(zipTree(tasks.named<BootJar>("bootJar").get().outputs.files.singleFile))
        into("build/dependency")
    }
}

docker {
    name = tasks.named<BootJar>("bootJar").get().baseName
    copySpec.from(tasks["unpack"].outputs).into("dependency")
    buildArgs(mapOf("DEPENDENCY" to "dependency"))
}

tasks["build"].finalizedBy(tasks["docker"])