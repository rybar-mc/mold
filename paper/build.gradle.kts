import java.net.URI

plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
    id("com.gradleup.shadow") version "9.0.0-beta11"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":common"))

    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

tasks.register("paper") {
    group = "application"
    dependsOn("shadowJar")
    doLast {
        val runDir = File(project.projectDir.path, "run")
        runDir.mkdirs()

        val pluginDir = File(runDir, "plugins")
        pluginDir.mkdirs()

        pluginDir.listFiles()?.forEach {
            if (it.name.endsWith(".jar")) {
                it.delete()
            }
        }

        val builtJar = tasks.getByName("shadowJar").outputs.files.singleFile
        val targetJar = File(pluginDir, builtJar.name)

        builtJar.copyTo(targetJar, true)

        val serverUrl = "https://api.papermc.io/v2/projects/paper/versions/1.21.4/builds/204/downloads/paper-1.21.4-204.jar"
        val serverJar = File(runDir, "server.jar")

        if (!serverJar.exists()) {
            serverJar.outputStream().use { output ->
                URI.create(serverUrl).toURL().openStream().use { input ->
                    input.copyTo(output)
                }
            }

            File(runDir, "eula.txt").writeText("eula=true")
        }

        exec {
            commandLine("java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "-jar", "server.jar", "--nogui")
            workingDir(runDir)
        }
    }
}