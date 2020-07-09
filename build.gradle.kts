plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.zaxxer:HikariCP:3.4.2")
    compileOnly("mysql:mysql-connector-java:8.0.19")
}

group = "pro.husk"
version = "1.3.6"
description = "mysql"

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("mysql-" + project.property("version") + ".jar")
    }

    build {
        dependsOn(shadowJar)
    }

    javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "pro.husk"
            artifactId = "mysql"
            version = project.property("version").toString()

            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://maven.husk.pro/repository/maven-public/")

            credentials {
                username = "slave"
                password = if (project.hasProperty("repoPass")) {
                    project.property("repoPass").toString()
                } else {
                    ""
                }
            }
        }
    }
}