plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)

    id("paper-plugin")
}

dependencies {
    implementation(project(":quickie-paper"))
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }

    shadowJar {
        archiveClassifier.set("")
    }
}