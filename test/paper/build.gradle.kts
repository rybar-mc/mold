import java.net.URI

plugins {
    id("com.gradleup.shadow") version "9.0.2"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":paper"))
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
}

tasks.shadowJar {
    minimize()
}

tasks.register("paper") {
    group = "application"
    dependsOn("shadowJar")
    doLast {
        val runDir = File(project.projectDir.path, "run")
        runDir.mkdirs()

        val pluginDir = File(runDir, "plugins")
        pluginDir.mkdirs()

        pluginDir.listFiles()?.forEach {
            if (it.name.startsWith("paper-test-") && it.name.endsWith("-all.jar")) {
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