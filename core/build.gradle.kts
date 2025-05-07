plugins {
    id("root-plugin")
}

project.group = "${rootProject.group}.core"
project.version = rootProject.version

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly(libs.brigadier)
}