plugins {
    id("root-plugin")

    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadow)
}

project.group = "${rootProject.group}.paper"
project.version = rootProject.version

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)

    api(project(":quickie-core"))
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}